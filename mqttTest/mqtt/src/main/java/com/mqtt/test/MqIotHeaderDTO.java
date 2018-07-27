package com.mqtt.test;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotHeaderDTO {


    //消息类型 0 直传， 1 透传

    short transferType = 0;

    //消息状态 0发起请求， 2返回请求
    short packetType = 0;

    //由请求端产生，response时id必须一致
    Integer tokenId ;

    //源id
      String sourceId = "30,30000";

    //目的id
       String destId;

    //加密类型 0:不启用加密，1:AES加密，2:RSA加密
       Short encryptType = 0 ;

    //由发送端产生循环的顺序递增的message sequence id
      Long msgSeqId;

    //时间戳
     String timestamp = String.valueOf(System.currentTimeMillis());

    //版本
       String version = "0.0.1";

    //消息类型
      String actionType;


    public MqIotHeaderDTO() {
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
