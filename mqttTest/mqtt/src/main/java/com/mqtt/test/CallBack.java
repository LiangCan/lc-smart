package com.mqtt.test;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.Map;


/**
 * 回调类
 *
 * @author liang
 */
public class CallBack implements MqttCallback {
    //收到总数
    static int i = 1;

    public void messageArrived(final String topic, final MqttMessage message) throws Exception {
        System.out.println("topic = " + topic + "  <"+ i++ +">msg = " + message.toString());
    }

    public void returnCode(String message, String code){
        Gson gson = new Gson();
        MqIotMessageDTO mqIotMessageDTO = gson.fromJson(message, MqIotMessageDTO.class);
        if(mqIotMessageDTO.getHeader().getActionType().equals("deleteObject")){
            Map<String, String> map = new HashMap<String, String>();
            map.put("code", code);

            mqIotMessageDTO.setBody(map);

            String sid = mqIotMessageDTO.getHeader().getSourceId();
            mqIotMessageDTO.getHeader().setSourceId(mqIotMessageDTO.getHeader().getDestId());
            mqIotMessageDTO.getHeader().setDestId(sid);
            mqIotMessageDTO.getHeader().setPacketType((short)1);

            MQTTUtils.push(mqIotMessageDTO.getHeader().getDestId(),gson.toJson(mqIotMessageDTO));
        }
    }

    public void connectionLost(Throwable cause) {

    }


    public void deliveryComplete(IMqttDeliveryToken arg0) {
    }


}