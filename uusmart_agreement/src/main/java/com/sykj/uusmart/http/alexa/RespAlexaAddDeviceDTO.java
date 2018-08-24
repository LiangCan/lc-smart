package com.sykj.uusmart.http.alexa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
public class RespAlexaAddDeviceDTO {
    /**
     * 设备Id
     */
    private String endpointId;

    /**
     * 厂商
     */
    private String manufacturerName = "DotStone";

    private Map<String, String> cookie  = new HashMap<>();
    /**
     * 设备名称
     */
    private String friendlyName ;
    /**
     * 描述
     */
    private String description = "DotStone Device";

    private String displayCategories []= {"SMARTPLUG"};

    private  List<Map<String, String>> capabilities ;


    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getDisplayCategories() {
        return displayCategories;
    }

    public void setDisplayCategories(String[] displayCategories) {
        this.displayCategories = displayCategories;
    }

    public List<Map<String, String>> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<Map<String, String>> capabilities) {
        this.capabilities = capabilities;
    }

    public Map<String, String> getCookie() {
        return cookie;
    }

    public void setCookie(Map<String, String> cookie) {
        this.cookie = cookie;
    }
}
