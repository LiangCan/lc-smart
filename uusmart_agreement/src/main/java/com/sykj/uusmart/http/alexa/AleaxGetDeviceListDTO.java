package com.sykj.uusmart.http.alexa;


import com.sykj.uusmart.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by Liang on 2016/12/29.
 */
@ApiModel
public class AleaxGetDeviceListDTO {

    @Size(max = 64, min = 1, message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "token", value = "token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
