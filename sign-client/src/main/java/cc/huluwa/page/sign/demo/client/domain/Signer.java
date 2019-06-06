package cc.huluwa.page.sign.demo.client.domain;

import lombok.Data;

@Data
public class Signer {

    private String name;

    private Byte type;

    private String identity;

    private Float firstX;

    private Float firstY;

    private Float secondX;

    private Float secondY;
}
