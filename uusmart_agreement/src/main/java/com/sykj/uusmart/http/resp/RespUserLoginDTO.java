package com.sykj.uusmart.http.resp;

import com.sykj.uusmart.pojo.UserInfo;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
public class RespUserLoginDTO {
    String token;

    UserInfo userInfo;

    public RespUserLoginDTO() {
    }

    public RespUserLoginDTO(String token, UserInfo userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
