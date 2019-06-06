package cc.huluwa.page.sign.demo.server.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/3/7 下午1:56
 */
@Data
public class SignAreaMo implements Serializable {

    private static final long serialVersionUID = -3286742555955611109L;

    private Float firstX;

    private Float firstY;

    private Float secondX;

    private Float secondY;

    /**
     * 签署图片
     */
    private String signPic;

    /**
     * 签署理由
     */
    private String reason;

    /**
     * 签署位置
     */
    private String location;

    /**
     * 签署文件固定值
     */
    private String signFiled;

    /**
     * pdf签署的页数
     */
    private Integer page;
}
