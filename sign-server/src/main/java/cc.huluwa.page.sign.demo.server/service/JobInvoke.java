package cc.huluwa.page.sign.demo.server.service;

import cc.huluwa.page.sign.demo.server.domain.PcSignDTO;
import cc.huluwa.page.sign.demo.server.domain.SuccessVO;
import cc.huluwa.page.sign.demo.server.job.CallBackJob;
import cc.huluwa.page.sign.demo.server.utils.HttpUtils;
import cc.huluwa.page.sign.demo.server.utils.RedisListTools;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class JobInvoke {

    @Autowired
    private RedisListTools<Integer> redisListTools;

    @Autowired
    private QuartzService quartzService;


    /**
     * 调度任务执行方法的具体实现，
     * 原理：第一次任务执行完成后，判断时候已经收到SUCCESS，
     * 如果没有，则从redis中取出第二个执行时间，并将任务添加到
     * quartz，如果收到了SUCCESS，则将redis中剩余的执行时间删除
     * @param pcSignDTO
     * @param successVO
     */
    public void invokeJon(PcSignDTO pcSignDTO, SuccessVO successVO) {

        boolean flag = false;
        try {
            String  result = HttpUtils.doPost(pcSignDTO.getCallBackUrl(), successVO);

            if (result.equals("SUCCESS")) {
                redisListTools.delete(pcSignDTO.getContractId());
                log.info("{}回调成功，已经接收到客户端返回的SUCCESS",pcSignDTO.getContractId());
            } else {
                flag = true;
                log.error("回调收到的不是SUCCESS，{}",result);
            }
        } catch (IOException e) {
           log.error("error:{}-->回调访问异常",e.getMessage());
            flag = true;
        }
        //回调没收到SUCCESS，就执行该方法
        if (flag) {
            Integer time = redisListTools.rightPop(pcSignDTO.getContractId());
            if (time == null) {
                log.info("{}回调失败，所有回调都已经触发完毕，不再进行回调");
            } else {
                Date startAt = new Date(System.currentTimeMillis() + time);
                try {
                    quartzService.addJob(CallBackJob.class,time+"",pcSignDTO.getContractId(),JSON.toJSONString(pcSignDTO),JSON.toJSONString(successVO),startAt);
                } catch (SchedulerException e) {
                    log.error("error:{}-->任务调度添加失败:{}",e.getMessage(),pcSignDTO.getContractId());
                }
            }
        }
    }
}
