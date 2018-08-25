package com.sykj.uusmart.hystric.impl;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.vivo.bindDervice.BindDerviceReq;
import com.sykj.uusmart.hystric.VivoServiceAPI;
import com.sykj.uusmart.hystric.WisdomServiceAPI;
import org.springframework.stereotype.Component;

@Component
public class VivoServiceAPIHystric implements VivoServiceAPI {


    @Override
    public String bindDeviceForVivo(BindDerviceReq reqBaseDTO) {
        throw new CustomRunTimeException(Constants.resultCode.THIRD_PARTY_SERVER_ERROR, Constants.systemError.THIRD_PARTY_SERVER_ERROR );

    }
}
