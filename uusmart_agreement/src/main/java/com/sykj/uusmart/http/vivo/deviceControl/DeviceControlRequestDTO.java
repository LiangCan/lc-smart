package com.sykj.uusmart.http.vivo.deviceControl;
import  java.util.*;

public class DeviceControlRequestDTO {

    private String deviceId;

    private Map<String,String> properties;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
