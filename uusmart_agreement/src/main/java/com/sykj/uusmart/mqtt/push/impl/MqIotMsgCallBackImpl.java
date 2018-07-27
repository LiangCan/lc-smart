package com.sykj.uusmart.mqtt.push.impl;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotErrorRespBodyDTO;
import com.sykj.uusmart.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
@Component
public class MqIotMsgCallBackImpl implements com.sykj.uusmart.mqtt.push.MqIotMsgCallBack {

    Logger logger = LoggerFactory.getLogger(MqIotMsgCallBackImpl.class);

    //MQTT每条消息重发次数为 2次
    @Value("${dir.mqtt.repush.msg.number}")
    private Integer MQTT_REPUSH_MSG_NUMBER;



    @Override
    public void onSuccess(MqIotMessage mqIotMessage) {
        MqIotUtils.PUSH_MSG_MAP.remove(mqIotMessage.getRespBody().getHeader().getTokenId());
        mqIotMessage.setStatus(Constants.shortNumber.ONE);
        MqIotUtils.timer.remove((Runnable) mqIotMessage.getHandeMqIotMsgTimeOut());
        if(  mqIotMessage.getLatch() != null){
            mqIotMessage.getLatch().countDown();
        }
    }

    @Override
    public void onFail(MqIotMessage<MqIotErrorRespBodyDTO> mqIotMessage) {
        logger.error(GsonUtils.toJSON(mqIotMessage.getRespBody()));

        MqIotUtils.PUSH_MSG_MAP.remove(mqIotMessage.getRespBody().getHeader().getTokenId());
        MqIotUtils.timer.remove((Runnable) mqIotMessage.getHandeMqIotMsgTimeOut());
        mqIotMessage.setStatus(Constants.shortNumber.NINE);
        if(  mqIotMessage.getLatch() != null){
            mqIotMessage.getLatch().countDown();
        }
    }
}
