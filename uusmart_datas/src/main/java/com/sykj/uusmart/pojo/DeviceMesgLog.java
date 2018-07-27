package com.sykj.uusmart.pojo;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/6/1 0001.
 */
@Entity
@Table(name="t_device_msg_log")
public class DeviceMesgLog {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="msg_id", length = 16)
    Long msgId;

    //消息类型
    @Column(name="actionType",columnDefinition=" varchar(20) COMMENT '用户ID' ")
    String actionType;

    //消息类型 0 直传， 1 透传
    @Column(name="transfer_type",columnDefinition=" smallint(2) COMMENT '消息类型 0 直传， 1 透传' ")
    short transferType;

    //消息状态 0发起请求， 2返回请求
    @Column(name="packet_type",columnDefinition=" smallint(2) COMMENT '消息类型 0 直传， 1 透传' ")
    short packetType;

    //由请求端产生，response时id必须一致
    @Column(name = "token_id", columnDefinition = " bigint(16) DEFAULT NULL COMMENT '消息的token' ")
    Integer tokenId;

    //源id
    @Column(name = "source_id", columnDefinition = " varchar(32) DEFAULT NULL COMMENT '源id' ")
    String sourceId;

    //目的id
    @Column(name = "dest_id", columnDefinition = " varchar(32) DEFAULT NULL COMMENT '目的id' ")
    String destId;

    //加密类型 0:不启用加密，1:AES加密，2:RSA加密
    @Column(name="encrypt_type",columnDefinition=" smallint(2) COMMENT '加密方式：0:不启用加密，1:AES加密，2:RSA加密' ")
    Short encryptType;

    //由发送端产生循环的顺序递增的message sequence id
    @Column(name = "msg_seq_id", columnDefinition = " bigint(16) DEFAULT NULL COMMENT '消息ID' ")
    Long msgSeqId;

    //时间戳
    @Column(name="create_time",columnDefinition=" bigint(13) COMMENT '创建时间' ")
    Long careteTime;

    //版本
    @Column(name = "msg_version", columnDefinition = " varchar(32) DEFAULT NULL COMMENT '版本' ")
    String version;

    @Column(name = "msg_body", columnDefinition = " longtext DEFAULT NULL COMMENT 'msg_body' ")
    String body;

    @Column(name = "result_code", columnDefinition = " varchar(8) DEFAULT NULL COMMENT '返回码' ")
    String resultCode;

    @Column(name = "remarks", columnDefinition = "  varchar(32) DEFAULT NULL COMMENT '返回说明' ")
    String remarks;

    public DeviceMesgLog() {
    }

    public DeviceMesgLog(String actionType, short transferType, short packetType, Integer tokenId, String sourceId, String destId, Short encryptType, Long msgSeqId, Long careteTime, String version, String body) {
        this.actionType = actionType;
        this.transferType = transferType;
        this.packetType = packetType;
        this.tokenId = tokenId;
        this.sourceId = sourceId;
        this.destId = destId;
        this.encryptType = encryptType;
        this.msgSeqId = msgSeqId;
        this.careteTime = careteTime;
        this.version = version;
        this.body = body;
    }

    public DeviceMesgLog(String actionType, short transferType, short packetType, Integer tokenId, String sourceId, String destId, Short encryptType, Long msgSeqId, Long careteTime, String version, String body, String resultCode, String remarks) {
        this.actionType = actionType;
        this.transferType = transferType;
        this.packetType = packetType;
        this.tokenId = tokenId;
        this.sourceId = sourceId;
        this.destId = destId;
        this.encryptType = encryptType;
        this.msgSeqId = msgSeqId;
        this.careteTime = careteTime;
        this.version = version;
        this.body = body;
        this.resultCode = resultCode;
        this.remarks = remarks;
    }


    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public short getTransferType() {
        return transferType;
    }

    public void setTransferType(short transferType) {
        this.transferType = transferType;
    }

    public short getPacketType() {
        return packetType;
    }

    public void setPacketType(short packetType) {
        this.packetType = packetType;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
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

    public Short getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(Short encryptType) {
        this.encryptType = encryptType;
    }

    public Long getMsgSeqId() {
        return msgSeqId;
    }

    public void setMsgSeqId(Long msgSeqId) {
        this.msgSeqId = msgSeqId;
    }

    public Long getCareteTime() {
        return careteTime;
    }

    public void setCareteTime(Long careteTime) {
        this.careteTime = careteTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
