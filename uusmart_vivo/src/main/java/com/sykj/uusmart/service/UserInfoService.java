package com.sykj.uusmart.service;

import com.sykj.uusmart.pojo.redis.UserLogin;

/**
 * Created by Administrator on 2018/7/7 0007.
 */
public interface UserInfoService {
    UserLogin getTokenInfo(String token, boolean check);

    Long getUserId(boolean checkNull);
}
