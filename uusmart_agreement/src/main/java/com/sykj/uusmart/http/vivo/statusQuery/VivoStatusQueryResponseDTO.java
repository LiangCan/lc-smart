package com.sykj.uusmart.http.vivo.statusQuery;

import com.google.gson.JsonObject;
import java.util.*;

public class VivoStatusQueryResponseDTO {

    private String deviceId;

    private Map<String,Object> properties;

    private String status;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
