package com.sykj.uusmart.service;

import com.sykj.uusmart.http.vivo.deviceControl.DeviceControlReqDTO;
import com.sykj.uusmart.http.vivo.statusQuery.VivoStatusQueryReqDTO;
import com.sykj.uusmart.http.vivo.tokenManager.GetTokenRespDTO;

import java.util.List;
import java.util.Map;

public interface VivoDeviceService {
    List<Map<String, Object>> getDeviceListByOpenId(String openId);

    List<Map<String, String>> deviceControlForVivo(DeviceControlReqDTO reqDTO);

    List<Map<String, Object>> statusQueryForVivo(VivoStatusQueryReqDTO reqDTO);

    GetTokenRespDTO bindDerviceforVivo(String openId, String deviceId);

    int eventReportForVivo(String openId, String deviceId, String eventType, String eventName);
}
