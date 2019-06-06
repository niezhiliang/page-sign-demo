package cc.huluwa.page.sign.demo.client.domain;

import lombok.Data;

import java.util.List;

/**
 * 页面签署参数
 */
@Data
public class PcSignDTO {

    /**
     * 合同编号
     */
    private String contractId;

    /**
     * 合同文件
     */
    private String fileBase64;

    /**
     * 签章图片
     */
    private String signImgBase64;

    /**
     * 签署人姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String identityCode;

    /**
     * 回调地址
     */
    private String callBackUrl;

    /**
     * 回调页面
     */
    private String callPage;

    /**
     * 签署区域
     */
    private List<PortDTO> ports;

}
