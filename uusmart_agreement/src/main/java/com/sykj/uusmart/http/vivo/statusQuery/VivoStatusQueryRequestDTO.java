package com.sykj.uusmart.http.vivo.statusQuery;
import java.util.*;
public class VivoStatusQueryRequestDTO {
    private String deviceId;

    private List<String> properties;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}
