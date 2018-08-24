package com.sykj.uusmart.service.impl;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.vivo.deviceControl.DeviceControlReqDTO;
import com.sykj.uusmart.http.vivo.deviceControl.DeviceControlRequestDTO;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotMessageUtils;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.NexusUserDevice;
import com.sykj.uusmart.pojo.UserInfo;
import com.sykj.uusmart.repository.DeviceInfoRepository;
import com.sykj.uusmart.repository.NexusUserDeviceRepository;
import com.sykj.uusmart.repository.UserInfoRepository;
import com.sykj.uusmart.service.VivoDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sykj.uusmart.utils.ServiceGetUtils.mqIotUtils;

@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class VivoDeviceServiceImpl implements VivoDeviceService {

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public List<Map<String, Object>> getDeviceListByOpenId(String openId) {
        UserInfo userinfo = userInfoRepository.findUserInfoByVivoOpenId(openId);
        return deviceInfoRepository.findDeviceListByUserId(Long.valueOf(userinfo.getUserId()));
    }

    @Override
    public List<Map<String, String>> deviceControlForVivo(DeviceControlReqDTO reqDTO) {
        List<Map<String,String>> resultList = new ArrayList<>();
        UserInfo userInfo = userInfoRepository.findUserInfoByVivoOpenId(reqDTO.getOpenId());
        Long uid = userInfo.getUserId();
        Map<String , String > data = new HashMap<>();
        for(DeviceControlRequestDTO deviceData :reqDTO.getDevices()){
            Long did = Long.valueOf(deviceData.getDeviceId());
            DeviceInfo deviceInfo = deviceInfoRepository.findOne(did);
            NexusUserDevice nexusUserDevice =  nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, did);
            if(deviceInfo == null  || nexusUserDevice == null ){
                data.put("deviceId",deviceData.getDeviceId());
                data.put("status","-1");
                data.put("description","设备不存在");
                resultList.add(data);
                continue;
            }
//            CustomRunTimeException.checkNull(nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, did)," Nexus ");
            Map contBody = MqIotMessageUtils.getOnOffCmd(deviceData.getProperties().getOnoff(), null);
            MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getControllor(serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.TWO) + did, contBody);
            mqIotUtils.mqIotPushMsg(deviceInfo, mqIotMessageDTO);
            data.put("deviceId",deviceData.getDeviceId());
            data.put("status","0");
            resultList.add(data);
        }
        return resultList;
    }
}
