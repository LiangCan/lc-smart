package com.sykj.uusmart.http.vivo.statusQuery;

import com.sykj.uusmart.http.vivo.VivoCommonReqDTO;
import com.sykj.uusmart.http.vivo.deviceList.GetDeviceListForVivoResponseDTO;
import io.swagger.annotations.ApiModel;

import java.util.List;
@ApiModel
public class VivoStatusQueryReqDTO extends VivoCommonReqDTO {

    List<VivoStatusQueryRequestDTO> devices;

    public List<VivoStatusQueryRequestDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<VivoStatusQueryRequestDTO> devices) {
        this.devices = devices;
    }
}
