package com.sykj.uusmart.http.vivo.bindDervice;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.vivo.VivoCommonReqDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BindDerviceReq extends VivoCommonReqDTO {

    @NotEmpty(message = Constants.systemError.PARAM_MISS)
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
