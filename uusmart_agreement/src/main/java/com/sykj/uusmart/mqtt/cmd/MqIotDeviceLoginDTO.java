package com.sykj.uusmart.mqtt.cmd;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.utils.RegularExpressionUtils;

import javax.validation.constraints.*;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotDeviceLoginDTO extends MqIotBodyBaseDTO{

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=12, max =12, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Pattern(regexp= RegularExpressionUtils.NUMBER_SIXTEEN , message= Constants.systemError.PARAM_VALUE_INVALID )
    private String mac;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=2, max =15, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Pattern(regexp= RegularExpressionUtils.NUMBER_DIAN , message= Constants.systemError.PARAM_VALUE_INVALID )
    private String swVer;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=2, max =15, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Pattern(regexp= RegularExpressionUtils.NUMBER_DIAN , message= Constants.systemError.PARAM_VALUE_INVALID )
    private String hwVer;



    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSwVer() {
        return swVer;
    }

    public void setSwVer(String swVer) {
        this.swVer = swVer;
    }

    public String getHwVer() {
        return hwVer;
    }

    public void setHwVer(String hwVer) {
        this.hwVer = hwVer;
    }
}
