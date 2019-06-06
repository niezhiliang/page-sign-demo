package cc.huluwa.page.sign.demo.client.domain;

import lombok.Data;

@Data
public class SuccessVO {

    private String file;

    private String contractId;

    private Long timeStamp;

    private String sign;
}
