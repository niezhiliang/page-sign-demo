package cc.huluwa.page.sign.demo.server.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/3/7 上午11:19
 */
@Data
public class HttpContractSignerMo implements Serializable {

    private static final long serialVersionUID = -6440976482903809896L;

    /**
     * 签署用户 1.个人 2.企业
     */
    private Byte type;

    /**
     * 个人填充这个字段
     */
    private SignPersonMo personal;

    /**
     * 企业填充字段
     */
    private SignPersonMo company;

    /**
     * 签署区域
     */
    private SignAreaMo area;

}
