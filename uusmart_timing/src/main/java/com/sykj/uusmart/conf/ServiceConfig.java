package com.sykj.uusmart.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

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

    //0.0.1
    @Value("${diy.tcp.agreement.version}")
    private  String TCP_VERSION;

    //3 天
    @Value("${diy.redis.token.validit.time}")
    private Long LONG_TOKEN_INVALID_TIME ;

    //app:login:token
    @Value("${diy.redis.prefix.login.token}")
    private String REDIS_USER_LOGIN_TOKEN;

    public String getTCP_VERSION() {
        return TCP_VERSION;
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
}
