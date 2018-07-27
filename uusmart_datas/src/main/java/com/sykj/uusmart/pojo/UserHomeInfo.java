package com.sykj.uusmart.pojo;

/**
 * Created by Liang on 2017/3/22.
 */
import javax.persistence.*;

@Entity
@Table(name="t_user_home_info")
public class UserHomeInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="hid", length = 16)
    private Long hid;

    @Column(name="userId",columnDefinition=" bigint(16) COMMENT '用户ID' ")
    private Long userId;

    @Column(columnDefinition=" varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '家庭名称' ")
    private String homeName;

    @Column(columnDefinition=" bigint(13)   COMMENT '创建时间' ")
    private Long createDate;

    @Column(columnDefinition=" smallint(2)   COMMENT '状态1：当前 9：正常' ")
    private Short status;

    public UserHomeInfo() {
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getHid() {
        return hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
