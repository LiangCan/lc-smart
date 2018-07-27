package com.sykj.uusmart.mqtt.push;


import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotErrorRespBodyDTO;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
public interface MqIotMsgCallBack {

    void onSuccess(MqIotMessage mqIotMessage);

    void  onFail(MqIotMessage<MqIotErrorRespBodyDTO> mqIotMessage);

}
