package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.pojo.ServiceLog;
import com.sykj.uusmart.pojo.redis.UserLogin;
import com.sykj.uusmart.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class UserInfoServiceImpl implements UserInfoService  {


    private  Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ServiceConfig serviceConfig;

    /**
     * 校验token
     * @param token
     * @param check 是否校验为空，如果是 会抛出异常
     * @return
     */
    @Override
    public  UserLogin getTokenInfo(String token, boolean check) {
        UserLogin userLogin = (UserLogin) redisTemplate.opsForValue().get(serviceConfig.getREDIS_USER_LOGIN_TOKEN() + Constants.specialSymbol.COLOM + token);

        if (userLogin == null ) {
            if(check){
                throw new CustomRunTimeException(Constants.resultCode.API_LOGIN_TOKEN_INVALID, Constants.systemError.API_LOGIN_TOKEN_INVALID);
            }
            return null;
        }
        //刷新token
        redisTemplate.opsForValue().set(serviceConfig.getREDIS_USER_LOGIN_TOKEN(), userLogin, serviceConfig.getLONG_TOKEN_INVALID_TIME(), TimeUnit.DAYS);
        return userLogin;
    }

    /**
     * 获取uid
     * @param
     * @return
     */
    @Override
    public Long getUserId(boolean checkNull){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        ServiceLog serviceLog = (ServiceLog) request.getAttribute(serviceConfig.getSERVICE_LOG());
        CustomRunTimeException.checkNull(serviceLog,"serviceLog");
        if(checkNull){
            if(serviceLog.getUserId() == null || serviceLog.getUserId() < 1){
                throw new CustomRunTimeException(Constants.resultCode.API_LOGIN_TOKEN_INVALID, Constants.systemError.API_LOGIN_TOKEN_INVALID);
            }
        }
        return serviceLog.getUserId();

    }
}
