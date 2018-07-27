package com.sykj.uusmart.service.impl;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.mqtt.MQTTUtils;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.repository.NexusUserDeviceRepository;
import com.sykj.uusmart.service.UserService;
import com.sykj.uusmart.utils.ConfigGetUtils;
import com.sykj.uusmart.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/6/27 0027.
 */
@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class UserServiceImpl implements UserService{

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    @Autowired
    ServiceConfig serviceConfig;

    public void pushDeviceAllUser(MqIotMessage mqIotMessage) {
        List<Long> userIds = nexusUserDeviceRepository.byDeviceIdQueryUserId(mqIotMessage.getDeviceInfo().getDeviceId());
        for (Long userid : userIds) {
            String destId = mqIotMessage.getMqIotMessageDTO().getHeader().getDestId();
            mqIotMessage.getMqIotMessageDTO().getHeader().setDestId(Constants.role.APP + Constants.specialSymbol.URL_SEPARATE + userid);
            MQTTUtils.push(Constants.role.APP + Constants.specialSymbol.URL_SEPARATE + userid, GsonUtils.toJSON(mqIotMessage.getMqIotMessageDTO()));
            mqIotMessage.getMqIotMessageDTO().getHeader().setDestId(destId);
        }
    }

    String IS_PUSH_STATIS_DEST = "d/ffffffff";
    @Override
    public void isPushDeviceStatus(MqIotMessage mqIotMessage) {
        //推送到相关用户，通知设备上线了
        if(IS_PUSH_STATIS_DEST.equals(mqIotMessage.getMqIotMessageDTO().getHeader().getDestId())){
            pushDeviceAllUser(mqIotMessage);
        }
    }
}
