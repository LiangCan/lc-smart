package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.cmdtype.baidu.BaiDuCmdListEmun;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.baidu.*;
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
import com.sykj.uusmart.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ToBaiDuServiceImpl implements ToBaiDuService {


    private Logger log = LoggerFactory.getLogger(ToBaiDuServiceImpl.class);

    @Autowired
    ToTiamMaoService toTiamMaoService;

    @Autowired
    UserHomeInfoRepository userHomeInfoRepository;

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

    @Override
    public RespBDBeanDTO baiduBaseDTO(BDMsgBeanDTO bdMsgBeanDTO) {
        Long userId = toTiamMaoService.redisFindId((String) bdMsgBeanDTO.getPayload().get("accessToken"));//status节点
        UserHomeInfo homeInfo = userHomeInfoRepository.byUserIdQueryUseOne(userId);

        String cmdType = bdMsgBeanDTO.getHeader().getNamespace();
        //处理发现设备
        if (BaiDuCmdListEmun.discovery.getValue().equals(cmdType)) {
            return handleDiscoveryDevice(userId, homeInfo.getHid(), bdMsgBeanDTO);
        }

        //处理控制设备
        if (BaiDuCmdListEmun.controller.getValue().equals(cmdType)) {
            return hanleDeviceController(bdMsgBeanDTO);
        }


//        //处理查询设备
//        if (BaiDuCmdListEmun.query.getValue().equals(cmdType)) {
//            return handleQueryDevice(deviceInfo, reqTianMaoBaseDTO);
//        }


        return new RespBDBeanDTO(bdMsgBeanDTO.getHeader(), null);
    }

    private RespBDBeanDTO hanleDeviceController(BDMsgBeanDTO msgBeanDTO) {
        String jsonStr = GsonUtils.toJSON(msgBeanDTO.getPayload().get("appliance"));
        ReqBDControlDTO reqBDControlDTO = GsonUtils.toObj(jsonStr, ReqBDControlDTO.class);
        Long deviceId = Long.parseLong(reqBDControlDTO.getApplianceId());
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(deviceId);
        ProductInfo productInfo = productInfoRepository.findOne(deviceInfo.getProductId());
        if (!productInfo.isToBaiDu()) {
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NULL, Constants.systemError.API_DATA_IS_NULL, new Object[]{" Product to TiamMao "});
        }
        Map<String, String> body = new HashMap<>();
        String headerName = "TurnOnConfirmation";
        if ("TurnOnRequest".equals(msgBeanDTO.getHeader().getName())) {
            body = MqIotMessageUtils.getOnOffCmd("on", null);
        }

        if ("TurnOffRequest".equals(msgBeanDTO.getHeader().getName())) {
            body = MqIotMessageUtils.getOnOffCmd("off", null);
            headerName = "TurnOffConfirmation";
        }

        MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getControllor(serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.TWO) + deviceInfo.getDeviceId(), body);
        mqIotUtils.mqIotPushMsg(mqIotMessageDTO);

        Map<String, List<BDAttributesDTO>> map = new HashMap<>();
        map.put("attributes", new ArrayList<>());
        msgBeanDTO.getHeader().setName(headerName);
        return new RespBDBeanDTO(msgBeanDTO.getHeader(), map);
    }


    private RespBDBeanDTO handleDiscoveryDevice(Long userId, Long hid, BDMsgBeanDTO bdMsgBeanDTO) {
        List<NexusUserDevice> lnud = nexusUserDeviceRepository.findByUserIdAndHomeID(userId, hid);
        List<RespDBDiscoveredAppliances> respDBDiscoveredAppliances = new ArrayList<>();
        for (NexusUserDevice nexusUserDevice : lnud) {

            RespDBDiscoveredAppliances appliances = new RespDBDiscoveredAppliances();
            appliances.setApplianceId(String.valueOf(nexusUserDevice.getDeviceId()));
            appliances.setFriendlyName(nexusUserDevice.getRemarks());

            DeviceInfo deviceInfo = deviceInfoRepository.findOne(nexusUserDevice.getDeviceId());
            ProductInfo productInfo = productInfoRepository.findOne(deviceInfo.getProductId());
            if (!productInfo.isToTiamMao()) {
                continue;
            }

            if (deviceInfo.getDeviceStatus() == Constants.mainStatus.SUCCESS) {
                appliances.setIsReachable(true);
            }

            appliances.setFriendlyName(productInfo.getTiamMaoTypeName());
            appliances.setVersion(deviceInfo.getVersionInfo());
            appliances.setFriendlyDescription(productInfo.getBrandName() + productInfo.getBaiDuTypeName() + productInfo.getModelName());
            appliances.setManufacturerName(productInfo.getBrandName());
            appliances.setModelName(productInfo.getModelName());
            respDBDiscoveredAppliances.add(appliances);

        }
        RespBDDiscoveredDTO lsl = new RespBDDiscoveredDTO();
        lsl.setDiscoveredAppliances(respDBDiscoveredAppliances);
        bdMsgBeanDTO.getHeader().setName("DiscoverAppliancesResponse");
        return new RespBDBeanDTO(bdMsgBeanDTO.getHeader(), lsl);
    }
}
