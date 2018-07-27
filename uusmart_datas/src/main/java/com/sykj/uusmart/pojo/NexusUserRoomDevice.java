package com.sykj.uusmart.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Administrator on 2018/5/24 0024.
 */
public class NexusUserRoomDevice {
    /**
     * 关系ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nurd_id", length = 16)
    private Long nurdId;

    @Column(name = "user_id", columnDefinition = " bigint(13) COMMENT '用户ID' ")
    private Long userId;

    @Column(name = "room_id", columnDefinition = " bigint(13) COMMENT '房间ID' ")
    private Long roomId;

    @Column(name = "share_id", columnDefinition = " bigint(13) COMMENT '分享ID' ")
    private Long shareId;

    @Column(name = "device_id", columnDefinition = " bigint(13) COMMENT '设备ID' ")
    private Long deviceId;

    @Column(name = "nud_status", columnDefinition = " smallint(2) COMMENT '状态' ")
    private Short nurdStatus;

    @Column(name = "create_time", columnDefinition = " bigint(13) COMMENT '创建时间' ")
    private Long createTime;

    public NexusUserRoomDevice() {
    }


    public NexusUserRoomDevice(Long userId, Long roomId, Long shareId, Long deviceId, Short nurdStatus, Long createTime) {
        this.userId = userId;
        this.roomId = roomId;
        this.shareId = shareId;
        this.deviceId = deviceId;
        this.nurdStatus = nurdStatus;
        this.createTime = createTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getNurdId() {
        return nurdId;
    }

    public void setNurdId(Long nurdId) {
        this.nurdId = nurdId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Short getNurdStatus() {
        return nurdStatus;
    }

    public void setNurdStatus(Short nurdStatus) {
        this.nurdStatus = nurdStatus;
    }
}
