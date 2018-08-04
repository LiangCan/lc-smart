package com.sykj.uusmart.pojo;

/**
 * Created by Liang on 2017/3/22.
 */
import javax.persistence.*;

@Entity
@Table(name="t_wisdom_info")
public class Wisdom {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="wid", length = 16)
    private Long wid;

    @Column(name="userId",columnDefinition=" bigint(16) COMMENT '用户ID' ")
    private Long userId;

    @Column(name="hid",columnDefinition=" bigint(16) COMMENT '家庭ID' ")
    private Long hid;

    @Column(name="wisdom_icon",columnDefinition=" varchar(128) COMMENT '图片地址' ")
    private String wisdomIcon;

    @Column(name="wisdom_name", columnDefinition=" varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '家庭名称' ")
    private String wisdomName;

    @Column(name="create_time",columnDefinition=" bigint(13)   COMMENT '创建时间' ")
    private Long createTime;

    @Column(name="and_or_run", columnDefinition=" smallint(2)   COMMENT '1  一起执行，2 满足其中一个就执行' ")
    private Short andOr;

    @Column(name="wisdom_status", columnDefinition=" smallint(2)   COMMENT '状态 1：打开 9：关闭' ")
    private Short wisdomStatus;

    @Column(name="wisdom_type", columnDefinition=" smallint(2)   COMMENT '类型：1' ")
    private Short wisdomType;

    @Column(name="update_num",columnDefinition=" bigint(4)   COMMENT '修改次数' ")
    private Integer updateNum;

    public Wisdom() {
    }

    public Integer getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(Integer updateNum) {
        this.updateNum = updateNum;
    }

    public Short getAndOr() {
        return andOr;
    }

    public void setAndOr(Short andOr) {
        this.andOr = andOr;
    }

    public String getWisdomIcon() {
        return wisdomIcon;
    }

    public void setWisdomIcon(String wisdomIcon) {
        this.wisdomIcon = wisdomIcon;
    }

    public Long getHid() {
        return hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public String getWisdomName() {
        return wisdomName;
    }

    public void setWisdomName(String wisdomName) {
        this.wisdomName = wisdomName;
    }

    public Short getWisdomStatus() {
        return wisdomStatus;
    }

    public void setWisdomStatus(Short wisdomStatus) {
        this.wisdomStatus = wisdomStatus;
    }

    public Short getWisdomType() {
        return wisdomType;
    }

    public void setWisdomType(Short wisdomType) {
        this.wisdomType = wisdomType;
    }
}
