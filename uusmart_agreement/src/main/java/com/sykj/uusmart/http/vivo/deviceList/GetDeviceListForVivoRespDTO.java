package com.sykj.uusmart.http.vivo.deviceList;

import java.util.List;

public class GetDeviceListForVivoRespDTO {

    List<GetDeviceListForVivoResponseDTO> devices;

    public List<GetDeviceListForVivoResponseDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<GetDeviceListForVivoResponseDTO> devices) {
        this.devices = devices;
    }
}
