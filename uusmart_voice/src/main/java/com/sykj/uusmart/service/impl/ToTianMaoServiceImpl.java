package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.cmdtype.tianmao.TiamMaoCmdListEmun;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.baidu.BDMsgBeanDTO;
import com.sykj.uusmart.http.tianmao.ReqTianMaoBaseDTO;
import com.sykj.uusmart.http.tianmao.TMDevice;
import com.sykj.uusmart.http.tianmao.TMDiscoveryRespDTO;
import com.sykj.uusmart.http.tianmao.TMQueryRespDTO;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotMessageUtils;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.NexusUserDevice;
import com.sykj.uusmart.pojo.ProductInfo;
import com.sykj.uusmart.pojo.UserHomeInfo;
import com.sykj.uusmart.repository.DeviceInfoRepository;
import com.sykj.uusmart.repository.NexusUserDeviceRepository;
import com.sykj.uusmart.repository.ProductInfoRepository;
import com.sykj.uusmart.repository.UserHomeInfoRepository;
import com.sykj.uusmart.service.ToBaiDuService;
import com.sykj.uusmart.service.ToTiamMaoService;
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
public class ToTianMaoServiceImpl implements ToTiamMaoService {


    private Logger log = LoggerFactory.getLogger(ToTianMaoServiceImpl.class);


    @Autowired
    UserHomeInfoRepository userHomeInfoRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;


    @Override
    public TMDiscoveryRespDTO tianMaoBaseDTO(ReqTianMaoBaseDTO reqTianMaoBaseDTO) {
        Long userId = redisFindId((String) reqTianMaoBaseDTO.getPayload().get("accessToken"));//status节点
        UserHomeInfo homeInfo = userHomeInfoRepository.byUserIdQueryUseOne(userId);

        String cmdType = reqTianMaoBaseDTO.getHeader().getNamespace();

        //处理发现设备
        if (TiamMaoCmdListEmun.discovery.getValue().equals(cmdType)) {
            return handleDiscoveryDevice(userId, homeInfo.getHid(), reqTianMaoBaseDTO);
        }

        String didStr = reqTianMaoBaseDTO.getPayload().get("deviceId").toString();
        //针对风扇灯做处理
        Long deviceId;
        String isfanlight = null;
        if(didStr.indexOf("/")> -1){
            deviceId = Long.parseLong(didStr.split("/")[1]);
            isfanlight = didStr.split("/")[0];
        }else{
            deviceId = Long.parseLong(didStr);
        }
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(deviceId);
        ProductInfo productInfo = productInfoRepository.findOne(deviceInfo.getProductId());
        if (!productInfo.isToTiamMao()) {
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NULL, Constants.systemError.API_DATA_IS_NULL, new Object[]{" Product to TiamMao "});
        }

        //处理查询设备
        if (TiamMaoCmdListEmun.query.getValue().equals(cmdType)) {
            return handleQueryDevice(deviceInfo, reqTianMaoBaseDTO);
        }

        //处理控制设备
        if (TiamMaoCmdListEmun.controller.getValue().equals(cmdType)) {
            return hanleDeviceController(userId, deviceInfo, reqTianMaoBaseDTO, isfanlight);
        }

        return new TMDiscoveryRespDTO(reqTianMaoBaseDTO.getHeader(), null);
    }

    private TMQueryRespDTO handleQueryDevice(DeviceInfo deviceInfo, ReqTianMaoBaseDTO reqTianMaoBaseDTO) {
        CustomRunTimeException.checkDeviceIsOffLine(deviceInfo, false);
        Map<String, String> map = new HashMap<>();
        map.put("deviceId", String.valueOf(deviceInfo.getDeviceId()));
        reqTianMaoBaseDTO.getHeader().setName(reqTianMaoBaseDTO.getHeader().getNamespace() + "Response");
        Map<String, String> map2 = new HashMap<>();
        map2.put("name", "powerstate");
        String stateStr = "on";
        map2.put("value", stateStr);
        List<Map<String, String>> resultQuery = new ArrayList<>();
        resultQuery.add(map2);
        return new TMQueryRespDTO(reqTianMaoBaseDTO.getHeader(), map, resultQuery);
    }


    private TMDiscoveryRespDTO handleDiscoveryDevice(Long userId, Long hid, ReqTianMaoBaseDTO reqTianMaoBaseDTO) {
        List<NexusUserDevice> lnud = nexusUserDeviceRepository.findByUserIdAndHomeID(userId, hid);
        List<TMDevice> ltmd = new ArrayList<>();
        for (NexusUserDevice nexusUserDevice : lnud) {

            TMDevice tmDevice = new TMDevice();
            tmDevice.setDeviceId(String.valueOf(nexusUserDevice.getDeviceId()));
            tmDevice.setDeviceName(nexusUserDevice.getRemarks());

            DeviceInfo deviceInfo = deviceInfoRepository.findOne(nexusUserDevice.getDeviceId());
            ProductInfo productInfo = productInfoRepository.findOne(deviceInfo.getProductId());
            if(!productInfo.isToTiamMao()){
                continue;
            }
            // 风扇灯的处理
            if(deviceInfo.getProductId() == Constants.shortNumber.THREE){
                TMDevice tmDevice_2 = tmDevice.clone();
                tmDevice_2.setDeviceType("light");
                tmDevice_2.setIcon("http://goodtime-iot.com/deviceIcon/2.png");
                tmDevice_2.setDeviceId("l/" + nexusUserDevice.getDeviceId());
                ltmd.add(tmDevice_2);
                tmDevice.setDeviceId("f/" + nexusUserDevice.getDeviceId());
            }
            tmDevice.setDeviceType(productInfo.getTiamMaoTypeName());
            tmDevice.setIcon(productInfo.getProductIcon());
            ltmd.add(tmDevice);

        }
        Map<String, List<TMDevice>> lsl = new HashMap<>();
        lsl.put("devices", ltmd);
        reqTianMaoBaseDTO.getHeader().setName("DiscoveryDevicesResponse");
        return new TMDiscoveryRespDTO(reqTianMaoBaseDTO.getHeader(), lsl);
    }

    private TMDiscoveryRespDTO hanleDeviceController(Long userId, DeviceInfo deviceInfo, ReqTianMaoBaseDTO reqTianMaoBaseDTO, String isfanlight) {
        MqIotMessageDTO mqIotMessageDTO = null;
        Map<String, String> body = null;

        if ("SetColor".equals(reqTianMaoBaseDTO.getHeader().getName())) {
            body = MqIotMessageUtils.getSetColorCmd(reqTianMaoBaseDTO.getPayload().get("value").toString());

        } else if ("SetBrightness".equals(reqTianMaoBaseDTO.getHeader().getName())) {
            body = MqIotMessageUtils.getSetBrightnessCmd(reqTianMaoBaseDTO.getPayload().get("value").toString());

        } else if ("TurnOff".equals(reqTianMaoBaseDTO.getHeader().getName()) || "TurnOn".equals(reqTianMaoBaseDTO.getHeader().getName())) {

            String cmd = reqTianMaoBaseDTO.getPayload().get("value").toString();
            body = MqIotMessageUtils.getOnOffCmd(getOnOff.get(cmd), isfanlight);

        } else if("SetWindSpeed".equals(reqTianMaoBaseDTO.getHeader().getName())){
            body = MqIotMessageUtils.getSetWindSpeedCmd(reqTianMaoBaseDTO.getPayload().get("value").toString());

        } else if("OpenSwing".equals(reqTianMaoBaseDTO.getHeader().getName())){
            body = MqIotMessageUtils.getSetWindSpeedCmd(reqTianMaoBaseDTO.getPayload().get("value").toString());

        } else if("SetMode".equals(reqTianMaoBaseDTO.getHeader().getName())){
            String model = getMode.get(reqTianMaoBaseDTO.getPayload().get("value").toString());
            body = MqIotMessageUtils.getSetModeCmd(model);
        }{
            new CustomRunTimeException("0","暂时不支持该指令");
        }

        mqIotMessageDTO = MqIotMessageUtils.getControllor(serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.TWO) + deviceInfo.getDeviceId(), body);
        mqIotUtils.mqIotPushMsg(mqIotMessageDTO);
        Map<String, String> map3 = new HashMap<>();
        map3.put("deviceId", String.valueOf(deviceInfo.getDeviceId()));
        reqTianMaoBaseDTO.getHeader().setName(reqTianMaoBaseDTO.getHeader().getNamespace() + "Response");
        return new TMDiscoveryRespDTO(reqTianMaoBaseDTO.getHeader(), map3);
    }

    public  Long redisFindId(String token) {
        DefaultOAuth2AccessToken idDto = (DefaultOAuth2AccessToken) redisTemplate.opsForValue().get("access:" + token);
        CustomRunTimeException.checkNull(idDto, "oauth");
        return Long.parseLong(idDto.getAdditionalInformation().get("id") + "");
    }

    public static Map<String, String> getMode;
    public static Map<String, String> getOnOff;
    static {
        getMode = new HashMap<>();
        getMode.put("normalWind","1");
        getMode.put("natureWind","2");
        getMode.put("sleepWind","3");
        getMode.put("quietWind","4");
        getMode.put("comfortableWind","5");
        getMode.put("babyWind","6");

        getOnOff = new HashMap<>();
        getOnOff.put("on","1");
        getOnOff.put("off","0");
    }

}
