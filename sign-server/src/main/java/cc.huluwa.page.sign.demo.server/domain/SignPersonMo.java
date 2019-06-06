package cc.huluwa.page.sign.demo.server.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/3/7 下午2:06
 */
@Data
public class SignPersonMo implements Serializable {

    private static final long serialVersionUID = -2556768657974722132L;

    /**
     * 真实姓名或公司名称
     */
    private String name;

    /**
     * 身份证号码
     */
    private String identityCode;

    /**
     * 统一信用号
     */
    private String creditCode;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 签署手机号码
     */
    private String mobile;

    /**
     * 证书密码
     */
    private String certPassword;

    /**
     * 证书别名
     */
    private String alias;
}
