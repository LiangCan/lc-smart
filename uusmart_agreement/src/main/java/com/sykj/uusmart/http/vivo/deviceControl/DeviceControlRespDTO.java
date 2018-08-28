package com.sykj.uusmart.http.vivo.deviceControl;

import java.util.List;
import java.util.Map;

public class DeviceControlRespDTO {

    private List<Map< String,String>> devices;

    public List<Map<String, String>> getDevices() {
        return devices;
    }

    public void setDevices(List<Map<String, String>> devices) {
        this.devices = devices;
    }
}
