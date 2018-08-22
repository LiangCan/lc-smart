package com.sykj.uusmart.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
@RefreshScope
@Configuration
public class ServiceConfig {


    @Value("${spring.redis.host}")
    private  String REDIS_HOST;

    @Value("${mqtt.urls}")
    private String MQTT_URL;

    @Value("${mqtt.client.id}")
    private String MQTT_CLIENT_NAME;

    private final  String SERVICE_LOG = "serviceLog";

    @Value("${mqtt.client.topic}")
    private  String[] SUB_MQTT_TOPIC ;

    @Value("${diy.redis.dingdong}")
    private String DINGDONG_TOKEN;

    //3 天
    @Value("${diy.redis.token.validit.time}")
    private Long LONG_TOKEN_INVALID_TIME ;

    //app:login:token
    @Value("${diy.redis.prefix.login.token}")
    private String REDIS_USER_LOGIN_TOKEN;

    //vivo.token.accessToken
    @Value("${Sykj.vivo.redisAccessTokenName}")
    private String REDIS_ACCESS_TOKEN_NAME;

    //vivo.token.refreshToken
    @Value("${Sykj.vivo.redisRefreshTokennName}")
    private String REDIS_REFRESH_TOKEN_NAME;

    @Value("${Sykj.vivo.accessTokenExpireIn}")
    private Long REDIS_ACCESS_TOKEN_INVALID_TIME;

    @Value("${Sykj.vivo.refreshTokenExpireIn}")
    private Long REDIS_REFRESH_TOKEN_INVALID_TIME;

    public String getDINGDONG_TOKEN() {
        return DINGDONG_TOKEN;
    }

    public Long getLONG_TOKEN_INVALID_TIME() {
        return LONG_TOKEN_INVALID_TIME;
    }

    public String getREDIS_USER_LOGIN_TOKEN() {
        return REDIS_USER_LOGIN_TOKEN;
    }

    public String getREDIS_HOST() {
        return REDIS_HOST;
    }

    public String[] getSUB_MQTT_TOPIC() {
        return SUB_MQTT_TOPIC;
    }

    public String getMQTT_URL() {
        return MQTT_URL;
    }

    public String getMQTT_CLIENT_NAME() {
        return MQTT_CLIENT_NAME;
    }

    public String getSERVICE_LOG() {
        return SERVICE_LOG;
    }

    public String getREDIS_ACCESS_TOKEN_NAME() {
        return REDIS_ACCESS_TOKEN_NAME;
    }

    public String getREDIS_REFRESH_TOKEN_NAME() {
        return REDIS_REFRESH_TOKEN_NAME;
    }

    public Long getREDIS_ACCESS_TOKEN_INVALID_TIME() {
        return REDIS_ACCESS_TOKEN_INVALID_TIME;
    }

    public Long getREDIS_REFRESH_TOKEN_INVALID_TIME() {
        return REDIS_REFRESH_TOKEN_INVALID_TIME;
    }
}
