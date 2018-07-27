package com.sykj.uusmart.hystric.impl;

import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.hystric.TimingServiceAPI;
import org.springframework.stereotype.Component;

@Component
public class TimingServiceAPIHystric implements TimingServiceAPI {


    @Override
    public ResponseDTO byDeviceDeleteAllTiming(ReqBaseDTO reqBaseDTO) {
        return null;
    }
}
