package com.sykj.uusmart.http.vivo.deviceControl;

public class DeviceControlRequestDTO {

    private String deviceId;

    private DevicePropertiesData properties;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DevicePropertiesData getProperties() {
        return properties;
    }

    public void setProperties(DevicePropertiesData properties) {
        this.properties = properties;
    }
}
