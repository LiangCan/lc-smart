package com.mqtt.test;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2018/6/20 0020.
 */
public class Test {
    public static void main(String[] args) {
        MQTTUtils.init( "tcp://goodtime-iot.com:1883", "test123213",new CallBack(),"s/test"  );
        deviceSyn();
        MQTTUtils.disConnect();
    }

    public static void pressureTest(){
        for(int i = 0; i < 100; i ++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MQTTUtils.push("d/102","{\"header\":{\"transferType\":0,\"packetType\":0,\"tokenId\":104919,\"sourceId\":\"s/test\",\"destId\":\"d/102\",\"encryptType\":0,\"msgSeqId\":"+i+",\"timestamp\":\""+ System.currentTimeMillis() +"\",\"version\":\"0.0.1\",\"actionType\":\"deleteObject\"},\"body\":{\"eventCode\":\"*/*,311\",\"role\":\"subs\"}}");
        }
    }

    public static void deviceLogin(){
        MQTTUtils.push("s/deviceSer","{\"header\":{\"transferType\":0,\"packetType\":0,\"tokenId\":10001,\"sourceId\":\"d/1\",\"destId\":\"s/deviceSer\",\"encryptType\":0,\"msgSeqId\":1,\"timestamp\":\"0000000009000\",\"version\":\"0.0.1\",\"actionType\":\"login\"},\"body\":{\"productClass\":0,\"retryCount\":1,\"mac\":\"5CCF7FF79DCF\",\"swVer\":\"1.0.2\",\"hwVer\":\"1.0.2\"},\"append\":{\"value\":\"from device\"}}");
    }

    public static void deviceSyn(){
        MQTTUtils.push("s/wisdomTest","{\"header\":{\"transferType\":0,\"packetType\":0,\"tokenId\":10001,\"sourceId\":\"d/252\",\"destId\":\"s/deviceSer\",\"encryptType\":0,\"msgSeqId\":1,\"timestamp\":\"0000000009000\",\"version\":\"0.0.1\",\"actionType\":\"syn\"},\"body\":{\"role\":\"gen\",\"datas\":{\"25\":0}}}");
    }
}
