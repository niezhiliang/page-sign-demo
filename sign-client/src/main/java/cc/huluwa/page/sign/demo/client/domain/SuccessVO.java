package cc.huluwa.page.sign.demo.client.domain;

import lombok.Data;

@Data
public class SuccessVO {
    /**
     * 签署成功的合同文件
     */
    private String file;

    /**
     * 合同编号
     */
    private String contractId;

    /**
     * 时间戳
     */
    private Long timeStamp;

    /**
     * md5的签名
     */
    private String sign;
}
