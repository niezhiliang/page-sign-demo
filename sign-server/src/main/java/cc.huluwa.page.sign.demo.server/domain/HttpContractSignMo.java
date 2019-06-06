package cc.huluwa.page.sign.demo.server.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/3/7 上午11:16
 */
@Data
public class HttpContractSignMo implements Serializable {

    private static final long serialVersionUID = -3673646248283240646L;

    /**
     * 合同标题
     */
    private String title;

    /**
     * pdf文件
     */
    private String content;

    /**
     * 是否公证
     */
    private Byte isNotarial;

    /**
     * 签署的appId
     */
    private String appId;

    /**
     * 签署的appSecret
     */
    private String appSecret;

    /**
     * 签署人信息
     */
    private HttpContractSignerMo signer;


}
