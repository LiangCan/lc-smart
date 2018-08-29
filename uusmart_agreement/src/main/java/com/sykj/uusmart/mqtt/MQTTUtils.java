package com.sykj.uusmart.mqtt;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.utils.AliyunUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * MQTTV3的发布消息类
 */
@Component
public class MQTTUtils {
    private static Logger log = LoggerFactory.getLogger(MQTTUtils.class);
    private static MqttClient CLIENT;
    private static int MQTT_CONNECT_STATUS = Constants.shortNumber.NINE;
    private static int MQTT_QOS = Constants.shortNumber.ONE;
    public static String[] clientSub;
    public static String clientId = "Service";


    public static void init(String tcp, String clientName, MqttCallback mqttCallBack, String[] sub) {

        clientId = clientName +  AliyunUtils.getCheckUtil(2);
        clientSub = sub;
        try {
            if (CLIENT != null && CLIENT.isConnected() ) {
                disConnect();
            }
            CLIENT = new MqttClient(tcp, clientId);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName("sykj");
            mqttConnectOptions.setPassword("uusmart".toCharArray());
            CLIENT.setCallback(mqttCallBack);
            CLIENT.connect(mqttConnectOptions);
            MQTT_CONNECT_STATUS = Constants.mainStatus.SUCCESS;
            log.info("MQTT "+ clientId +" 初始连接 " + tcp + " 成功 ");
            for(String subtopic:sub){
                MQTTUtils.sub(subtopic);
            }
//            MQTTUtils.sub("");
        } catch (Exception e) {
            log.error("MQTT 初始连接异常 正在重连", e);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                log.error("MQTT 初始连接暂停异常", e1);
            }
            MQTTUtils.init(tcp, clientId.substring(0, clientName.length() - 2 ) , mqttCallBack, clientSub);
        }
    }

    public static void push(String topic, String content) {
        log.info("发送： " + topic + " ==> " + content);

        if (MQTT_CONNECT_STATUS == Constants.mainStatus.FAIL) {
            log.error(" 发布个毛线啊 压根就没链接 ！");
            throw new CustomRunTimeException(String.valueOf(Constants.mainStatus.UNKNOWN), Constants.mainStatus.SYSTEM_ERROR);
        }
        MqttTopic mqttTopic = CLIENT.getTopic(topic);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(MQTT_QOS);
//        message.setRetained(true);
        MqttDeliveryToken token = null;
        try {
            token = mqttTopic.publish(message);
        } catch (MqttException e) {
            log.info("MQTT 发送异常", e);
        }
//        Integer code = token.hashCode();
//        System.out.print(code);
    }

    public static void sub(String topic){
        if (MQTT_CONNECT_STATUS == Constants.mainStatus.FAIL) {
            log.error(" 订阅个毛线啊 压根就没链接 ！");
            throw new CustomRunTimeException();
        }

        try {
            CLIENT.subscribe(topic, MQTT_QOS);
            log.info("订阅 "+topic +" 成功 " );
        } catch (MqttException e) {
            log.info("MQTT 订阅主题的时候发送 异常 ", e);
        }
    }


    public static void disConnect() {
        if (CLIENT == null || !CLIENT.isConnected()) {
            CLIENT = null;
            log.error(" 断开个毛线啊 压根就没链接 ！");
            return;
        }

        try {
            CLIENT.disconnect();
            CLIENT = null;
        } catch (MqttException e) {
            log.info("MQTT 断开链接异常", e);
        }
    }


//    public static void main(String[] args) {
//        MQTTUtils.init("tcp://172.19.2.122","123456", null,"test" );
//        MQTTUtils.push("u/12300", "");
//        MQTTUtils.push("u/a", "");
//        for(int i = 500; i<= 568 ; i++){
//            push("d/"+i, "{\"a\":\"20000\",\"b\":568,\"c\":\"10,40\",\"d\":\"20,568\",\"e\":1,\"f\":1512008039217,\"h\":\"0.0.1\",\"g\":\"{\\\"cmd\\\":100,\\\"switch\\\":1}\"}");
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        disConnect();
//    }
}   