package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.IdDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2018/6/30 0030.
 */
public class UserAddRoomDeviceDTO extends IdDTO{

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "1,2", required = true, value = "设备ID集合,L(1~255)")
    @Size(max = 255, min = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    private String deviceIds;

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }
}
