package com.sykj.uusmart.service;

import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.cmd.MqIotDeviceLoginDTO;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotRespDeviceLoginDTO;

/**
 * Created by Administrator on 2018/6/12 0012.
 */
public interface DeviceService {

    /** API 用户批量添加设备  */
    MqIotRespDeviceLoginDTO handelDeviceLogin(MqIotDeviceLoginDTO mqIotDeviceLoginDTO);

    void handelDeviceInform(MqIotMessage mqIotMessage);

    void handelDeviceDiscontrol(MqIotMessage mqIotMessage);
}
