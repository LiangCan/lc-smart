package com.sykj.uusmart.http.resp;

import com.sykj.uusmart.pojo.DeviceInfo;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
public class RespDeviceListDTO {

    private Long shareInfoId;

    private Short nudStatus;

    private Short role;

    private String remarks;

    private String deviceIcon;

    private Long binDingTime;

    private Long roomId;

    DeviceInfo deviceInfo;

    public RespDeviceListDTO() {
    }

    public RespDeviceListDTO(Long shareInfoId, Short nudStatus, Short role, String remarks, Long binDingTime, DeviceInfo deviceInfo) {
        this.shareInfoId = shareInfoId;
        this.nudStatus = nudStatus;
        this.role = role;
        this.remarks = remarks;
        this.binDingTime = binDingTime;
        this.deviceInfo = deviceInfo;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getDeviceIcon() {
        return deviceIcon;
    }

    public void setDeviceIcon(String deviceIcon) {
        this.deviceIcon = deviceIcon;
    }

    public Long getShareInfoId() {
        return shareInfoId;
    }

    public void setShareInfoId(Long shareInfoId) {
        this.shareInfoId = shareInfoId;
    }

    public Short getNudStatus() {
        return nudStatus;
    }

    public void setNudStatus(Short nudStatus) {
        this.nudStatus = nudStatus;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getBinDingTime() {
        return binDingTime;
    }

    public void setBinDingTime(Long binDingTime) {
        this.binDingTime = binDingTime;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
