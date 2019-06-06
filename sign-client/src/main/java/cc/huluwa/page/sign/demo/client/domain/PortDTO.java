package cc.huluwa.page.sign.demo.client.domain;

import lombok.Data;

/**
 * 签署区域实体类
 */
@Data
public class PortDTO {

    /**
     * 左下角的X坐标
     */
    private Float firstX;

    /**
     * 左下角的Y坐标
     */
    private Float firstY;

    /**
     * 右上角的X坐标
     */
    private Float secondX;

    /**
     * 右上角的Y坐标
     */
    private Float secondY;

    /**
     * 合同页数
     */
    private Integer imgNum;
}
