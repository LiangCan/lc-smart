package com.sykj.uusmart.mqtt;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.mqtt.push.MqIotMsgCallBack;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.UserInfo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
public class MqIotMessage<T> {

    private MqIotMessageDTO<T> mqIotMessageDTO;

    private MqIotMessageDTO respBody;

    private UserInfo userInfo;

    private DeviceInfo deviceInfo;

    private ScheduledFuture<?> handeMqIotMsgTimeOut ;

    private int reSendNumber = Constants.shortNumber.ONE;

    private Short status = Constants.shortNumber.ZERO;

    private int errorCode = 0;

    CountDownLatch latch = new CountDownLatch(1);
    //回调函数
    private MqIotMsgCallBack mqIotMsgCallBack;

    //是否缓存
    private boolean isCache = false;


    public MqIotMessage() {
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess(){
        return status != Constants.mainStatus.SUCCESS?false:true;
    }

    public boolean isFail(){
        return status == Constants.shortNumber.NINE ?true:false;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public MqIotMessage(CmdListEnum actionType, String sourceId , String destId , T body) {
        mqIotMessageDTO = new MqIotMessageDTO<>(actionType, sourceId, destId, body);
    }

    public ScheduledFuture<?> getHandeMqIotMsgTimeOut() {
        return handeMqIotMsgTimeOut;
    }

    public void setHandeMqIotMsgTimeOut(ScheduledFuture<?> handeMqIotMsgTimeOut) {
        this.handeMqIotMsgTimeOut = handeMqIotMsgTimeOut;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public MqIotMessage(MqIotMessageDTO<T> mqIotMessageDTO) {
        this.mqIotMessageDTO = mqIotMessageDTO;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public MqIotMsgCallBack getMqIotMsgCallBack() {
        return mqIotMsgCallBack;
    }

    public void setMqIotMsgCallBack(MqIotMsgCallBack mqIotMsgCallBack) {
        this.mqIotMsgCallBack = mqIotMsgCallBack;
    }

    public MqIotMessageDTO<T> getMqIotMessageDTO() {
        return mqIotMessageDTO;
    }

    public void setMqIotMessageDTO(MqIotMessageDTO<T> mqIotMessageDTO) {
        this.mqIotMessageDTO = mqIotMessageDTO;
    }

    public MqIotMessageDTO getRespBody() {
        return respBody;
    }

    public int getReSendNumber() {
        return reSendNumber;
    }

    public void setReSendNumber(int reSendNumber) {
        this.reSendNumber = reSendNumber;
    }

    public void setRespBody(MqIotMessageDTO respBody) {
        this.respBody = respBody;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

}
