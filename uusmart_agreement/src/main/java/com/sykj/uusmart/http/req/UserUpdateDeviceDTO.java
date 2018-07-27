package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.req.input.AddDeivceDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
@ApiModel
public class UserUpdateDeviceDTO extends IdDTO {


    @NotNull(message = Constants.systemError.PARAM_MISS)
    @Size(max = 32, min = 2, message = Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "我的设备", required=true, value = "设备名称,L(2~32")
    private String remarkes;


    @NotNull(message = Constants.systemError.PARAM_MISS)
    @Size(max = 16, min = 1, message = Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "1.png", required=true, value = "设备图片,L(1~16")
    private String deviceIcon;

    public String getRemarkes() {
        return remarkes;
    }

    public void setRemarkes(String remarkes) {
        this.remarkes = remarkes;
    }

    public String getDeviceIcon() {
        return deviceIcon;
    }

    public void setDeviceIcon(String deviceIcon) {
        this.deviceIcon = deviceIcon;
    }
}
