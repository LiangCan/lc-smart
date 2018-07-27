package com.sykj.uusmart.mqtt.cmd;

/**
 * Created by Liang on 2017/1/3.
 */
public enum CmdListEnum {
    //方法返回
    factoryReset("factoryReset"),

    //登录
    login("login"),

    //控制
    control("control"),

    //信息上报
    inform("inform"),

    //信息上报
    event("event"),

    //版本信息推送
    upgrade("upgrade"),

    //心跳上报
    heartbeat("heartbeat"),

    //对象添加
    addObject("addObject"),

    //对象删除
    deleteObject("deleteObject"),

    //对象查询
    queryObject("queryObject"),
    //Get
    get("get"),

    //differentPlacesLogin
    diffLogin("diffLogin"),

    //通知
    notify("notify"),

    //Set
    set("set"),

    //离线处理
    disconn("disconn"),

    //通知刷新
    notifyRefresh("notifyRefresh");

    private  String name;

    CmdListEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
