package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.alexa.*;
import com.sykj.uusmart.hystric.HelloService;
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
import com.sykj.uusmart.service.ToAleaxService;
import com.sykj.uusmart.service.ToTiamMaoService;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.utils.GsonUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseDTO userQueryStatus(IdDTO dto) {
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(dto.getId());
        CustomRunTimeException.checkDeviceIsOffLine(deviceInfo,false);
        DeviceStatusDTO deviceStatusDTO = new DeviceStatusDTO();
        if(deviceInfo != null  & deviceInfo.getDeviceStatus() == Constants.mainStatus.SUCCESS && !TextUtils.isEmpty(deviceInfo.getStatusInfo())){
            deviceStatusDTO.setS("1");

        }else{
            deviceStatusDTO.setS("0");
            deviceStatusDTO.setConnectivity("UNREACHABLE");
        }
        return new ResponseDTO(deviceStatusDTO);
    }

    @Override
    public ResponseDTO alexaSaveToken(SaveAlexaOauthInfoDTO saveAlexaOauthInfoDTO) {
        Long uid = redisFindId(saveAlexaOauthInfoDTO.getAccessToken());
        stringRedisTemplate.opsForValue().set("alexa:oauth:"+ uid, saveAlexaOauthInfoDTO.getOauthInfo() + System.currentTimeMillis());
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    public ResponseDTO alexaGetDeviceList(AleaxGetDeviceListDTO aleaxGetDeviceListDTO) {
        Long uid = redisFindId(aleaxGetDeviceListDTO.getToken());
        UserHomeInfo homeInfo = userHomeInfoRepository.byUserIdQueryUseOne(uid);
        List<NexusUserDevice> nexusUserDeviceList = nexusUserDeviceRepository.findByUserIdAndHomeID(uid, homeInfo.getHid());
        List<RespAlexaAddDeviceDTO> lrugdls = new ArrayList<>();
        for(NexusUserDevice nexusUserDevice : nexusUserDeviceList){
            DeviceInfo deviceInfo = deviceInfoRepository.findOne(nexusUserDevice.getDeviceId());
            ProductInfo productInfo = productInfoRepository.findOne(deviceInfo.getProductId());
            if(!productInfo.isToAlexa()){
                continue;
            }
            CustomRunTimeException.checkNull(deviceInfo, " DeviceInfo ");
            RespAlexaAddDeviceDTO respAlexaAddDeviceDTO = new RespAlexaAddDeviceDTO();
            respAlexaAddDeviceDTO.setEndpointId(String.valueOf(nexusUserDevice.getDeviceId()));
            respAlexaAddDeviceDTO.setFriendlyName(nexusUserDevice.getRemarks());
            respAlexaAddDeviceDTO.setDisplayCategories(new String []{productInfo.getAlexaTypeName()});
            List<Map<String, String>> capabilities  = new ArrayList<>();
            Map<String, String> pam =  new HashMap<>();
            pam.put("interface","Alexa.PowerController");
            pam.put("version","3");
            pam.put("type","AlexaInterface");
            Map<String,String> names = new HashMap<>();
//            names.put("name","powerState");
            names.put("name","powerState");
            PropertiesDTO propertiesDTO = new PropertiesDTO();
            propertiesDTO.getSupported().add(names);
            pam.put("properties", GsonUtils.toJSON(propertiesDTO));
            capabilities.add(pam);

            if(deviceInfo.getProductId() == 4){
                Map<String, String> pam1 =  new HashMap<>();
                pam1.put("type","AlexaInterface");
                pam1.put("interface","Alexa.ColorController");
                pam1.put("version","3");
                pam1.put("properties","{\"supported\":[{\"name\":\"color\"}],\"proactivelyReported\":false,\"retrievable\":false}}");
                capabilities.add(pam1);
            }

            Map<String, String> pam2 =  new HashMap<>();
            pam2.put("type","AlexaInterface");
            pam2.put("interface","Alexa.EndpointHealth");
            pam2.put("version","3");
            pam2.put("properties","{\"supported\":[{\"name\":\"connectivity\"}],\"proactivelyReported\":true,\"retrievable\":true}");
            capabilities.add(pam2);

            respAlexaAddDeviceDTO.setCapabilities(capabilities);
            lrugdls.add(respAlexaAddDeviceDTO);
        }
        return new ResponseDTO(lrugdls);
    }

    @Override
    public ResponseDTO aleaxPushMessage(AleaxPushDeviceMsgDTO aleaxPushDeviceMsgDTO) {
        Long uid = redisFindId(aleaxPushDeviceMsgDTO.getToken());
        Long did = Long.parseLong(aleaxPushDeviceMsgDTO.getDeviceId());
        CustomRunTimeException.checkNull(nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, did)," Nexus ");
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(did);
        Map contBody = MqIotMessageUtils.getOnOffCmd(aleaxPushDeviceMsgDTO.getCmd(),null);
        MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getControllor(serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.TWO) + aleaxPushDeviceMsgDTO.getDeviceId(), contBody);
        mqIotUtils.mqIotPushMsg(deviceInfo, mqIotMessageDTO);

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
