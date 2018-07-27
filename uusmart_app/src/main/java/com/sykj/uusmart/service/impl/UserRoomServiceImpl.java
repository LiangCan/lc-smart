package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.req.UserAddRoomDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.req.UserAddRoomDeviceDTO;
import com.sykj.uusmart.pojo.NexusUserDevice;
import com.sykj.uusmart.pojo.RoomInfo;
import com.sykj.uusmart.repository.*;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.service.UserRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class UserRoomServiceImpl implements UserRoomService {


    private  Logger log = LoggerFactory.getLogger(UserRoomServiceImpl.class);

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
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseDTO userAddRoom(UserAddRoomDTO userAddRoomDTO) {
        Long uid =userInfoService.getUserId(true);

        //鉴权
        CustomRunTimeException.checkNull(userHomeInfoRepository.byUserIdAndHidQueryOne(uid, userAddRoomDTO.getId()),"home");

         RoomInfo roomInfo = new RoomInfo(uid,
                System.currentTimeMillis(),
                Constants.shortNumber.TWO,
                userAddRoomDTO.getName(),
                userAddRoomDTO.getRoomIcon(),
                userAddRoomDTO.getId());

        roomInfoRepository.save(roomInfo);
        return new ResponseDTO(roomInfo.getRoomId());
    }

    @Override
    public ResponseDTO userDeleteRoom(IdDTO idDTO) {
        Long uid =userInfoService.getUserId(true);
        //鉴权
        CustomRunTimeException.checkNull(roomInfoRepository.byUserIdAndRoomId(uid, idDTO.getId()),"home");
        RoomInfo roomInfo = roomInfoRepository.byUserIdAndDefault(uid);
        nexusUserDeviceRepository.updateRoomIdByRoomId(roomInfo.getRoomId(), idDTO.getId());

        roomInfoRepository.delete(idDTO.getId());
        return new ResponseDTO(idDTO.getId());
    }

    @Override
    public ResponseDTO userGetRoomList(IdDTO idDTO) {
        Long uid =userInfoService.getUserId(true);
        //鉴权

        CustomRunTimeException.checkNull(userHomeInfoRepository.byUserIdAndHidQueryOne(uid, idDTO.getId())," Home ");
        return  new ResponseDTO(roomInfoRepository.byUserIdAndHidQueryRoomList(uid, idDTO.getId()));
    }

    @Override
    public ResponseDTO userUpdateRoom(UserAddRoomDTO userAddRoomDTO) {
        Long uid =userInfoService.getUserId(true);
        RoomInfo room = roomInfoRepository.byUserIdAndRoomId(uid, userAddRoomDTO.getId());
        CustomRunTimeException.checkNull(room," Room ");
        room.setRoomIcon(userAddRoomDTO.getRoomIcon());
        room.setRoomName(userAddRoomDTO.getName());
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }


    @Override
    public ResponseDTO userSetRoomDevice(List<UserAddRoomDeviceDTO> userAddRoomDeviceDTOs) {
        Long uid =userInfoService.getUserId(true);
        for(UserAddRoomDeviceDTO userAddRoomDeviceDTO : userAddRoomDeviceDTOs){
            //鉴权
            CustomRunTimeException.checkNull(roomInfoRepository.byUserIdAndRoomId(uid, userAddRoomDeviceDTO.getId())," Room ");

            String [] didsStr = userAddRoomDeviceDTO.getDeviceIds().split(",");
            for(String didStr : didsStr){
                Long did = Long.parseLong(didStr);
                NexusUserDevice nexusUserDevice = nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, did);
                CustomRunTimeException.checkNull(nexusUserDevice," Device Nexus ");
                nexusUserDeviceRepository.updatDeviceRoomId(userAddRoomDeviceDTO.getId(), nexusUserDevice.getNudId());
            }
        }
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }
}
