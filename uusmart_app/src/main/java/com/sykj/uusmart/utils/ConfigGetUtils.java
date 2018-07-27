package com.sykj.uusmart.utils;

import com.sykj.uusmart.conf.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
@Component
public class ConfigGetUtils {

    public static ServiceConfig serviceConfig;

    @Autowired(required = true)
    public void setServiceConfig(ServiceConfig serviceConfig) {
        ConfigGetUtils.serviceConfig = serviceConfig;
    }
}
