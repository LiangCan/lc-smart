package com.sykj.uusmart.cmdtype.tianmao;

/**
 * Created by Administrator on 2018/6/29 0029.
 */
public enum TiamMaoCmdListEmun {
    query("AliGenie.Iot.Device.Query"),
    controller("AliGenie.Iot.Device.Control"),
    discovery("AliGenie.Iot.Device.Discovery");
    private String value;

    TiamMaoCmdListEmun( String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
