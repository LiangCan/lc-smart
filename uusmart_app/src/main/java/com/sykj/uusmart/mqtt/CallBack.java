package com.sykj.uusmart.mqtt;


import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.utils.AliyunUtils;
import com.sykj.uusmart.utils.ExecutorUtils;
import com.sykj.uusmart.utils.ConfigGetUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 回调类
 *
 * @author liang
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class CallBack implements MqttCallback {
    private static Logger log = LoggerFactory.getLogger(CallBack.class);
    //app下所有消息
    public final static String MQTT_APP_ALL = "u/+";

    //app离线有消息
    public final static String MQTT_APP_DES = "u/disconnect";


    @Override
    public void messageArrived(final String topic, final MqttMessage message) throws Exception {
//		ExecutorUtils.cachedThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
        if (MQTT_APP_DES.equals(topic)) {
            log.info(message.toString());
        } else {
            log.info("topic = " + topic + "  <>msg = " + message.toString());
        }
    }
//		});
//	}


    @Override
    public void connectionLost(Throwable cause) {
        log.error(" MQTT 断开连接 正在重连 ... ");
        //重新连接
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            log.error("MQTT 初始连接暂定异常 ", e1);
        }

        ExecutorUtils.cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                MQTTUtils.init(ConfigGetUtils.serviceConfig.getMQTT_URL(), ConfigGetUtils.serviceConfig.getMQTT_CLIENT_NAME() , new CallBack(), ConfigGetUtils.serviceConfig.getSUB_MQTT_TOPIC());
            }
        });
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
    }


}