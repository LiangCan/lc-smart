package com.sykj.uusmart.service;

import com.sykj.uusmart.http.vivo.deviceControl.DeviceControlReqDTO;

import java.util.List;
import java.util.Map;

public interface VivoDeviceService {
    List<Map<String, Object>> getDeviceListByOpenId(String openId);


    List<Map<String, String>> deviceControlForVivo(DeviceControlReqDTO reqDTO);
}
