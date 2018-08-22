package com.sykj.uusmart.service.impl;


import com.codingapi.tx.annotation.TxTransaction;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.req.UserAddDeviceDTO;
import com.sykj.uusmart.http.req.UserUpdateDeviceDTO;
import com.sykj.uusmart.http.req.input.AddDeivceDTO;
import com.sykj.uusmart.http.resp.RespDeviceListDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.hystric.TimingServiceAPI;
import com.sykj.uusmart.hystric.WisdomServiceAPI;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotMessageUtils;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.pojo.*;
import com.sykj.uusmart.repository.*;
import com.sykj.uusmart.service.DeviceInfoService;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.utils.ExecutorUtils;
import com.sykj.uusmart.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class DeviceaInfoServiceImpl implements DeviceInfoService {


    private Logger log = LoggerFactory.getLogger(DeviceaInfoServiceImpl.class);

    @Autowired
    RoomInfoRepository roomInfoRepository;

    @Autowired
    UserHomeInfoRepository userHomeInfoRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    TimingServiceAPI timingServiceAPI;

    @Autowired
    WisdomServiceAPI wisdomServiceAPI;

    @Override
    @TxTransaction(isStart = true)
    public ResponseDTO userRisterDevice(UserAddDeviceDTO userAddDeviceDTO, ReqBaseDTO<IdDTO> timingReq) {
        Long uid = userInfoService.getUserId(true);

        Long hid = userAddDeviceDTO.getId();
        //家庭鉴权
        CustomRunTimeException.checkNull(userHomeInfoRepository.byUserIdAndHidQueryOne(uid, hid), "home");

        //房间鉴权
        CustomRunTimeException.checkNull(roomInfoRepository.byUserIdAndRoomId(uid, userAddDeviceDTO.getRoomId()), "room");
        Map<DeviceInfo, MqIotMessageDTO> notifyMap = new HashMap<DeviceInfo, MqIotMessageDTO>();
        Map<String, Long> dids = new HashMap<>();
        for (AddDeivceDTO addDeivceDTO : userAddDeviceDTO.getAddDeviceDTOList()) {
            String deviceIcon = productInfoRepository.queryProductIconById(addDeivceDTO.getPid());
            DeviceInfo deviceInfo = deviceInfoRepository.findDeviceInfoByAddress(addDeivceDTO.getDeviceAddress());

            //判断设备是否存在
            if (deviceInfo != null) {
                NexusUserDevice nexusUserDevice = nexusUserDeviceRepository.findDeviceIdAndRoleQueryUserId(deviceInfo.getDeviceId(), Constants.shortNumber.ONE);
                //判断设备用户绑定关系是否存在
                if (nexusUserDevice != null) {

                    // 删除设备的定时和设备的智能；
                    deleteWisdomAndTime(deviceInfo.getDeviceId(), timingReq);

                    //判断是被自己绑定
                    if (nexusUserDevice.getUserId().equals(uid) && nexusUserDevice.getHid().equals(hid)) {
                        dids.put(addDeivceDTO.getDeviceAddress(), deviceInfo.getDeviceId());
                        deviceInfo.setCreateTime(System.currentTimeMillis());
                        deviceInfo.setDeviceAddress(addDeivceDTO.getDeviceAddress());
                        deviceInfo.setDeviceName(addDeivceDTO.getName());
                        deviceInfo.setVersionInfo(addDeivceDTO.getDeviceVersion());
                        deviceInfo.setUserId(uid);
                        deviceInfo.setMainDeviceId(addDeivceDTO.getMainDeviceId());
                        deviceInfo.setLocaDid(addDeivceDTO.getLocaDid());
                        deviceInfo.setDeviceStatus(Constants.shortNumber.ZERO);
                        deviceInfo.setDeviceWifiSSID(userAddDeviceDTO.getDeviceWifiSSID());
                        deviceInfo.setProductId(addDeivceDTO.getPid());
                        deviceInfo.setClassification(userAddDeviceDTO.getClassification());
                        deviceInfo.setHid(hid);
                        deviceInfoRepository.save(deviceInfo);


                        nexusUserDevice.setDeviceId(deviceInfo.getDeviceId());
                        nexusUserDevice.setUserId(uid);
                        nexusUserDevice.setCreateTime(System.currentTimeMillis());
                        nexusUserDevice.setNudStatus(Constants.shortNumber.ONE);
                        nexusUserDevice.setHid(hid);
                        nexusUserDevice.setDeviceIcon(deviceIcon);
                        nexusUserDevice.setRoomId(userAddDeviceDTO.getRoomId());
                        nexusUserDevice.setRole(Constants.shortNumber.ONE);
                        nexusUserDevice.setRemarks(deviceInfo.getDeviceName());
                        nexusUserDeviceRepository.save(nexusUserDevice);
                        continue;
                    }

                    Map<String, String> natityRefresh = MqIotMessageUtils.getNotifyRefreshCmd("dervice");
                    MqIotMessageDTO mqIotMessage = MqIotMessageUtils.getNotify(serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.ONE) + nexusUserDevice.getUserId(), natityRefresh);
                    notifyMap.put(deviceInfo, mqIotMessage);

                    //删除绑定关系
                    nexusUserDeviceRepository.deleteByDeviceId(deviceInfo.getDeviceId());

                }
            } else {
                deviceInfo = new DeviceInfo();
            }
            deviceInfo.setCreateTime(System.currentTimeMillis());
            deviceInfo.setDeviceAddress(addDeivceDTO.getDeviceAddress());
            deviceInfo.setDeviceName(addDeivceDTO.getName());
            deviceInfo.setVersionInfo(addDeivceDTO.getDeviceVersion());
            deviceInfo.setUserId(uid);
            deviceInfo.setMainDeviceId(addDeivceDTO.getMainDeviceId());
            deviceInfo.setLocaDid(addDeivceDTO.getLocaDid());
            deviceInfo.setDeviceStatus(Constants.shortNumber.ZERO);
            deviceInfo.setDeviceWifiSSID(userAddDeviceDTO.getDeviceWifiSSID());
            deviceInfo.setProductId(addDeivceDTO.getPid());
            deviceInfo.setClassification(userAddDeviceDTO.getClassification());
            deviceInfo.setHid(hid);
            deviceInfoRepository.save(deviceInfo);



            NexusUserDevice nexusUserDevic = new NexusUserDevice();
            nexusUserDevic.setDeviceId(deviceInfo.getDeviceId());
            nexusUserDevic.setUserId(uid);
            nexusUserDevic.setCreateTime(System.currentTimeMillis());
            nexusUserDevic.setNudStatus(Constants.shortNumber.ONE);
            nexusUserDevic.setHid(hid);
            nexusUserDevic.setDeviceIcon(deviceIcon);
            nexusUserDevic.setRoomId(userAddDeviceDTO.getRoomId());
            nexusUserDevic.setRole(Constants.shortNumber.ONE);
            nexusUserDevic.setRemarks(deviceInfo.getDeviceName());
            nexusUserDeviceRepository.save(nexusUserDevic);
            dids.put(addDeivceDTO.getDeviceAddress(), deviceInfo.getDeviceId());

        }
        ExecutorUtils.cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<DeviceInfo, MqIotMessageDTO>> iterator = notifyMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<DeviceInfo, MqIotMessageDTO> entry = iterator.next();
                    mqIotUtils.mqIotPushMsg(entry.getKey(),entry.getValue());
                }
            }
        });

        return new ResponseDTO(dids);
    }

    @Override
    public ResponseDTO userUpdateDevice(UserUpdateDeviceDTO userUpdateDeviceDTO) {
        Long uid = userInfoService.getUserId(true);

        NexusUserDevice nexusUserDevice = nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, userUpdateDeviceDTO.getId());
        CustomRunTimeException.checkNull(nexusUserDevice, " Nexus ");

        DeviceInfo deviceInfo = deviceInfoRepository.findOne(userUpdateDeviceDTO.getId());
        CustomRunTimeException.checkNull(deviceInfo, " Device ");

        nexusUserDeviceRepository.updateRemarksAndIcon(userUpdateDeviceDTO.getRemarkes(), userUpdateDeviceDTO.getDeviceIcon(), nexusUserDevice.getNudId());

        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    public ResponseDTO userGetDeviceList(IdDTO idDTO) {
        Long uid = userInfoService.getUserId(true);

        //家庭鉴权
        CustomRunTimeException.checkNull(userHomeInfoRepository.byUserIdAndHidQueryOne(uid, idDTO.getId()), "home");

        List<NexusUserDevice> nexusUserDeviceList = nexusUserDeviceRepository.findByUserIdAndHomeID(uid, idDTO.getId());

        List<RespDeviceListDTO> deviceInfoList = new ArrayList<>();
        for (NexusUserDevice nexusUserDevice : nexusUserDeviceList) {
            DeviceInfo deviceInfo = deviceInfoRepository.findOne(nexusUserDevice.getDeviceId());
            RespDeviceListDTO respDeviceListDTO = new RespDeviceListDTO();
            respDeviceListDTO.setShareInfoId(nexusUserDevice.getShareInfoId());
            respDeviceListDTO.setBinDingTime(nexusUserDevice.getCreateTime());
            respDeviceListDTO.setDeviceIcon(nexusUserDevice.getDeviceIcon());
            respDeviceListDTO.setRole(nexusUserDevice.getRole());
            respDeviceListDTO.setRemarks(nexusUserDevice.getRemarks());
            respDeviceListDTO.setDeviceInfo(deviceInfo);
            respDeviceListDTO.setRoomId(nexusUserDevice.getRoomId());
            deviceInfoList.add(respDeviceListDTO);
        }

        return new ResponseDTO(deviceInfoList);
    }

    @Override
    @TxTransaction(isStart = true)
    public ResponseDTO userDelete(IdDTO idDTO, ReqBaseDTO<IdDTO> reqDTO) {
        Long uid = userInfoService.getUserId(true);
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(idDTO.getId());
        NexusUserDevice nexusUserDevice = nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, idDTO.getId());
        CustomRunTimeException.checkNull(nexusUserDevice, "nexus");

        if (nexusUserDevice.getRole() == Constants.shortNumber.ONE) {
            nexusUserDeviceRepository.deleteByDeviceId(idDTO.getId());
        } else {
            nexusUserDeviceRepository.delete(nexusUserDevice.getNudId());
        }
        deleteWisdomAndTime(nexusUserDevice.getDeviceId(), reqDTO);
        MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getFactoryReset(serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.TWO) + nexusUserDevice.getDeviceId());
        mqIotUtils.mqIotPushMsg(deviceInfo, mqIotMessageDTO);
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }



    public void deleteWisdomAndTime(Long devId, ReqBaseDTO reqBaseDTO) {
        IdDTO idDTO = new IdDTO();
        idDTO.setId(devId);
        reqBaseDTO.sethG(idDTO);
        String result = timingServiceAPI.byDeviceDeleteAllTiming(reqBaseDTO);
        ResponseDTO responseDTO = GsonUtils.toObj(result, ResponseDTO.class);
        if (responseDTO.gethRA().equals(String.valueOf(Constants.mainStatus.SUCCESS))) {
            result = wisdomServiceAPI.byDeviceDeleteAllWisdom(reqBaseDTO);
            responseDTO = GsonUtils.toObj(result, ResponseDTO.class);
            if (!responseDTO.gethRA().equals(String.valueOf(Constants.mainStatus.SUCCESS))) {
                throw new CustomRunTimeException(responseDTO.gethRA(), responseDTO.gethRD());
            }
        } else {
            throw new CustomRunTimeException(responseDTO.gethRA(), responseDTO.gethRD());
        }
    }
}
