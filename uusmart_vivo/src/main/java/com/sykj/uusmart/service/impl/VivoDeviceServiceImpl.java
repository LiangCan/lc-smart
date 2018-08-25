package com.sykj.uusmart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.vivo.deviceControl.DeviceControlReqDTO;
import com.sykj.uusmart.http.vivo.deviceControl.DeviceControlRequestDTO;
import com.sykj.uusmart.http.vivo.deviceList.GetDeviceListForVivoResponseDTO;
import com.sykj.uusmart.http.vivo.eventReport.EventReportDeviceReqDTO;
import com.sykj.uusmart.http.vivo.statusQuery.VivoStatusQueryReqDTO;
import com.sykj.uusmart.http.vivo.tokenManager.GetTokenRespDTO;
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
import com.sykj.uusmart.utils.SYStringUtils;
import com.sykj.uusmart.utils.VivoHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Value("${sykj.vivo.publicKey}")
    private String vivoPublicKey;

    @Value("${sykj.vivo.appId}")
    private String vivoAppId;

    @Value("${sykj.vivo.clientId}")
    private String vivoClientId;

    @Value("${sykj.vivo.clientSecret}")
    private String clientSecret;

    @Value("${sykj.vivo.appKey}")
    private String appKey;

    @Value("${sykj.vivo.reqAddress}")
    private String reqAddress;

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

    @Override
    public List<Map<String, Object>> statusQueryForVivo(VivoStatusQueryReqDTO reqDTO) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        for(GetDeviceListForVivoResponseDTO device : reqDTO.getDevices()){
            DeviceInfo deviceInfo = this.deviceInfoRepository.findOne(Long.valueOf(device.getDeviceId()));
            Map<String,Object> deviceMap = new HashMap<>();
            deviceMap.put("deviceId",deviceInfo.getDeviceId().toString());
            deviceMap.put("properties","");
        }
        return resultList;
    }

    @Override
    public GetTokenRespDTO bindDerviceforVivo(String openId, String deviceId) {
        GetTokenRespDTO respDTO = new GetTokenRespDTO();

        DeviceInfo deviceinfo = deviceInfoRepository.findOne(Long.valueOf(deviceId));
        respDTO.setMsg("");
        respDTO.setCode("0");

        JSONObject reqJson = new JSONObject();
        reqJson.put("openId",openId);
        reqJson.put("deviceId",deviceId);
//       产品型号
        reqJson.put("series","TB100V60WD");
//        厂商设备品类编码
        reqJson.put("categoryCode",deviceinfo.getProductId());

//        附件参数，OV服务端不做处理，如果要处理，传json格式的字符串；
//        reqJson.put("attachment",accessTokenCP);

        Map<String,String> headMap = new HashMap<>();
        headMap.put("appId",vivoAppId);
        headMap.put("timestamp",new Date().getTime()+"");
        headMap.put("nonce", SYStringUtils.getUUIDNotExistSymbol());

        VivoHttpUtils.httpPost(reqJson,reqAddress+"/device/bind",headMap,appKey);
//        设备绑定为bind，设备解绑为unbind
        this.eventReportForVivo(openId , deviceId ,"bind","bind");

        return respDTO;
    }

    /**
     *  暂时处理一个设备的数据上报，2018年8月25日11:06:07 lgf
     * @param openId vivo用户唯一表示
     * @param deviceId  设备id
     * @param eventType bind：设备绑定/解绑 onOffline：设备上下线 alarm：设备产生的告警 error：设备的故障信息 attachmentChange: 附件参数变更
     * @param eventName 设备绑定为bind，设备解绑为unbind；设备上线为online，设备下线为offline
     */
    @Override
    public int eventReportForVivo(String openId, String deviceId, String eventType, String eventName) {
        int result = 0 ;
        List<EventReportDeviceReqDTO> eventDeviceList = new ArrayList<>();

        EventReportDeviceReqDTO deviceReqDTO = new EventReportDeviceReqDTO();
        deviceReqDTO.setDeviceId(deviceId);
        deviceReqDTO.setEventName(eventName);
        deviceReqDTO.setEventType(eventType);
        eventDeviceList.add(deviceReqDTO);

        JSONObject reqJson = new JSONObject();
        reqJson.put("openId",openId);
        reqJson.put("devices",eventDeviceList);

//        附件参数，OV服务端不做处理，如果要处理，传json格式的字符串；
//        reqJson.put("attachment",accessTokenCP);

        Map<String,String> headMap = new HashMap<>();
        headMap.put("appId",vivoAppId);
        headMap.put("timestamp",new Date().getTime()+"");
        headMap.put("nonce",SYStringUtils.getUUIDNotExistSymbol());

        String  reqResult = VivoHttpUtils.httpPost(reqJson,reqAddress+"/device/eventReport",headMap,appKey);
        JSONObject jsonObject = JSONObject.parseObject( reqResult);
        if(jsonObject.getString("code").equals("0"))
            result = 1;
        return result;
    }


}
