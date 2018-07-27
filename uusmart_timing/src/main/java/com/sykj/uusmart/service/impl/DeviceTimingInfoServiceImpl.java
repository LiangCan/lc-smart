package com.sykj.uusmart.service.impl;


import com.codingapi.tx.annotation.TxTransaction;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserAddDeviceTimingDTO;
import com.sykj.uusmart.hystric.Demo2Client;
import com.sykj.uusmart.hystric.HelloService;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotMessageUtils;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.mqtt.cmd.timing.MqIotAddTimingBaseDTO;
import com.sykj.uusmart.pojo.DeviceTimingInfo;
import com.sykj.uusmart.repository.DeviceInfoRepository;
import com.sykj.uusmart.repository.DeviceTimingInfoRepository;
import com.sykj.uusmart.repository.NexusUserDeviceRepository;
import com.sykj.uusmart.service.DeviceTimingInfoService;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class DeviceTimingInfoServiceImpl implements DeviceTimingInfoService {
    private Logger log = LoggerFactory.getLogger(DeviceTimingInfoServiceImpl.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DeviceTimingInfoRepository deviceTimingInfoRepository;

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    MqIotMessageUtils mqIotMessageUtils;

    @Autowired
    MqIotUtils mqIotUtils;

    private final String ROLE = "task";

    @Autowired
    HelloService helloService;

    @Autowired
    Demo2Client demo2Client;

    @Override
    @TxTransaction(isStart = true)
//    @Transactional
    public ResponseDTO test(ReqBaseDTO reqBaseDTO) {
        int i=  demo2Client.save();
        CustomRunTimeException.checkNull(null, "Nexus");
        return new ResponseDTO(i);
        //        return responseDTO;
    }
    @Override
    public ResponseDTO userAddDeviceTiming(UserAddDeviceTimingDTO userAddDeviceTimingDTO) {
        Long userId = userInfoService.getUserId(true);

        CustomRunTimeException.checkNull(nexusUserDeviceRepository.findByUserIdAndDeviceId(userId, userAddDeviceTimingDTO.getId()), "Nexus");
        CustomRunTimeException.checkDeviceIsOffLine(deviceInfoRepository.findOne(userAddDeviceTimingDTO.getId()), true);

        DeviceTimingInfo deviceTimingInfo = new DeviceTimingInfo();
        deviceTimingInfo.setCreateTime(System.currentTimeMillis());
        deviceTimingInfo.setDtMode(userAddDeviceTimingDTO.getDtMode());
        deviceTimingInfo.setUserId(userId);
        deviceTimingInfo.setDtDays(userAddDeviceTimingDTO.getDtDays());
        deviceTimingInfo.setDtName(userAddDeviceTimingDTO.getDtName());
        deviceTimingInfo.setStartInfo(GsonUtils.toJSON(userAddDeviceTimingDTO.getStartInfo()));
        deviceTimingInfo.setEndInfo(GsonUtils.toJSON(userAddDeviceTimingDTO.getEndInfo()));
        deviceTimingInfo.setDeviceId(userAddDeviceTimingDTO.getId());
        deviceTimingInfoRepository.save(deviceTimingInfo);

        MqIotAddTimingBaseDTO mqIotTimerTaskDTO = MqIotMessageUtils.getAddTimingBody(userAddDeviceTimingDTO.getDtDays(), deviceTimingInfo.getDtid(), userAddDeviceTimingDTO.getDtMode(), userAddDeviceTimingDTO.getStartInfo(), userAddDeviceTimingDTO.getEndInfo(), ROLE);
        MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getAddObject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + userAddDeviceTimingDTO.getId(), mqIotTimerTaskDTO);
        MqIotMessage mqIotMessage = new MqIotMessage(mqIotMessageDTO);
        mqIotUtils.mqIotPushMsgAndGetResult(mqIotMessage);

        if (!mqIotMessage.isSuccess()) {
            MqIotAddTimingBaseDTO mqIotTimerTaskDTO2 = MqIotMessageUtils.getDeleteimingBody(deviceTimingInfo.getDtid(), ROLE);
            MqIotMessageDTO mqIotMessageDTO2 = MqIotMessageUtils.getDeletebject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + userAddDeviceTimingDTO.getId(), mqIotTimerTaskDTO2);
            mqIotUtils.mqIotPushMsg(mqIotMessageDTO2);
            throw new CustomRunTimeException(Constants.resultCode.PUSH_MSG_ERROR, Constants.systemError.PUSH_MSG_ERROR);
        }

        return new ResponseDTO(deviceTimingInfo.getDtid());
    }

    @Override
    public ResponseDTO userQueryDeviceTiming(IdDTO idDTO) {
        Long userId = userInfoService.getUserId(true);
        CustomRunTimeException.checkNull(nexusUserDeviceRepository.findByUserIdAndDeviceId(userId, idDTO.getId()), "Nexus");
        CustomRunTimeException.checkDeviceIsOffLine(deviceInfoRepository.findOne(idDTO.getId()), false);
        return new ResponseDTO(deviceTimingInfoRepository.queryByDeviceId(idDTO.getId()));
    }

    @Override
    public ResponseDTO userDeleteDeviceTiming(IdDTO idDTO) {
        Long userId = userInfoService.getUserId(true);
        DeviceTimingInfo deviceTimingInfo = deviceTimingInfoRepository.queryByUserIdAndDtid(userId, idDTO.getId());
        CustomRunTimeException.checkNull(deviceTimingInfo, "Nexus");
        MqIotAddTimingBaseDTO mqIotTimerTaskDTO = MqIotMessageUtils.getDeleteimingBody(deviceTimingInfo.getDtid(), ROLE);
        MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getDeletebject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + deviceTimingInfo.getDeviceId(), mqIotTimerTaskDTO);
        MqIotMessage mqIotMessage = new MqIotMessage(mqIotMessageDTO);
        deviceTimingInfoRepository.delete(idDTO.getId());
        mqIotMessage.setCache(true);
        mqIotUtils.mqIotPushMsgAndGetResult(mqIotMessage);
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    @TxTransaction
    public ResponseDTO userDeleteDeviceTimingAll(IdDTO idDTO) {
        Long userId = userInfoService.getUserId(true);
//        CustomRunTimeException.checkDeviceJurisdiction(nexusUserDeviceRepository.findByUserIdAndDeviceId(userId, idDTO.getId()), true);
        List<DeviceTimingInfo> list = deviceTimingInfoRepository.queryByDeviceId(idDTO.getId());
        for (DeviceTimingInfo deviceTimingInfo : list) {
            MqIotAddTimingBaseDTO mqIotTimerTaskDTO = MqIotMessageUtils.getDeleteimingBody(deviceTimingInfo.getDtid(), ROLE);
            MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getDeletebject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + idDTO.getId(), mqIotTimerTaskDTO);
            MqIotMessage mqIotMessage = new MqIotMessage(mqIotMessageDTO);
            mqIotMessage.setCache(true);
            mqIotUtils.mqIotPushMsgAndGetResult(mqIotMessage);
        }
        deviceTimingInfoRepository.deleteByDeviceId(idDTO.getId());

        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }
}
