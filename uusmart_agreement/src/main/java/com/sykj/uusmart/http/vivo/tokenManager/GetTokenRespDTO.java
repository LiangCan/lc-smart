package com.sykj.uusmart.http.vivo.tokenManager;

import com.sykj.uusmart.http.vivo.VivoCommonRespDTO;

import java.io.Serializable;

public class GetTokenRespDTO extends VivoCommonRespDTO implements Serializable {

    private static final long serialVersionUID = 394544154632582L;

    private String accessToken;

    private String refreshToken;

    private String expireIn;



    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(String expireIn) {
        this.expireIn = expireIn;
    }
}
