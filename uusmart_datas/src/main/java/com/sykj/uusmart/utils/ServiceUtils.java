package com.sykj.uusmart.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Liang on 2017/1/13.
 */
@Component
public class ServiceUtils {

    public static MessageUtils messageUtils;

    @Autowired(required = true)
    public void setMessageUtils(MessageUtils messageUtils) {
        ServiceUtils.messageUtils = messageUtils;
    }
}
