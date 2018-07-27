package com.sykj.uusmart.service.impl;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.mqtt.cmd.MqIotDeviceLoginDTO;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotRespDeviceLoginDTO;
import com.sykj.uusmart.pojo.CacheMessage;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.repository.CacheMessageRepository;
import com.sykj.uusmart.repository.DeviceInfoRepository;
import com.sykj.uusmart.service.DeviceService;
import com.sykj.uusmart.utils.ConfigGetUtils;
import com.sykj.uusmart.utils.ExecutorUtils;
import com.sykj.uusmart.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12 0012.
 */
@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    CacheMessageRepository cacheMessageRepository;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public MqIotRespDeviceLoginDTO handelDeviceLogin(MqIotDeviceLoginDTO mqIotDeviceLoginDTO) {
        DeviceInfo deviceInfo = ConfigGetUtils.deviceInfoRepository.findDeviceInfoByAddress(mqIotDeviceLoginDTO.getMac());
        CustomRunTimeException.checkNull(deviceInfo, "device");

        //修改上线状态
        deviceInfoRepository.updateDeviceStatusAndVersionInfoAndLatelyLoginTime(deviceInfo.getDeviceId(), Constants.shortNumber.ONE, System.currentTimeMillis(), mqIotDeviceLoginDTO.getHwVer() + Constants.specialSymbol.SEMICOLON + mqIotDeviceLoginDTO.getSwVer());
        MqIotRespDeviceLoginDTO mqIotRespDeviceLoginDTO = new MqIotRespDeviceLoginDTO();
        mqIotRespDeviceLoginDTO.setCode(Constants.shortNumber.ONE);
        mqIotRespDeviceLoginDTO.setRetryCount(Constants.shortNumber.ONE);
        mqIotRespDeviceLoginDTO.setProductClass(Constants.shortNumber.ZERO);

        mqIotUtils.handleCacheMessage(deviceInfo.getDeviceId(), serviceConfig.getMQTT_CLIENT_NAME());

        return mqIotRespDeviceLoginDTO;
    }

    @Override
    public void handelDeviceInform(MqIotMessage mqIotMessage) {
        deviceInfoRepository.updateDeviceStatusPackage(mqIotMessage.getDeviceInfo().getDeviceId(), GsonUtils.toJSON(mqIotMessage.getMqIotMessageDTO().getBody()));
    }



    @Override
    public void handelDeviceDiscontrol(MqIotMessage mqIotMessage) {
        Long time = System.currentTimeMillis() - mqIotMessage.getDeviceInfo().getLatelyLoginTime();
        if(mqIotMessage.getDeviceInfo().getUseTotalTime() == null || mqIotMessage.getDeviceInfo().getUseTotalTime() <= 0)
        {
            time = time/1000;
        }else {
            time = mqIotMessage.getDeviceInfo().getUseTotalTime() + time/1000;
        }
        deviceInfoRepository.updateDeviceStatusAndUseTotalTime(mqIotMessage.getDeviceInfo().getDeviceId(), time,  Constants.shortNumber.NINE);
    }
}
