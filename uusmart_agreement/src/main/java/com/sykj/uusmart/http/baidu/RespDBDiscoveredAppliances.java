/**
  * Copyright 2018 bejson.com 
  */
package com.sykj.uusmart.http.baidu;
import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2018-07-06 16:51:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RespDBDiscoveredAppliances {

    private String[] actions = {"turnOn","turnOff"};
    private String[] applianceTypes = {"SOCKET"};
    private AdditionalApplianceDetails additionalApplianceDetails = new AdditionalApplianceDetails();
    //ID
    private String applianceId;
    //介绍
    private String friendlyDescription;
    //名称
    private String friendlyName;
    //是否连接
    private boolean isReachable;
    //厂家名字
    private String manufacturerName;
    //型号名字
    private String modelName;
    //设备版本
    private String version;
    //设备属性
    private List<BDAttributesDTO> attributes = new ArrayList<>();

    public void setAdditionalApplianceDetails(AdditionalApplianceDetails additionalApplianceDetails) {
         this.additionalApplianceDetails = additionalApplianceDetails;
     }
     public AdditionalApplianceDetails getAdditionalApplianceDetails() {
         return additionalApplianceDetails;
     }

    public void setApplianceId(String applianceId) {
         this.applianceId = applianceId;
     }
     public String getApplianceId() {
         return applianceId;
     }

    public void setFriendlyDescription(String friendlyDescription) {
         this.friendlyDescription = friendlyDescription;
     }
     public String getFriendlyDescription() {
         return friendlyDescription;
     }

    public void setFriendlyName(String friendlyName) {
         this.friendlyName = friendlyName;
     }
     public String getFriendlyName() {
         return friendlyName;
     }

    public void setIsReachable(boolean isReachable) {
         this.isReachable = isReachable;
     }
     public boolean getIsReachable() {
         return isReachable;
     }

    public void setManufacturerName(String manufacturerName) {
         this.manufacturerName = manufacturerName;
     }
     public String getManufacturerName() {
         return manufacturerName;
     }

    public void setModelName(String modelName) {
         this.modelName = modelName;
     }
     public String getModelName() {
         return modelName;
     }

    public void setVersion(String version) {
         this.version = version;
     }
     public String getVersion() {
         return version;
     }

    public void setAttributes(List<BDAttributesDTO> attributes) {
         this.attributes = attributes;
     }
     public List<BDAttributesDTO> getAttributes() {
         return attributes;
     }

}