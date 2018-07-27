package com.sykj.uusmart.pojo.redis;


/**
 * Created by Liang on 2016/12/22.
 */
import javax.persistence.*;


public class UserCheckCode {

    private Long codeId;

    private String address;


    private Integer checkCode;


    private Short codeType;


    private Short codeStatus;


    private Long createTime;


    public UserCheckCode() {
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(Integer checkCode) {
        this.checkCode = checkCode;
    }

    public Short getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(Short codeStatus) {
        this.codeStatus = codeStatus;
    }

    public Short getCodeType() {
        return codeType;
    }

    public void setCodeType(Short codeType) {
        this.codeType = codeType;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
