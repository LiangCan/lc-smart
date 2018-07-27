package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.alexa.AleaxGetDeviceListDTO;
import com.sykj.uusmart.http.alexa.AleaxPushDeviceMsgDTO;
import com.sykj.uusmart.http.alexa.RespAlexaAddDeviceDTO;
import com.sykj.uusmart.hystric.HelloService;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotMessageUtils;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.NexusUserDevice;
import com.sykj.uusmart.pojo.UserHomeInfo;
import com.sykj.uusmart.repository.DeviceInfoRepository;
import com.sykj.uusmart.repository.NexusUserDeviceRepository;
import com.sykj.uusmart.repository.ProductInfoRepository;
import com.sykj.uusmart.repository.UserHomeInfoRepository;
import com.sykj.uusmart.service.ToAleaxService;
import com.sykj.uusmart.service.ToTiamMaoService;
import com.sykj.uusmart.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class ToAleaxServiceImpl implements ToAleaxService {

    private Logger log = LoggerFactory.getLogger(ToAleaxServiceImpl.class);

    @Autowired
    ToTiamMaoService toTiamMaoService;

    @Autowired
    UserHomeInfoRepository userHomeInfoRepository;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    RedisTemplate redisTemplate;




    @Override
    public ResponseDTO alexaGetDeviceList(AleaxGetDeviceListDTO aleaxGetDeviceListDTO) {
        Long uid = redisFindId(aleaxGetDeviceListDTO.getToken());
        UserHomeInfo homeInfo = userHomeInfoRepository.byUserIdQueryUseOne(uid);
        List<NexusUserDevice> nexusUserDeviceList = nexusUserDeviceRepository.findByUserIdAndHomeID(uid, homeInfo.getHid());
        List<RespAlexaAddDeviceDTO> lrugdls = new ArrayList<>();

        for (NexusUserDevice nexusUserDevice : nexusUserDeviceList) {
            DeviceInfo deviceInfo = deviceInfoRepository.findOne(nexusUserDevice.getDeviceId());
            CustomRunTimeException.checkDeviceIsOffLine(deviceInfo, true);

            RespAlexaAddDeviceDTO respAlexaAddDeviceDTO = new RespAlexaAddDeviceDTO();
            respAlexaAddDeviceDTO.setApplianceId(String.valueOf(nexusUserDevice.getDeviceId()));
            List<String> actions = new ArrayList<>();
            actions.add("turnOn");
            actions.add("turnOff");
            actions.add("setColor");
            respAlexaAddDeviceDTO.setActions(actions);
            respAlexaAddDeviceDTO.setModelName("LS-W-SOCKET-1");
            respAlexaAddDeviceDTO.setFriendlyDescription("NVC");
            respAlexaAddDeviceDTO.setFriendlyName(nexusUserDevice.getRemarks());
            boolean reachable = false;
            if (deviceInfo.getDeviceStatus() == Constants.mainStatus.SUCCESS) {
                reachable = true;
            }
            respAlexaAddDeviceDTO.setReachable(reachable);
            lrugdls.add(respAlexaAddDeviceDTO);
        }
        return new ResponseDTO(lrugdls);
    }

    @Override
    public ResponseDTO aleaxPushMessage(AleaxPushDeviceMsgDTO aleaxPushDeviceMsgDTO) {
        Long uid = redisFindId(aleaxPushDeviceMsgDTO.getToken());
        CustomRunTimeException.checkNull(nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, Long.parseLong(aleaxPushDeviceMsgDTO.getDeviceId()))," Nexus ");

        Map contBody = MqIotMessageUtils.getOnOffCmd(aleaxPushDeviceMsgDTO.getCmd());
        MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getControllor(serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.TWO) + aleaxPushDeviceMsgDTO.getDeviceId(), contBody);
        mqIotUtils.mqIotPushMsg(mqIotMessageDTO);

        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    public  Long redisFindId(String token) {
        DefaultOAuth2AccessToken idDto = (DefaultOAuth2AccessToken) redisTemplate.opsForValue().get("access:" + token);
        CustomRunTimeException.checkNull(idDto, "oauth");
        return Long.parseLong(idDto.getAdditionalInformation().get("id") + "");
    }

    private static Map<String, String> getOnOff;
    static {
        getOnOff = new HashMap<>();
        getOnOff.put("on","1");
        getOnOff.put("off","0");
    }
}
