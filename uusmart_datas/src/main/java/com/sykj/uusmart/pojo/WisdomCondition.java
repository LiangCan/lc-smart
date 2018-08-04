package com.sykj.uusmart.pojo;

/**
 * Created by Liang on 2017/3/22.
 */
import javax.persistence.*;

@Entity
@Table(name="t_wisdom_condition")
public class WisdomCondition {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="wc_id", length = 16)
    private Long wcid;

    @Column(name="userId",columnDefinition=" bigint(16) COMMENT '用户ID' ")
    private Long userId;

    @Column(name="wid",columnDefinition=" bigint(16) COMMENT '智能ID' ")
    private Long wid;

    @Column(name="id",columnDefinition=" bigint(16) COMMENT '设备ID' ")
    private Long id;

    @Column(name="create_time",columnDefinition=" bigint(13)   COMMENT '创建时间' ")
    private Long createTime;

    @Column(name="condition_status", columnDefinition=" smallint(2)   COMMENT '状态 1：正常 9：无效' ")
    private Short conditionStatus;

    @Column(name="condition_type", columnDefinition=" smallint(2)   COMMENT '类型：1 点击,2设备触发' ")
    private Short conditionType;

    @Column(name="condition_name", columnDefinition=" varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件名称' ")
    private String conditionName;

    @Column(name="appointment", columnDefinition=" varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件约定' ")
    private String appointment;

    @Column(name="condition_value", columnDefinition=" varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件值' ")
    private String conditionValue;

//    @Column(name="update_num",columnDefinition=" bigint(4)   COMMENT '修改次数' ")
//    private int updateNum;

    public WisdomCondition() {
    }

    public WisdomCondition(Long id, Long wcid, Short conditionType) {
        this.wcid = wcid;
        this.id = id;
        this.conditionType = conditionType;
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

    public Long getWcid() {
        return wcid;
    }

    public void setWcid(Long wcid) {
        this.wcid = wcid;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Short getConditionStatus() {
        return conditionStatus;
    }

    public void setConditionStatus(Short conditionStatus) {
        this.conditionStatus = conditionStatus;
    }

    public Short getConditionType() {
        return conditionType;
    }

    public void setConditionType(Short conditionType) {
        this.conditionType = conditionType;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }
}
