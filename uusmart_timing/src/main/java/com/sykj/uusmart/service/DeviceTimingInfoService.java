package com.sykj.uusmart.service;

import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserAddDeviceTimingDTO;
import com.sykj.uusmart.http.req.UserUpdateDeviceTimingDTO;
import com.sykj.uusmart.mqtt.cmd.MqIotSysObjectDTO;
import com.sykj.uusmart.pojo.DeviceInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/7/7 0007.
 */
public interface DeviceTimingInfoService {
     ResponseDTO test(ReqBaseDTO reqBaseDTO);

     ResponseDTO userAddDeviceTiming(UserAddDeviceTimingDTO userAddDeviceTimingDTOS);

     ResponseDTO userQueryDeviceTiming(IdDTO idDTO);

     ResponseDTO userDeleteDeviceTiming(IdDTO idDTO);

     ResponseDTO userDeleteDeviceTimingAll(IdDTO idDTO);

     ResponseDTO userUpdateDeviceTiming(UserUpdateDeviceTimingDTO userUpdateDeviceTimingDTO);

     void synTiming(MqIotSysObjectDTO mqIotSysObjectDTO, DeviceInfo deviceInfo);
}
