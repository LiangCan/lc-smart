package com.sykj.uusmart.service;


import com.sykj.uusmart.http.AccountDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserGetCheckCodeDTO;
import com.sykj.uusmart.http.req.UserLoginDTO;
import com.sykj.uusmart.http.req.UserRegisterDTO;
import com.sykj.uusmart.pojo.redis.UserLogin;

/**
 * 用户的服务层
 */
public interface UserInfoService {


    UserLogin getTokenInfo(String token, boolean checkNull);

    Long getUserId(boolean checkNull);
    
}
