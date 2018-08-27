package com.sykj.uusmart.http.vivo.statusQuery;
import java.util.List;

public class VivoStatusQueryRespDTO {

    private List<VivoStatusQueryResponseDTO> devices;

    public List<VivoStatusQueryResponseDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<VivoStatusQueryResponseDTO> devices) {
        this.devices = devices;
    }
}
