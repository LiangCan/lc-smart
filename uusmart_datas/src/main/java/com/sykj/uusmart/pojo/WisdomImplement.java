package com.sykj.uusmart.pojo;

/**
 * Created by Liang on 2017/3/22.
 */
import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="t_wisdom_implement")
public class WisdomImplement {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="wi_id", length = 16)
    private Long wiid;

    @Column(name="userId",columnDefinition=" bigint(16) COMMENT '用户ID' ")
    private Long userId;

    @Column(name="wid",columnDefinition=" bigint(16) COMMENT '智能ID' ")
    private Long wid;

    @Column(name="id",columnDefinition=" bigint(16) COMMENT '执行的ID' ")
    private Long id;

    @Column(name="create_time",columnDefinition=" bigint(13)   COMMENT '创建时间' ")
    private Long createTime;

    @Column(name="implement_status", columnDefinition=" smallint(2)   COMMENT '状态 1：打开 9：无效' ")
    private Short implementStatus;

    @Column(name="implement_type", columnDefinition=" smallint(2)   COMMENT '类型：1 设备执行' ")
    private Short implementType;

    @Column(name="implement_name", columnDefinition=" varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行名称' ")
    private String implementName;

    @Column(name="appointment", columnDefinition=" varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行约定' ")
    private String appointment;

    @Column(name="implement_value", columnDefinition=" varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行的值' ")
    private String implementValue;

//    @Column(name="update_num",columnDefinition=" bigint(4)   COMMENT '修改次数' ")
//    private int updateNum;



    public WisdomImplement() {
    }

    public WisdomImplement(Long id,Long wiid, Short implementType) {
        this.id = id;
        this.wiid = wiid;
        this.implementType = implementType;
    }

    public WisdomImplement(BigInteger id, Short implementType, String implementName, String implementValue) {
        this.id = id.longValue();
        this.implementType = implementType;
        this.implementName = implementName;
        this.implementValue = implementValue;
    }

//    public int getUpdateNum() {
//        return updateNum;
//    }
//
//    public void setUpdateNum(int updateNum) {
//        this.updateNum = updateNum;
//    }

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public Short getImplementStatus() {
        return implementStatus;
    }

    public void setImplementStatus(Short implementStatus) {
        this.implementStatus = implementStatus;
    }

    public Short getImplementType() {
        return implementType;
    }

    public void setImplementType(Short implementType) {
        this.implementType = implementType;
    }

    public String getImplementName() {
        return implementName;
    }

    public void setImplementName(String implementName) {
        this.implementName = implementName;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getImplementValue() {
        return implementValue;
    }

    public void setImplementValue(String implementValue) {
        this.implementValue = implementValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWiid() {
        return wiid;
    }

    public void setWiid(Long wiid) {
        this.wiid = wiid;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
