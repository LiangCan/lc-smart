package com.sykj.uusmart.http.alexa;

import java.util.List;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
public class RespAlexaAddDeviceDTO {
    /**
     * 设备Id
     */
    private String applianceId;

    private String manufacturerName = "DotSdon";

    private String version = "1.0";

    /**
     * 设备名称
     */
    private String friendlyName;
    /**
     * 是否在线
     */
    private boolean isReachable;

    /**
     * 描述
     */
    private String friendlyDescription;

    /**
     * 支持的指令
     */
    private List<String> actions ;

    private String modelName;

    public String getModelName() {
        return modelName;
    }

    public String getFriendlyDescription() {
        return friendlyDescription;
    }

    public void setFriendlyDescription(String friendlyDescription) {
        this.friendlyDescription = friendlyDescription;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public void setReachable(boolean reachable) {
        isReachable = reachable;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }
}
