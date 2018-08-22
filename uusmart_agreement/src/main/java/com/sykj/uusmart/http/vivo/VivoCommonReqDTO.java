package com.sykj.uusmart.http.vivo;

import com.sykj.uusmart.Constants;
import org.hibernate.validator.constraints.NotEmpty;

public class VivoCommonReqDTO {

    @NotEmpty(message = Constants.systemError.PARAM_MISS)
    private String openId ;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
