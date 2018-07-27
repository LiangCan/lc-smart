package com.sykj.uusmart.http.dingdong;

public class DingDongStateDTO {
    private String operation;
    private String userid;
    private Long timestamp;
    private String appid;
    private Integer isowner;

    public DingDongStateDTO() {
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Integer getIsowner() {
        return isowner;
    }

    public void setIsowner(Integer isowner) {
        this.isowner = isowner;
    }
}