package com.sykj.uusmart.http.vivo.deviceList;

import com.google.gson.JsonObject;

public class GetDeviceListForVivoResponseDTO {

    private String deviceId;

    private String series;

    private String categoryCode;

    private JsonObject attachment;
//    attachment


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }


    public JsonObject getAttachment() {
        return attachment;
    }

    public void setAttachment(JsonObject attachment) {
        this.attachment = attachment;
    }
}
