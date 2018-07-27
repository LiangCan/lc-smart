package com.sykj.uusmart.mqtt;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.mqtt.push.ThingMsgCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2018/6/15 0015.
 */
public class MqIotThingMessage {
    private List<MqIotMessage> pushMsgs;
    private List<MqIotMessageDTO> callbackMsgs;
    private int successNumber = Constants.shortNumber.ZERO;
    private int state = Constants.shortNumber.ZERO;
    CountDownLatch latch;
    private boolean isCallback = true;

    public MqIotThingMessage(List<MqIotMessageDTO> pushMsgs, List<MqIotMessageDTO> callbackMsgs) {
        this.pushMsgs = new ArrayList<>();
        for(MqIotMessageDTO mqIotMessageDTO : pushMsgs){
            this.pushMsgs.add(new MqIotMessage(mqIotMessageDTO));
        }

        this.callbackMsgs = callbackMsgs;
        latch = new CountDownLatch(pushMsgs.size());
    }

    public MqIotThingMessage(List<MqIotMessageDTO> pushMsgs) {
        this.pushMsgs = new ArrayList<>();
        for(MqIotMessageDTO mqIotMessageDTO : pushMsgs){
            this.pushMsgs.add(new MqIotMessage(mqIotMessageDTO));
        }
        isCallback = false;
        latch = new CountDownLatch(pushMsgs.size());
    }

    public int getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(int successNumber) {
        this.successNumber = successNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public MqIotThingMessage() {
    }

    public boolean isCallback() {
        return isCallback;
    }

    public void setCallback(boolean callback) {
        isCallback = callback;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public List<MqIotMessage> getPushMsgs() {
        return pushMsgs;
    }

    public void setPushMsgs(List<MqIotMessage> pushMsgs) {
        this.pushMsgs = pushMsgs;
    }

    public List<MqIotMessageDTO> getCallbackMsgs() {
        return callbackMsgs;
    }

    public void setCallbackMsgs(List<MqIotMessageDTO> callbackMsgs) {
        this.callbackMsgs = callbackMsgs;
    }

}
