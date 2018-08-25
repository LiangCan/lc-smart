package com.sykj.uusmart.service;

import com.alibaba.fastjson.JSONObject;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.vivo.tokenManager.GetTokenRespDTO;
import com.sykj.uusmart.pojo.UserInfo;

public interface VivoService {

    GetTokenRespDTO getTokenByVivoUser(String openId);

    GetTokenRespDTO refreshVivoUserToken(String openId, String refreshToken);

    String getOpenIdForVivo(String code);

    ResponseDTO userBinding(JSONObject getOpenIdJson);

    /**
     * 注册用户
     * @return
     */
    UserInfo registerUser(String vivoOpenId);

    ResponseDTO vivoUserLogin(UserInfo userInfo , String vivoOpenId);


    boolean checkAccsssTokenNicety(String openId , String accsssToken);

}
