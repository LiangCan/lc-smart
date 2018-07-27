package com.sykj.uusmart.mqtt;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.utils.AliyunUtils;
import com.sykj.uusmart.utils.RegularExpressionUtils;
import com.sykj.uusmart.validator.CheckLong;

import javax.validation.constraints.*;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotHeaderDTO {


    //消息类型 0 直传， 1 透传
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value=0, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value=1, message= Constants.systemError.PARAM_VALUE_LENGTH)
    short transferType = 0;

    //消息状态 0发起请求， 2返回请求
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value=0, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value=1, message= Constants.systemError.PARAM_VALUE_LENGTH)
    short packetType = 0;

    //由请求端产生，response时id必须一致
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value=0, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value=2147483647, message= Constants.systemError.PARAM_VALUE_LENGTH)
    Integer tokenId = AliyunUtils.getCheckUtil(6);

    //源id
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=3, max =36, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Pattern(regexp= RegularExpressionUtils.NUMBER_XIEGAN , message= Constants.systemError.PARAM_VALUE_INVALID )
    String sourceId;

    //目的id
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=3, max =36, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Pattern(regexp= RegularExpressionUtils.NUMBER_XIEGAN , message= Constants.systemError.PARAM_VALUE_INVALID )
    String destId;

    //加密类型 0:不启用加密，1:AES加密，2:RSA加密
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value=0, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value=3, message= Constants.systemError.PARAM_VALUE_LENGTH)
    Short encryptType = 0 ;

    //由发送端产生循环的顺序递增的message sequence id
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @CheckLong(min=1, max =10)
    Long msgSeqId;

    //时间戳
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=13, max =13, message= Constants.systemError.PARAM_VALUE_LENGTH)
    String timestamp = String.valueOf(System.currentTimeMillis());

    //版本
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=5, max =10, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Pattern(regexp= RegularExpressionUtils.NUMBER_DIAN , message= Constants.systemError.PARAM_VALUE_INVALID )
    String version = "0.0.1";

    //消息类型
    @NotNull( message = Constants.systemError.PARAM_MISS)
    CmdListEnum actionType;

    public MqIotHeaderDTO(CmdListEnum actionType, String sourceId, String destId) {
        this.actionType = actionType;
        this.destId = destId;
        this.sourceId = sourceId;
    }

    public MqIotHeaderDTO() {
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public CmdListEnum getActionType() {
        return actionType;
    }

    public void setActionType(CmdListEnum actionType) {
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
