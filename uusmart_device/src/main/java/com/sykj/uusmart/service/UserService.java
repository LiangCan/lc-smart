package com.sykj.uusmart.service;

import com.sykj.uusmart.mqtt.MqIotMessage;

/**
 * Created by Administrator on 2018/6/27 0027.
 */
public interface UserService {
    /**
     * 推送给设备绑定的所有用户
     * @param mqIotMessageDTO
     */
    void pushDeviceAllUser(MqIotMessage mqIotMessageDTO);

    /**
     * 判断是否要推送设备状态
     * @param mqIotMessage
     */
    void isPushDeviceStatus(MqIotMessage mqIotMessage);
}
