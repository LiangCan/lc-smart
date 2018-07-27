package com.sykj.uusmart.http.tianmao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/25 0025.
 */
public class TMDevice {
    String deviceId;
    String deviceName;
    String deviceType="outlet";
    String zone="";
    String brand="慎勇";
    String model="SY-W-SOCKET-1";
    String icon="http://goodtime-iot.com/deviceIcon/1.png";
    List<Map<String, String>> properties = new ArrayList<>();
    String[]  actions = {"TurnOn","TurnOff","SetColor","SetBrightness","SetWindSpeed","SetMode"};
    TMExtensions extensions =  new TMExtensions();
    public TMDevice() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "color");
        map.put("value","Red");
        properties.add(map);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Map<String, String>> getProperties() {
        return properties;
    }

    public void setProperties(List<Map<String, String>> properties) {
        this.properties = properties;
    }

    public String[] getActions() {
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public TMExtensions getExtensions() {
        return extensions;
    }

    public void setExtensions(TMExtensions extensions) {
        this.extensions = extensions;
    }
}
