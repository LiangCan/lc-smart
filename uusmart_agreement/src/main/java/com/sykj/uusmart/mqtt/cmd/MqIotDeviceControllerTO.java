package com.sykj.uusmart.mqtt.cmd;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.utils.RegularExpressionUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotDeviceControllerTO extends MqIotBodyBaseDTO{
    private Map<String, String> parmMaps;

    public Map<String, String> getParmMaps() {
        return parmMaps;
    }

    public void setParmMaps(Map<String, String> parmMaps) {
        this.parmMaps = parmMaps;
    }
}
