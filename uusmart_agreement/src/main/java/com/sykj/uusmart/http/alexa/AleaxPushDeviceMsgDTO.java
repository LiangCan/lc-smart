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
public class AleaxPushDeviceMsgDTO {
    @Size(max = 64, min = 1, message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "token", value = "token")
    private String token;

    @Size(max = 64, min = 1, message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "1", value = "设备ID")
    private String deviceId;

    @NotEmpty(message =Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "off", value = "指令")
    private String cmd;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
