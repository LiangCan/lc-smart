package com.sykj.uusmart.mqtt;


import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.handle.CmdHandle;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotErrorRespBodyDTO;
import com.sykj.uusmart.repository.DeviceMesgLogRepository;
import com.sykj.uusmart.utils.AliyunUtils;
import com.sykj.uusmart.utils.ConfigGetUtils;
import com.sykj.uusmart.utils.ExecutorUtils;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.validator.ValidatorUtils;
import org.apache.http.util.TextUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
public class CallBack implements MqttCallback {
    private static Logger log = LoggerFactory.getLogger(CallBack.class);


    @Autowired
    CmdHandle cmdHandle;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    DeviceMesgLogRepository deviceMesgLogRepository;


    @Override
    public void messageArrived(final String topic, final MqttMessage message) {
        ExecutorUtils.cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("收到 ： " + topic + " ==> " + message.toString());

                    //解析协议
                    MqIotMessageDTO mqttIotMessage = GsonUtils.toObj(message.toString(), MqIotMessageDTO.class);


                    ValidatorUtils.validata(mqttIotMessage.getHeader());

                    if (serviceConfig.getMQTT_CLIENT_NAME().equals(topic)) {
                        //处理发送需要返回的消息数据
                        if(mqIotUtils.handleCallBack(mqttIotMessage)){
                            return ;
                        }

                        //需要处理业务的数据
                        MqIotMessage mqIotMessage = cmdHandle.handle(mqttIotMessage);

                        //保存日志
                        mqIotUtils.mqIotSaveMsg(mqIotMessage.getMqIotMessageDTO());

                        //处理成功后，判断是否有要回复的消息
                        mqIotUtils.isRespHandleMessage(mqIotMessage);
                    } else {
                        //保存日志
                        mqIotUtils.mqIotSaveMsg(mqttIotMessage);
                    }

                } catch (CustomRunTimeException e) {
                    //运行时捕获自定义的错误的返回
                    log.error(e.getErrorCode() + " =  " + e.getErrorMsg() + " 请求数据: " + message.toString());
                    if (e.getResult() != null) {

                        MqIotMessageDTO errorRespMsg = (MqIotMessageDTO) e.getResult();

                        //异常日志保存
                        mqIotUtils.mqIotSaveErrorMsg(errorRespMsg, e.getErrorCode(), e.getErrorMsg());

                        //返回异常消息
                        if (errorRespMsg.getHeader() != null && !TextUtils.isEmpty(errorRespMsg.getHeader().getSourceId()) && !errorRespMsg.getHeader().getSourceId().equals(serviceConfig.getMQTT_CLIENT_NAME())) {
                            MqIotErrorRespBodyDTO mqIotErrorRespBodyDTO = new MqIotErrorRespBodyDTO(Integer.parseInt(e.getErrorCode()), e.getErrorMsg());
                            errorRespMsg.setBody(mqIotErrorRespBodyDTO);
                            mqIotUtils.setRespDestId(errorRespMsg);
                            mqIotUtils.mqIotPushMsg(errorRespMsg);
                        }
                    }

                    return;
                } catch (Exception e) {

                    log.error("请求数据有错 " + message.toString(), e);
                    return;
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
                MQTTUtils.init(ConfigGetUtils.serviceConfig.getMQTT_URL(), ConfigGetUtils.serviceConfig.getMQTT_CLIENT_NAME() , new CallBack(), ConfigGetUtils.serviceConfig.getSUB_MQTT_TOPIC());
            }
        });
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
    }


}