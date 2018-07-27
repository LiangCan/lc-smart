package com.sykj.uusmart.pojo.redis;


import java.io.Serializable;

public class UserLogin implements Serializable {
    private static final long serialVersionUID = -1L;

    private Long userId;

    private String account;

    private String token;

    private Short loginStatus;

    private Short os;

    private Long createTime;


    public UserLogin() {
    }


    public Short getOs() {
        return os;
    }

    public void setOs(Short os) {
        this.os = os;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Short getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Short loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }




    @Override
    public String toString() {
        return "UserLogin{" +
                ", userId=" + userId +
                ", account=" + account +
                ", token='" + token + '\'' +
                ", loginStatus=" + loginStatus +
                ", createTime=" + createTime +
                '}';
    }
}
