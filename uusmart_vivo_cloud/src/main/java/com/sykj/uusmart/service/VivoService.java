package com.sykj.uusmart.service;

import com.alibaba.fastjson.JSONObject;
import com.sykj.uusmart.http.vivo.VivoCommonRespDTO;
import com.sykj.uusmart.http.vivo.getOpenId.GetOpenIdReqDTO;
import com.sykj.uusmart.http.vivo.tokenManager.GetTokenRespDTO;

public interface VivoService {

    GetTokenRespDTO getTokenByVivoUser(String openId);

    GetTokenRespDTO refreshVivoUserToken(String openId, String refreshToken);

    String getOpenIdForVivo(String code);

    GetTokenRespDTO userBinding(JSONObject getOpenIdJson);

    /**
     * 注册用户
     * @return
     */
    int registerUser(String vivoOpenId);

    GetTokenRespDTO vivoUserLogin(String vivoOpenId);

    GetTokenRespDTO bindDerviceforVivo(String openId, String deviceId);
}
