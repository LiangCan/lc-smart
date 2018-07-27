package com.sykj.uusmart.pojo;


/**
 * Created by Liang on 2016/12/22.
 */
import javax.persistence.*;

@Entity
@Table(name="t_nexus_user_device")
public class NexusUserDevice {
    /**  关系ID */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="nud_id", length = 16)
    private Long nudId;

    @Column(name="user_id",columnDefinition=" bigint(13) COMMENT '用户ID' ")
    private Long userId;

    @Column(name="device_id",columnDefinition=" bigint(13) COMMENT '设备ID' ")
    private Long deviceId;

    @Column(name="share_info_id",columnDefinition=" bigint(13) COMMENT '分享信息ID 处于分享者角色的时候必填' ")
    private Long shareInfoId;

    @Column(name="nud_status",columnDefinition=" smallint(2) COMMENT '关系状态' ")
    private Short nudStatus;

    @Column(name="role",columnDefinition=" smallint(2) COMMENT ' 角色 ' ")
    private Short role;

    @Column(name = "remarks", columnDefinition = " varchar(32) DEFAULT NULL COMMENT '设备注释名' ")
    private String remarks;

    @Column(name = "device_icon", columnDefinition = " varchar(255) DEFAULT NULL COMMENT '图片地址' ")
    private String deviceIcon;

    @Column(name="create_time",columnDefinition=" bigint(13) COMMENT '创建时间' ")
    private Long createTime;

    @Column(name="room_id", columnDefinition=" bigint(16)   COMMENT '房间ID' ")
    private Long roomId;

    @Column(name="hid",columnDefinition=" bigint(16) COMMENT '家庭ID' ")
    private Long hid;

    public NexusUserDevice() {
    }

    public NexusUserDevice(Long userId, Long deviceId, Long shareInfoId, Short nudStatus, Short role, String remarks, Long createTime, Long hid) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.shareInfoId = shareInfoId;
        this.nudStatus = nudStatus;
        this.role = role;
        this.remarks = remarks;
        this.createTime = createTime;
        this.hid = hid;
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

    public Long getHid() {
        return hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }

    public Long getShareInfoId() {
        return shareInfoId;
    }

    public void setShareInfoId(Long shareInfoId) {
        this.shareInfoId = shareInfoId;
    }

    public Long getNudId() {
        return nudId;
    }

    public void setNudId(Long nudId) {
        this.nudId = nudId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
