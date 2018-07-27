package com.sykj.uusmart.http.dingdong;

import com.sykj.uusmart.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

@ApiModel
public class DingDongCmdDTO {

    @Length(min = 1, max = 32, message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "小智", value = "设备名称")
    private String deviceName;

    @ApiModelProperty(example = "回家", value = "场景")
    private String sceneName;

    @Length(min = 1, max = 8, message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "off", value = "指令")
    private String cmd;

    @ApiModelProperty(example = "true", value = "是否结束回话")
    private String close;

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCmd() {
        return cmd;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "DingDongCmdDTO{" +
                "deviceName='" + deviceName + '\'' +
                ", cmd='" + cmd + '\'' +
                ", sceneName='" + sceneName + '\'' +
                ", close='" + close + '\'' +
                '}';
    }
}