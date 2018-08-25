package com.sykj.uusmart.http.vivo.statusQuery;

import com.sykj.uusmart.http.vivo.VivoCommonReqDTO;
import com.sykj.uusmart.http.vivo.deviceList.GetDeviceListForVivoResponseDTO;

import java.util.List;

public class VivoStatusQueryReqDTO extends VivoCommonReqDTO {

    List<GetDeviceListForVivoResponseDTO> devices;

    public List<GetDeviceListForVivoResponseDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<GetDeviceListForVivoResponseDTO> devices) {
        this.devices = devices;
    }
}
