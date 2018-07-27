package com.sykj.uusmart.mqtt;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.pojo.CacheMessage;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.utils.ServiceGetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by Administrator on 2018/6/15 0015.
 */
public class HandeMqIotMsgTimeOut implements Runnable{
    Logger log = LoggerFactory.getLogger(HandeMqIotMsgTimeOut.class);
    MqIotMessage mqIotMessage;

    public HandeMqIotMsgTimeOut() {
    }

    public HandeMqIotMsgTimeOut(MqIotMessage mqIotMessage) {
        this.mqIotMessage = mqIotMessage;
    }

    @Override
    public void run() {
//        MqIotUtils.timer.remove(mqIotMessage.getHandeMqIotMsgTimeOut());
        if(mqIotMessage.getReSendNumber() < Constants.MSG_TIME_OUT_MAX){
            log.error(Thread.currentThread().getName()+ " 消息发送第"+mqIotMessage.getReSendNumber()+"次响应超时");
            mqIotMessage.setReSendNumber(mqIotMessage.getReSendNumber() + Constants.shortNumber.ONE);
            ServiceGetUtils.mqIotUtils.mqIotPushMsgAndGetResult(mqIotMessage);
        }else{
            log.error(Thread.currentThread().getName()+ " 消息发送响应超时 ");
            mqIotMessage.setStatus(Constants.shortNumber.TWO);
            
            if(mqIotMessage.getLatch() != null){
                mqIotMessage.getLatch().countDown();
            }
            //判断是否需要缓存
            if(mqIotMessage.isCache()){
                if(mqIotMessage.getStatus() != Constants.mainStatus.SUCCESS){
                    CacheMessage cacheMessage = new CacheMessage();
                    cacheMessage.setActionType(mqIotMessage.getMqIotMessageDTO().getHeader().getActionType().getName());
                    cacheMessage.setCmd(GsonUtils.toJSON(mqIotMessage.getMqIotMessageDTO()));
                    cacheMessage.setDestId(mqIotMessage.getMqIotMessageDTO().getHeader().getDestId());
                    cacheMessage.setSourceId(mqIotMessage.getMqIotMessageDTO().getHeader().getSourceId());
                    ServiceGetUtils.cacheMessageRepository.save(cacheMessage);
                }
            }
        }
        return;
    }

    public MqIotMessage getMqIotMessage() {
        return mqIotMessage;
    }

    public void setMqIotMessage(MqIotMessage mqIotMessage) {
        this.mqIotMessage = mqIotMessage;
    }


}
