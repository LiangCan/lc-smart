package com.sykj.uusmart.pojo;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/6/1 0001.
 */
@Entity
@Table(name="t_cache_msg")
public class CacheMessage {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="cid", length = 16)
    Long cid;

    //消息类型
    @Column(name="actionType",columnDefinition=" varchar(20) COMMENT '用户ID' ")
    String actionType;


    //源id
    @Column(name = "source_id", columnDefinition = " varchar(32) DEFAULT NULL COMMENT '源id' ")
    String sourceId;

    //目的id
    @Column(name = "dest_id", columnDefinition = " varchar(32) DEFAULT NULL COMMENT '目的id' ")
    String destId;


    @Column(name = "cmd", columnDefinition = " longtext DEFAULT NULL COMMENT '完整的指令消息' ")
    String cmd;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
