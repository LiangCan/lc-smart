package com.sykj.uusmart.cmdtype.baidu;

public enum BaiDuCmdListEmun {
    query("DuerOS.ConnectedHome.Query"),
    controller("DuerOS.ConnectedHome.Control"),
    discovery("DuerOS.ConnectedHome.Discovery");
    private String value;

    BaiDuCmdListEmun(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
