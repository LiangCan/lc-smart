package com.sykj.uusmart.hystric.impl;

import com.sykj.uusmart.hystric.DeviceServiceAPI;
import org.springframework.stereotype.Component;

@Component
public class DeviceServiceAPIHiHystric implements DeviceServiceAPI {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}