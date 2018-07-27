package com.mqtt.test;


import org.eclipse.paho.client.mqttv3.*;

/**
 * MQTTV3的发布消息类
 */
public class MQTTUtils {
    private static MqttClient CLIENT;
    private static int MQTT_CONNECT_STATUS = 9;
    private static int MQTT_QOS = 1;
    public static String clientSub = "u/";
    public static String clientId = "Service";


    public static void init(String tcp, String clientName, MqttCallback mqttCallBack, String sub) {

        try {
//            if (CLIENT != null && CLIENT.isConnected() ) {
//                disConnect();
//            }
            CLIENT = new MqttClient(tcp, clientId);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName("sykj");
            mqttConnectOptions.setPassword("uusmart".toCharArray());
            CLIENT.setCallback(mqttCallBack);
            CLIENT.connect(mqttConnectOptions);
            MQTT_CONNECT_STATUS = 1;
            System.out.println("MQTT "+ clientId +" 初始连接 " + tcp + " 成功 ");
            MQTTUtils.sub(sub);
        } catch (Exception e) {
            System.out.println("MQTT 初始连接异常 正在重连");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                System.out.println("MQTT 初始连接暂停异常");
            }
            MQTTUtils.init(tcp, clientId.substring(0, clientName.length() - 2 ) , mqttCallBack, clientSub);
        }
    }

    public static void push(String topic, String content) {
        System.out.println("发送： " + topic + " ==> " + content);

        if (MQTT_CONNECT_STATUS != 1) {
            System.out.println(" 发布个毛线啊 压根就没链接 ！");
        }
        MqttTopic mqttTopic = CLIENT.getTopic(topic);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(MQTT_QOS);
//        message.setRetained(true);
        MqttDeliveryToken token = null;
        try {
            token = mqttTopic.publish(message);
        } catch (MqttException e) {
            System.out.println("MQTT 发送异常");
        }
//        Integer code = token.hashCode();
//        System.out.print(code);
    }

    public static void sub(String topic){
        if (MQTT_CONNECT_STATUS != 1) {
            System.out.println(" 订阅个毛线啊 压根就没链接 ！");
        }

        try {
            CLIENT.subscribe(topic);
            System.out.println("订阅 "+topic +" 成功 " );
        } catch (MqttException e) {
            System.out.println("MQTT 订阅主题的时候发送 异常 ");
        }
    }


    public static void disConnect() {
        if (CLIENT == null || !CLIENT.isConnected()) {
            CLIENT = null;
            System.out.println(" 断开个毛线啊 压根就没链接 ！");
            return;
        }

        try {
            CLIENT.disconnect();
            CLIENT = null;
        } catch (MqttException e) {
            System.out.println("MQTT 断开链接异常");
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