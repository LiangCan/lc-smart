package com.sykj.uusmart.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="t_room_info")
public class RoomInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="room_id", length = 16)
    private Long roomId;

    @Column(name="user_id", columnDefinition=" bigint(16)  COMMENT '创建用户' ")
    private Long userId;

    @Column(name="create_time", columnDefinition=" bigint(13)  COMMENT '创建时间' ")
    private Long createTime;

    @Column(name="room_type", columnDefinition=" smallint(2)  COMMENT '房间类型' ")
    private Short roomType;

    @Column(name="room_name", columnDefinition=" varchar(32) DEFAULT NULL COMMENT '房间名' ")
    private String roomName;


    @Column(name="room_icon", columnDefinition=" varchar(32) DEFAULT NULL COMMENT '房间图片标识' ")
    private String roomIcon;

    @Column(name="hid", columnDefinition=" bigint(16)   COMMENT '所属家庭ID' ")
    private Long hid;

//    @Transient
//    private List<NexusUserRoomDevice> lnurd;

    public RoomInfo() {
    }

    public RoomInfo(Long userId, Long createTime, Short roomType, String roomName,  String roomIcon, Long hid) {
        this.userId = userId;
        this.createTime = createTime;
        this.roomType = roomType;
        this.roomName = roomName;
        this.roomIcon = roomIcon;
        this.hid = hid;
    }

    public String getRoomIcon() {
        return roomIcon;
    }

    public void setRoomIcon(String roomIcon) {
        this.roomIcon = roomIcon;
    }


    public Long getHid() {
        return hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Short getRoomType() {
        return roomType;
    }

    public void setRoomType(Short roomType) {
        this.roomType = roomType;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


}
