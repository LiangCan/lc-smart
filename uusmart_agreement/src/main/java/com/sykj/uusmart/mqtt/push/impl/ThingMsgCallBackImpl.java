package com.sykj.uusmart.mqtt.push.impl;


import com.sykj.uusmart.mqtt.MqIotThingMessage;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
public class ThingMsgCallBackImpl implements com.sykj.uusmart.mqtt.push.ThingMsgCallBack {


    @Override
    public void onSuccess(MqIotThingMessage mqIotThingMessage) {
        mqIotThingMessage.getLatch().countDown();

    }

    @Override
    public void onFail(MqIotThingMessage mqIotThingMessage) {
        mqIotThingMessage.getLatch().countDown();

    }
}
