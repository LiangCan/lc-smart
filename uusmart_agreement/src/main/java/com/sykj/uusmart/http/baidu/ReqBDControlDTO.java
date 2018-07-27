/**
  * Copyright 2018 bejson.com 
  */
package com.sykj.uusmart.http.baidu;


import java.util.Map;

public class ReqBDControlDTO {

    private Map<String, String> additionalApplianceDetails;
    private String applianceId;

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public Map<String, String> getAdditionalApplianceDetails() {
        return additionalApplianceDetails;
    }

    public void setAdditionalApplianceDetails(Map<String, String> additionalApplianceDetails) {
        this.additionalApplianceDetails = additionalApplianceDetails;
    }
}