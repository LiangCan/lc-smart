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
    //3 天
    @Value("${diy.redis.token.validit.time}")
    private Long LONG_TOKEN_INVALID_TIME ;

    //app:login:token
    @Value("${diy.redis.prefix.login.token}")
    private String REDIS_USER_LOGIN_TOKEN;

    //app:check_code
    @Value("${diy.redis.prefix.check_code}")
    private String REDIS_USER_CHECK_CODE;

    //5 分钟
    @Value("${diy.redis.check_code.validit.time}")
    private Integer CHECK_CODE_INVALID_TIME;

    //app:check_email
    @Value("${diy.redis.prefix.check_email_number}")
    private String REDIS_USER_EMAIL_NUMBER;

    //5 条每天
    @Value("${diy.redis.check_code.validit.number}")
    private Integer CHECK_CODE_INVALID_NUMBER;

    //MQTT每条消息的等待响应超时时间 2秒
    @Value("${dir.mqtt.push.msg.timeOut}")
    private Integer MQTT_PUSH_MSG_TIMEOUT;

    //MQTT每条消息重发次数为 2次
    @Value("${dir.mqtt.repush.msg.number}")
    private Integer MQTT_REPUSH_MSG_NUMBER;

    //app:ssid
    @Value("${diy.redis.prefix.appssid}")
    private  String REDIS_APPSSID;

    //0.0.1
    @Value("${diy.http.agreement.version}")
    private  String HTTP_VERSION;

    @Value("${mqtt.urls}")
    private String MQTT_URL;

    @Value("${mqtt.client.id}")
    private String MQTT_CLIENT_NAME;

    private final  String SERVICE_LOG = "serviceLog";

    @Value("${mqtt.client.topic}")
    private  String[] SUB_MQTT_TOPIC ;

    public Integer getMQTT_PUSH_MSG_TIMEOUT() {
        return MQTT_PUSH_MSG_TIMEOUT;
    }

    public Integer getMQTT_REPUSH_MSG_NUMBER() {
        return MQTT_REPUSH_MSG_NUMBER;
    }

    public String[] getSUB_MQTT_TOPIC() {
        return SUB_MQTT_TOPIC;
    }

    public Long getLONG_TOKEN_INVALID_TIME() {
        return LONG_TOKEN_INVALID_TIME;
    }

    public String getREDIS_USER_LOGIN_TOKEN() {
        return REDIS_USER_LOGIN_TOKEN;
    }

    public String getREDIS_USER_CHECK_CODE() {
        return REDIS_USER_CHECK_CODE;
    }

    public Integer getCHECK_CODE_INVALID_TIME() {
        return CHECK_CODE_INVALID_TIME;
    }

    public String getREDIS_USER_EMAIL_NUMBER() {
        return REDIS_USER_EMAIL_NUMBER;
    }

    public Integer getCHECK_CODE_INVALID_NUMBER() {
        return CHECK_CODE_INVALID_NUMBER;
    }

    public String getREDIS_APPSSID() {
        return REDIS_APPSSID;
    }

    public String getHTTP_VERSION() {
        return HTTP_VERSION;
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
