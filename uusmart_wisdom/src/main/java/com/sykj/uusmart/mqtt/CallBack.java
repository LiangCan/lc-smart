package com.sykj.uusmart.mqtt;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotErrorRespBodyDTO;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotRespCodeDTO;
import com.sykj.uusmart.service.WisdomService;
import com.sykj.uusmart.utils.ConfigGetUtils;
import com.sykj.uusmart.utils.ExecutorUtils;
import com.sykj.uusmart.utils.GsonUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


/**
 * 回调类
 *
 * @author liang
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class CallBack implements MqttCallback {
    private static Logger log = LoggerFactory.getLogger(CallBack.class);


    //app离线有消息
    public final static String MQTT_APP_DES = "u/disconnect";

    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    WisdomService wisdomService;

    @Override
    public void messageArrived(final String topic, final MqttMessage message) throws Exception {

        ExecutorUtils.cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                if (ConfigGetUtils.serviceConfig.getMQTT_CLIENT_NAME().equals(topic)) {
                    log.info(topic + " ==> " + message.toString());

                    MqIotMessageDTO mqIotMessageDTO = GsonUtils.toObj(message.toString(), MqIotMessageDTO.class);

                    if(mqIotUtils.handleCallBack(mqIotMessageDTO)){
                        return;
                    }

                    String [] desID = mqIotMessageDTO.getHeader().getDestId().split(Constants.specialSymbol.URL_SEPARATE);
                    //判断是否要处理的数据
                    if(Constants.role.WISDOM.equals(desID[0])){
                        wisdomService.notifyWisdom(message.toString(),Long.parseLong(desID[1]));
                    }
                }
            }
        });
    }


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
                MQTTUtils.init(ConfigGetUtils.serviceConfig.getMQTT_URL(), ConfigGetUtils.serviceConfig.getMQTT_CLIENT_NAME(),  ConfigGetUtils.callBack, ConfigGetUtils.serviceConfig.getSUB_MQTT_TOPIC());
            }
        });
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
    }


}