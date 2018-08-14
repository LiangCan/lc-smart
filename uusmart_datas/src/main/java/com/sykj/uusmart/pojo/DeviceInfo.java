package com.sykj.uusmart.pojo;

import javax.persistence.*;

@Entity
@Table(name="t_device_info")
public class DeviceInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="device_id", length = 16)
    private Long deviceId;

    @Column(name="device_wifi_ssid", columnDefinition=" varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '超级精灵绑定的wifi ssid' ")
    private String deviceWifiSSID;

    @Column(name="device_address", columnDefinition=" varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '设备mac地址' ")
    private String deviceAddress;//

    @Column(name="device_name", columnDefinition=" varchar(32)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL  COMMENT '设备名称' ")
    private String deviceName;

    @Column(name="version_info", columnDefinition=" varchar(1024)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL  COMMENT '版本信息' ")
    private String versionInfo;

    @Column(name="status_info", columnDefinition=" varchar(1024)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL  COMMENT '状态信息' ")
    private String statusInfo;

    @Column(name="device_status", columnDefinition=" smallint(2)  COMMENT '设备在线离线状态' ")
    private Short deviceStatus;

    @Column(name="user_id", columnDefinition=" bigint(16)  COMMENT '创建用户' ")
    private Long userId;

    @Column(name="create_time", columnDefinition=" bigint(13)  COMMENT '创建时间' ")
    private Long createTime;

    @Column(name="lately_login_time", columnDefinition=" bigint(13)  COMMENT '最近登录时间' ")
    private Long latelyLoginTime;

    @Column(name="use_total_time", columnDefinition=" bigint(13)  COMMENT '使用的总时间' ")
    private Long useTotalTime;

    @Column(name="classification", columnDefinition=" smallint(2)  COMMENT '设备类别' ")
    private Short classification;

    @Column(name="main_device_id", columnDefinition=" bigint(16)  COMMENT '主Id' ")
    private Long mainDeviceId;

    @Column(name="loca_did", columnDefinition=" bigint(16)  COMMENT '本地Id' ")
    private Long locaDid;

    @Column(name="hid", columnDefinition=" bigint(16)   COMMENT '所属家庭ID' ")
    private Long hid;


    @Column(name="product_id", columnDefinition=" bigint(16)  COMMENT '产品ID' ")
    private Long productId;

    public DeviceInfo() {
    }

    public Long getLocaDid() {
        return locaDid;
    }

    public void setLocaDid(Long locaDid) {
        this.locaDid = locaDid;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }


    public Long getLatelyLoginTime() {
        return latelyLoginTime;
    }

    public void setLatelyLoginTime(Long latelyLoginTime) {
        this.latelyLoginTime = latelyLoginTime;
    }

    public Long getUseTotalTime() {
        return useTotalTime;
    }

    public void setUseTotalTime(Long useTotalTime) {
        this.useTotalTime = useTotalTime;
    }

    public Long getMainDeviceId() {
        return mainDeviceId;
    }

    public void setMainDeviceId(Long mainDeviceId) {
        this.mainDeviceId = mainDeviceId;
    }

    public Long getHid() {
        return hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }
    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Short getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Short deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public Short getClassification() {
        return classification;
    }

    public void setClassification(Short classification) {
        this.classification = classification;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getDeviceWifiSSID() {
        return deviceWifiSSID;
    }

    public void setDeviceWifiSSID(String deviceWifiSSID) {
        this.deviceWifiSSID = deviceWifiSSID;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
