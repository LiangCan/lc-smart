package com.sykj.uusmart.http.req.input;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.NameDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

/**
 * Created by Administrator on 2018/5/10 0010.
 */
@ApiModel
public class AddDeivceDTO extends NameDTO{


    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Size(max = 32, min = 10, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "60019401CDC1", required=true, value = "mac地址,L(10~32)")
    private String deviceAddress;//设备mac地址

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Min(value = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value = 16, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "1", required = true, value = "产品ID,L(1~16)")
    private Long pid;


    @Min(value = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value = 16, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "0", value = "主设备的ID,L(1~16)")
    private Long mainDeviceId;

    @Min(value = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value = 16, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "1",  value = "本地设备ID,L(1~16)")
    private Long locaDid;

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Size(max = 16, min = 2, message =Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "3.1.0", required=true, value = "设备版本,L(2~16)")
    private String deviceVersion;


    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getMainDeviceId() {
        return mainDeviceId;
    }

    public void setMainDeviceId(Long mainDeviceId) {
        this.mainDeviceId = mainDeviceId;
    }

    public Long getLocaDid() {
        return locaDid;
    }

    public void setLocaDid(Long locaDid) {
        this.locaDid = locaDid;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }
}
