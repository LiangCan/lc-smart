package com.sykj.uusmart.pojo;

import javax.persistence.*;

@Entity
@Table(name="t_device_timing_info")
public class DeviceTimingInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="dtid", length = 16)
    private Long dtid;

    @Column(name="dt_name", columnDefinition=" varchar(32) DEFAULT NULL COMMENT ' 任务的名字 ' ")
    private String dtName;

    @Column(name="start_info", columnDefinition=" longtext DEFAULT NULL COMMENT ' 启动的信息 ' ")
    private String startInfo;

    @Column(name="end_info", columnDefinition=" longtext DEFAULT NULL COMMENT ' 结束的信息 ' ")
    private String endInfo;

    @Column(name="dt_mode", columnDefinition=" varchar(32) DEFAULT NULL COMMENT ' 模型 ' ")
    private String dtMode;

    @Column(name="dt_days", columnDefinition=" varchar(8) DEFAULT NULL COMMENT ' 执行日期 ' ")
    private String dtDays;

    @Column(name="create_time", columnDefinition="  bigint(13) DEFAULT NULL COMMENT ' 创建的时间 ' ")
    private Long createTime;

    @Column(name="user_id", columnDefinition=" bigint(16) DEFAULT NULL COMMENT ' 用户ID ' ")
    private Long userId;

    @Column(name="device_id", columnDefinition=" bigint(16) DEFAULT NULL COMMENT ' 设备ID ' ")
    private Long deviceId;

    @Column(name="dt_status", columnDefinition=" smallint(2) DEFAULT NULL COMMENT ' 任务的状态 ' ")
    private Short dtStatus;

    @Column(name="update_num", columnDefinition=" bigint(4) DEFAULT NULL COMMENT ' 任务定时 ' ")
    private Integer updateNum;

    public void setDtStatus(Short dtStatus) {
        this.dtStatus = dtStatus;
    }

    public Integer getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(Integer updateNum) {
        this.updateNum = updateNum;
    }

    public String getDtDays() {
        return dtDays;
    }

    public void setDtDays(String dtDays) {
        this.dtDays = dtDays;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public void setDtStatus(short dtStatus) {
        this.dtStatus = dtStatus;
    }

    public String getDtMode() {
        return dtMode;
    }

    public void setDtMode(String dtMode) {
        this.dtMode = dtMode;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDtid() {
        return dtid;
    }

    public void setDtid(Long dtid) {
        this.dtid = dtid;
    }

    public String getDtName() {
        return dtName;
    }

    public void setDtName(String dtName) {
        this.dtName = dtName;
    }

    public String getStartInfo() {
        return startInfo;
    }

    public void setStartInfo(String startInfo) {
        this.startInfo = startInfo;
    }

    public String getEndInfo() {
        return endInfo;
    }

    public void setEndInfo(String endInfo) {
        this.endInfo = endInfo;
    }
}
