package cc.huluwa.page.sign.demo.client.controller;

import cc.huluwa.page.sign.demo.client.domain.PcSignDTO;
import cc.huluwa.page.sign.demo.client.domain.PortDTO;
import cc.huluwa.page.sign.demo.client.domain.SuccessVO;
import cc.huluwa.page.sign.demo.client.utils.HttpUtils;
import cc.huluwa.page.sign.demo.client.utils.MD5Utils;
import cc.huluwa.page.sign.demo.client.utils.RedisTools;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class SignController {

    /**
     * 请求客户端地址
     */
    @Value("${sign.requestUrl}")
    private String requestUrl;

    /**
     * 回调地址
     */
    @Value("${sign.callBack}")
    private String callBackUrl;

    /**
     * 回调页面
     */
    @Value("${sign.callPage}")
    private String callPage;

    @Autowired
    private RedisTools redisTools;


    /**
     * 跳转到签署点击跳转页面
     * @return
     */
    @RequestMapping(value = "/")
    public String signUI() {
        return "index";
    }


    /**
     * 点击签署按钮
     * @return
     */
    @RequestMapping(value = "sign")
    @ResponseBody
    public String sign() {

        PcSignDTO pcSignDTO = new PcSignDTO();
        pcSignDTO.setContractId(UUID.randomUUID().toString().replaceAll("-",""));
        pcSignDTO.setCallBackUrl(callBackUrl);
        pcSignDTO.setCallPage(callPage);
        pcSignDTO.setFileBase64(encryptToBase64(SignController.class.getClassLoader().getResource("./test.pdf").getPath()));
        pcSignDTO.setSignImgBase64(encryptToBase64(SignController.class.getClassLoader().getResource("./sign.png").getPath()));
        pcSignDTO.setName("苏雨");
        pcSignDTO.setIdentityCode("1008611");

        PortDTO portDTO = new PortDTO();
        portDTO.setFirstX(346f);
        portDTO.setFirstY(460f);
        portDTO.setSecondX(506f);
        portDTO.setSecondY(540f);
        portDTO.setImgNum(1);

        pcSignDTO.setPorts(Arrays.asList(portDTO));

        String str = HttpUtils.doPost(requestUrl,pcSignDTO);
        System.out.println(str);

        return str;
    }

    /**
     * 回调地址
     * @param successVO
     * @return
     */
    @RequestMapping(value = "callBack")
    @ResponseBody
    public String callBack(@RequestBody SuccessVO successVO) throws IllegalAccessException {
        System.out.println("收到回调---------------");
        System.out.println(JSON.toJSONString(successVO));

        //得到服务端的签名
        String sign = successVO.getSign();
        successVO.setSign(null);

        String newSign = MD5Utils.getSign(successVO,"123456789");

        //判断签名是否正确
        if (newSign.equalsIgnoreCase(sign)) {
            redisTools.save(successVO.getContractId(),successVO.getFile(),1, TimeUnit.HOURS);
            return "SUCCESS";
        }
        log.error("MD5签名验签失败");
        return "FAILER";
    }

    /**
     * 回调页面
     * @param contractId
     * @param model
     * @return
     */
    @RequestMapping(value = "callBackUI")
    public String callBackUI(String contractId, Model model) {
        model.addAttribute("pdf", redisTools.get(contractId));
        return "successUI";
    }

    /**
     * 文件转base64
     * @param filePath
     * @return
     */
    public String encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
