package cc.huluwa.page.sign.demo.client.domain;

import lombok.Data;

import java.util.List;

@Data
public class PcSignDTO {

    private String contractId;

    private String fileBase64;

    private String signImgBase64;

    private String name;

    private String identityCode;

    private String callBackUrl;

    private String callPage;

    private List<PortDTO> ports;

}
