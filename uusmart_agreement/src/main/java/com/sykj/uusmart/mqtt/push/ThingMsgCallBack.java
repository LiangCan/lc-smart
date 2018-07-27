package com.sykj.uusmart.mqtt.push;


import com.sykj.uusmart.mqtt.MqIotThingMessage;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
public interface ThingMsgCallBack  {

    void  onSuccess(MqIotThingMessage mqIotThingMessage);

    void  onFail(MqIotThingMessage mqIotThingMessage);
}
