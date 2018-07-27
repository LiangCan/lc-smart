package com.sykj.uusmart.handle;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.mqtt.MQTTUtils;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.repository.NexusUserDeviceRepository;
import com.sykj.uusmart.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5 0005.
 */
@Component
public class PushAPPUtils {

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    @Autowired
    MqIotUtils mqIotUtils;

    public void pushDeviceAllUser(MqIotMessage mqIotMessageDTO) {
        List<Long> userIds = nexusUserDeviceRepository.byDeviceIdQueryUserId(mqIotMessageDTO.getDeviceInfo().getDeviceId());

        for (Long userid : userIds) {
            mqIotMessageDTO.getMqIotMessageDTO().getHeader().setDestId(Constants.role.APP + Constants.specialSymbol.URL_SEPARATE + userid);
            MQTTUtils.push("u/" + userid, GsonUtils.toJSON(mqIotMessageDTO));
        }
    }
}
