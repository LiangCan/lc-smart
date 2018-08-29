package com.sykj.uusmart.service.impl;


import com.codingapi.tx.annotation.TxTransaction;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserAddDeviceTimingDTO;
import com.sykj.uusmart.http.req.UserOnOffObjectDTO;
import com.sykj.uusmart.http.req.UserUpdateDeviceTimingDTO;
import com.sykj.uusmart.hystric.Demo2Client;
import com.sykj.uusmart.hystric.HelloService;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotMessageUtils;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.mqtt.cmd.MqIotSysObjectDTO;
import com.sykj.uusmart.mqtt.cmd.timing.MqIotAddTimingBaseDTO;
import com.sykj.uusmart.mqtt.cmd.timing.MqIotDeleteTimingBaseDTO;
import com.sykj.uusmart.pojo.*;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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


    @Override
    public ResponseDTO userOnOffDeviceTiming(UserOnOffObjectDTO userOnOffObjectDTO) {
        userInfoService.getUserId(true);
        DeviceTimingInfo deviceTimingInfo = deviceTimingInfoRepository.findOne(userOnOffObjectDTO.getId());
        CustomRunTimeException.checkNull(deviceTimingInfo, " DeviceTimingInfo ");

        if(userOnOffObjectDTO.getStatus().equals(Constants.shortNumber.ONE)){
            LinkedHashMap startInfo = GsonUtils.toObj(deviceTimingInfo.getStartInfo(), LinkedHashMap.class);
            LinkedHashMap endInfo = GsonUtils.toObj(deviceTimingInfo.getEndInfo(), LinkedHashMap.class);
            pushAddTimingCmd(deviceTimingInfo, startInfo, endInfo);
        }else  if(userOnOffObjectDTO.getStatus().equals(Constants.shortNumber.NINE)){
            DeviceInfo deviceInfo = deviceInfoRepository.findOne(deviceTimingInfo.getDeviceId());
            MqIotDeleteTimingBaseDTO mqIotTimerTaskDTO = MqIotMessageUtils.getDeleteTimingBody(deviceTimingInfo.getDtid(), ROLE);
            MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getDeletebject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + deviceTimingInfo.getDeviceId(), mqIotTimerTaskDTO);
            mqIotUtils.mqIotPushMsg(deviceInfo, mqIotMessageDTO);
        }
        deviceTimingInfoRepository.updateDtStatusByDtId(userOnOffObjectDTO.getStatus(), deviceTimingInfo.getDtid());
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    public ResponseDTO test(ReqBaseDTO reqBaseDTO) {
        ResponseDTO responseDTO =  helloService.hello();
        CustomRunTimeException.checkNull(null, "Nexus");
        return responseDTO;
    }
    @Override
    public ResponseDTO userAddDeviceTiming(UserAddDeviceTimingDTO userAddDeviceTimingDTO) {
        Long userId = userInfoService.getUserId(true);
        CustomRunTimeException.checkNull(nexusUserDeviceRepository.findByUserIdAndDeviceId(userId, userAddDeviceTimingDTO.getId()), "Nexus");
        DeviceTimingInfo deviceTimingInfo = new DeviceTimingInfo();
        deviceTimingInfo.setCreateTime(System.currentTimeMillis());
        deviceTimingInfo.setDtMode(userAddDeviceTimingDTO.getDtMode());
        deviceTimingInfo.setUserId(userId);
        deviceTimingInfo.setDtDays(userAddDeviceTimingDTO.getDtDays());
        deviceTimingInfo.setDtName(userAddDeviceTimingDTO.getDtName());
        deviceTimingInfo.setUpdateNum(0);
        deviceTimingInfo.setDtStatus(Constants.shortNumber.ONE);
        deviceTimingInfo.setStartInfo(GsonUtils.toJSON(userAddDeviceTimingDTO.getStartInfo()));
        deviceTimingInfo.setEndInfo(GsonUtils.toJSON(userAddDeviceTimingDTO.getEndInfo()));
        deviceTimingInfo.setDeviceId(userAddDeviceTimingDTO.getId());
        deviceTimingInfoRepository.save(deviceTimingInfo);
        pushAddTimingCmd(deviceTimingInfo, userAddDeviceTimingDTO.getStartInfo(), userAddDeviceTimingDTO.getEndInfo());
        return new ResponseDTO(deviceTimingInfo.getDtid());
    }

    public void pushAddTimingCmd(DeviceTimingInfo deviceTimingInfo, LinkedHashMap startInfo, LinkedHashMap endInfo){
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(deviceTimingInfo.getDeviceId());
        CustomRunTimeException.checkDeviceIsOffLine(deviceInfo, false);
        MqIotAddTimingBaseDTO mqIotTimerTaskDTO = MqIotMessageUtils.getAddTimingBody(deviceTimingInfo.getDtDays(), deviceTimingInfo.getDtid(), deviceTimingInfo.getDtMode(), startInfo, endInfo, ROLE, 0);
        MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getAddObject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + deviceTimingInfo.getDeviceId(), mqIotTimerTaskDTO);
        mqIotUtils.mqIotPushMsg(deviceInfo, mqIotMessageDTO);
    }

    @Override
    public ResponseDTO userUpdateDeviceTiming(UserUpdateDeviceTimingDTO userUpdateDeviceTimingDTO) {
        Long userId = userInfoService.getUserId(true);
        CustomRunTimeException.checkNull(nexusUserDeviceRepository.findByUserIdAndDeviceId(userId, userUpdateDeviceTimingDTO.getId()), "Nexus");
        DeviceTimingInfo deviceTimingInfo = deviceTimingInfoRepository.findOne(userUpdateDeviceTimingDTO.getDtId());
        CustomRunTimeException.checkNull(deviceTimingInfo," Timing ");

        deviceTimingInfo.setCreateTime(System.currentTimeMillis());
        deviceTimingInfo.setDtMode(userUpdateDeviceTimingDTO.getDtMode());
        deviceTimingInfo.setUserId(userId);
        deviceTimingInfo.setDtDays(userUpdateDeviceTimingDTO.getDtDays());
        deviceTimingInfo.setDtName(userUpdateDeviceTimingDTO.getDtName());
        deviceTimingInfo.setUpdateNum(deviceTimingInfo.getUpdateNum().intValue() + Constants.shortNumber.ONE );
        deviceTimingInfo.setStartInfo(GsonUtils.toJSON(userUpdateDeviceTimingDTO.getStartInfo()));
        deviceTimingInfo.setEndInfo(GsonUtils.toJSON(userUpdateDeviceTimingDTO.getEndInfo()));
        deviceTimingInfo.setDeviceId(userUpdateDeviceTimingDTO.getId());
        deviceTimingInfoRepository.save(deviceTimingInfo);
        pushAddTimingCmd(deviceTimingInfo, userUpdateDeviceTimingDTO.getStartInfo(), userUpdateDeviceTimingDTO.getEndInfo());
        return new ResponseDTO(deviceTimingInfo.getDtid());
    }

    @Override
    public void synTiming(MqIotSysObjectDTO mqIotSysObjectDTO, DeviceInfo deviceInfo) {
        List<MqIotMessageDTO> pushMsgs = new ArrayList<>();

        List<DeviceTimingInfo> timingInfos = deviceTimingInfoRepository.queryByDeviceIdAndDtStatus(deviceInfo.getDeviceId(), Constants.shortNumber.ONE);
        for(DeviceTimingInfo deviceTimingInfo : timingInfos){
            if(!mqIotSysObjectDTO.getDatas().containsKey(deviceTimingInfo.getDtid())){
                LinkedHashMap startInfo = GsonUtils.toObj(deviceTimingInfo.getStartInfo(), LinkedHashMap.class);
                LinkedHashMap endInfo = GsonUtils.toObj(deviceTimingInfo.getEndInfo(), LinkedHashMap.class);
                MqIotAddTimingBaseDTO mqIotTimerTaskDTO2 = MqIotMessageUtils.getAddTimingBody(deviceTimingInfo.getDtDays(), deviceTimingInfo.getDtid(), deviceTimingInfo.getDtMode(),startInfo , endInfo, ROLE, deviceTimingInfo.getUpdateNum());
                pushMsgs.add(MqIotMessageUtils.getAddObject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + deviceTimingInfo.getDeviceId(), mqIotTimerTaskDTO2));
            }
        }

        if (Constants.TIMING_ROLE.equals(mqIotSysObjectDTO.getRole())){
            for(Long timingId : mqIotSysObjectDTO.getDatas().keySet()){
                DeviceTimingInfo deviceTimingInfo = deviceTimingInfoRepository.findOneAndDtStauts(timingId, Constants.shortNumber.ONE);
                if(deviceTimingInfo == null){
                    MqIotDeleteTimingBaseDTO mqIotTimerTaskDTO = MqIotMessageUtils.getDeleteTimingBody(timingId, ROLE);
                    pushMsgs.add(MqIotMessageUtils.getDeletebject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + deviceInfo.getDeviceId(), mqIotTimerTaskDTO));
                    continue;
                }

                if(deviceTimingInfo.getUpdateNum().intValue() > mqIotSysObjectDTO.getDatas().get(timingId).intValue()){
                    LinkedHashMap startInfo = GsonUtils.toObj(deviceTimingInfo.getStartInfo(), LinkedHashMap.class);
                    LinkedHashMap endInfo = GsonUtils.toObj(deviceTimingInfo.getEndInfo(), LinkedHashMap.class);
                    MqIotAddTimingBaseDTO mqIotTimerTaskDTO2 = MqIotMessageUtils.getAddTimingBody(deviceTimingInfo.getDtDays(), deviceTimingInfo.getDtid(), deviceTimingInfo.getDtMode(),startInfo , endInfo, ROLE, deviceTimingInfo.getUpdateNum());
                    pushMsgs.add(MqIotMessageUtils.getAddObject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + deviceTimingInfo.getDeviceId(), mqIotTimerTaskDTO2));
                }

            }
        }else{
            CustomRunTimeException.parameterError(" role ");
        }

        for (MqIotMessageDTO pushMsg : pushMsgs) {
            mqIotUtils.mqIotPushMsg(new MqIotMessage(pushMsg));
        }
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
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(deviceTimingInfo.getDeviceId());
        MqIotDeleteTimingBaseDTO mqIotTimerTaskDTO = MqIotMessageUtils.getDeleteTimingBody(deviceTimingInfo.getDtid(), ROLE);
        MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getDeletebject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + deviceTimingInfo.getDeviceId(), mqIotTimerTaskDTO);
        deviceTimingInfoRepository.delete(idDTO.getId());

        mqIotUtils.mqIotPushMsg(deviceInfo, mqIotMessageDTO);
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    @TxTransaction
    public ResponseDTO userDeleteDeviceTimingAll(IdDTO idDTO) {
        userInfoService.getUserId(true);
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(idDTO.getId());
        CustomRunTimeException.checkNull(deviceInfo,"Device");
        List<DeviceTimingInfo> list = deviceTimingInfoRepository.queryByDeviceId(idDTO.getId());
        for (DeviceTimingInfo deviceTimingInfo : list) {
            MqIotDeleteTimingBaseDTO mqIotTimerTaskDTO = MqIotMessageUtils.getDeleteTimingBody(deviceTimingInfo.getDtid(), ROLE);
            MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getDeletebject(serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + idDTO.getId(), mqIotTimerTaskDTO);
            mqIotUtils.mqIotPushMsg(deviceInfo, mqIotMessageDTO);
        }
        deviceTimingInfoRepository.deleteByDeviceId(idDTO.getId());
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }
}
