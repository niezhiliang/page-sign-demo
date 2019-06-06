package cc.huluwa.page.sign.demo.server.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SuccessVO {

    private String file;

    private String contractId;

    private Long timeStamp;

    private String sign;
}
