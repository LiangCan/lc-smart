package com.sykj.uusmart.utils;

import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.tianmao.ReqTianMaoBaseDTO;
import com.sykj.uusmart.http.tianmao.TMQueryRespDTO;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.pojo.CacheMessage;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.repository.CacheMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/20 0020.
 */
@Component
public class ServiceGetUtils {

    public static MqIotUtils mqIotUtils;

    public static CacheMessageRepository cacheMessageRepository;

    @Autowired(required = true)
    public void setCacheMessageRepository(CacheMessageRepository cacheMessageRepository) {
        ServiceGetUtils.cacheMessageRepository = cacheMessageRepository;
    }

    @Autowired(required = true)
    public void setMqIotUtils(MqIotUtils mqIotUtils) {
        ServiceGetUtils.mqIotUtils = mqIotUtils;
    }
}

