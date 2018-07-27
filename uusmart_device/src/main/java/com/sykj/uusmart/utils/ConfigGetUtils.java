package com.sykj.uusmart.utils;

import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.handle.PushAPPUtils;
import com.sykj.uusmart.mqtt.CallBack;
import com.sykj.uusmart.pojo.DeviceVersionInfo;
import com.sykj.uusmart.repository.CacheMessageRepository;
import com.sykj.uusmart.repository.DeviceInfoRepository;
import com.sykj.uusmart.repository.DeviceVersionInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
@Component
public class ConfigGetUtils {

    public static ServiceConfig serviceConfig;

    public static StringRedisTemplate stringRedisTemplate;

    public static DeviceInfoRepository deviceInfoRepository;


    public static PushAPPUtils pushAPPUtils;



    @Autowired(required = true)
    public  void setPushAPPUtils(PushAPPUtils pushAPPUtils) {
        ConfigGetUtils.pushAPPUtils = pushAPPUtils;
    }
    public static CallBack callBack;

    @Autowired(required = true)
    public void setCallBack(CallBack callBack) {
        ConfigGetUtils.callBack = callBack;
    }

    @Autowired(required = true)
    public void setDeviceInfoRepository(DeviceInfoRepository deviceInfoRepository) {
        ConfigGetUtils.deviceInfoRepository = deviceInfoRepository;
    }

    @Autowired(required = true)
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        ConfigGetUtils.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired(required = true)
    public void setServiceConfig(ServiceConfig serviceConfig) {
        ConfigGetUtils.serviceConfig = serviceConfig;
    }
}
