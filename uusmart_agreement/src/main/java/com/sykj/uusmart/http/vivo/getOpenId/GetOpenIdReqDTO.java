package com.sykj.uusmart.http.vivo.getOpenId;

import com.sykj.uusmart.Constants;
import org.hibernate.validator.constraints.NotEmpty;

public class GetOpenIdReqDTO {
    @NotEmpty(message = Constants.systemError.PARAM_MISS)
    private String code ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
