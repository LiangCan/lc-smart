package com.sykj.uusmart.hystric.impl;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.hystric.TimingServiceAPI;
import org.springframework.stereotype.Component;

@Component
public class TimingServiceAPIHystric implements TimingServiceAPI {


    @Override
    public String byDeviceDeleteAllTiming(ReqBaseDTO reqBaseDTO) {
        System.out.println( "定时服务报错；");
        throw new CustomRunTimeException(Constants.resultCode.THIRD_PARTY_SERVER_ERROR, Constants.systemError.THIRD_PARTY_SERVER_ERROR );
//        return null;
    }

    @Override
    public String test(ReqBaseDTO reqBaseDTO) {
        throw new CustomRunTimeException(Constants.resultCode.THIRD_PARTY_SERVER_ERROR, Constants.systemError.THIRD_PARTY_SERVER_ERROR );
    }
}
