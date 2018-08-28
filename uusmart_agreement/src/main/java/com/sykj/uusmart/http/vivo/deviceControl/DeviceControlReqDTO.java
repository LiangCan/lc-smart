package com.sykj.uusmart.http.vivo.deviceControl;

import com.sykj.uusmart.http.vivo.VivoCommonReqDTO;

import java.util.List;

public class DeviceControlReqDTO  extends VivoCommonReqDTO {

    private List<DeviceControlRequestDTO> devices;

    public List<DeviceControlRequestDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceControlRequestDTO> devices) {
        this.devices = devices;
    }
}
