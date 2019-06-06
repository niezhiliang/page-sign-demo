package cc.huluwa.page.sign.demo.server.job;

import cc.huluwa.page.sign.demo.server.domain.PcSignDTO;
import cc.huluwa.page.sign.demo.server.domain.SuccessVO;
import cc.huluwa.page.sign.demo.server.service.JobInvoke;
import cc.huluwa.page.sign.demo.server.utils.SpringContextUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Data
public class CallBackJob extends QuartzJobBean implements Job {

    private String data;

    private String pcSignDTO;

    /**
     * quartz调度任务执行的方法
     * @param jobExecutionContext
     */
    protected void executeInternal(JobExecutionContext jobExecutionContext) {

        PcSignDTO pcSignDTO = JSON.parseObject(this.pcSignDTO,PcSignDTO.class);

        SuccessVO successVO = JSON.parseObject(this.data,SuccessVO.class);

        JobInvoke jobInvoke =  SpringContextUtil.getBean(JobInvoke.class);

        jobInvoke.invokeJon(pcSignDTO,successVO);

    }
}
