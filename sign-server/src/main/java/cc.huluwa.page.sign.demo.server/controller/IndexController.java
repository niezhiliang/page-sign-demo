package cc.huluwa.page.sign.demo.server.controller;

import cc.huluwa.page.sign.demo.server.domain.*;
import cc.huluwa.page.sign.demo.server.job.CallBackJob;
import cc.huluwa.page.sign.demo.server.service.QuartzService;
import cc.huluwa.page.sign.demo.server.utils.MD5Utils;
import cc.huluwa.page.sign.demo.server.utils.RedisListTools;
import cc.huluwa.page.sign.demo.server.utils.RedisTools;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private HttpServletRequest request;

    /**
     * 返回给客户端的签署form表单前缀
     */
    private final static String FORM_BRFORE = "<html>" +
                               "<head></head>" +
                               "<body>";

    /**
     * 返回给客户端签署的from扁担后缀
     */
    private final static String FORM_END =
                               "</form>" +
                               "<script>document.forms[0].submit();</script>" +
                               "</body>" +
                               "</html>";

    @Autowired
    private RedisTools redisTools;

    @Autowired
    private RedisListTools<Integer> redisListTools;

    @Value("${pageSign.times}")
    private List<Integer> times;

    @Autowired
    private QuartzService quartzService;


    /**
     * 客户端请求的签署页面
     * @param pcSignDTO
     * @return
     */
    @RequestMapping(value = "pcSign")
    @ResponseBody
    public String pcSign(@RequestBody PcSignDTO pcSignDTO) {

        /**
         * 拼接签署form表单
         */
        String form = "<form  method='post' action='http://127.0.0.1:8080/signUI'>";

        String input = "<input type='hidden' name='signParams' value='"+JSON.toJSONString(pcSignDTO)+"'/>";

        log.info(JSON.toJSONString(pcSignDTO));
        return FORM_BRFORE+form+input+FORM_END;
    }

    /**
     * 签署页面
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "signUI")
    public String sign(Model model) {

        long start = System.currentTimeMillis();

        String signParams = request.getParameter("signParams");


        PcSignDTO pcSignDTO = JSON.parseObject(signParams,PcSignDTO.class);

        System.out.println(System.currentTimeMillis() - start + "---------------");
        redisTools.save(pcSignDTO.getContractId(),signParams,1, TimeUnit.DAYS);

        System.out.println(System.currentTimeMillis() - start + "---------------");

        model.addAttribute("params",pcSignDTO);

        System.out.println(System.currentTimeMillis() - start + "---------------");


        return "signPage";
    }


    /**
     * 页面调用签署的方法
     * 由于公司原因，所以我把签署的代码去掉了，并没有真正的去签署
     * @param contractId
     * @return
     */
    @RequestMapping(value = "sign")
    @ResponseBody
    public String sign(String contractId) throws InterruptedException, IllegalAccessException {

        String signParams = redisTools.get(contractId);
        PcSignDTO pcSignDTO = JSON.parseObject(signParams,PcSignDTO.class);

        SuccessVO successVO = new SuccessVO();
        successVO.setFile(pcSignDTO.getFileBase64());
        successVO.setTimeStamp(System.currentTimeMillis());
        successVO.setContractId(pcSignDTO.getContractId());
        //添加md5签名
        successVO.setSign(MD5Utils.getSign(successVO,"123456789"));
        //这是回调页面浏览器的值
        String returnPage = pcSignDTO.getCallPage()+"?contractId="+pcSignDTO.getContractId();

        //回调地址
        //将回调时间保持到redis中
        redisListTools.leftPushList(pcSignDTO.getContractId(),times);
        //第一个回调时间(一般为0)
        Integer times = redisListTools.rightPop(pcSignDTO.getContractId());
        try {
            quartzService.addJob
                    (CallBackJob.class,times+"",pcSignDTO.getContractId(),JSON.toJSONString(pcSignDTO),JSON.toJSONString(successVO),new Date(System.currentTimeMillis() + times));
        } catch (SchedulerException e) {
            log.error("添加回调error:{}",e.getMessage());
        }
        //我是通过回调地址将合同文件保存到了redis，所以要确保回调地址的执行在回调页面的前面，回调页面才能正常显示合同
        Thread.sleep(3000);
        return returnPage;
    }
}
