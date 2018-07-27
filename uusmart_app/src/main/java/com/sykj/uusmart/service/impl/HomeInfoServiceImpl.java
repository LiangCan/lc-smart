package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.NameDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.pojo.RoomInfo;
import com.sykj.uusmart.pojo.UserHomeInfo;
import com.sykj.uusmart.repository.RoomInfoRepository;
import com.sykj.uusmart.repository.UserHomeInfoRepository;
import com.sykj.uusmart.repository.UserInfoRepository;
import com.sykj.uusmart.service.UserHomeService;
import com.sykj.uusmart.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class HomeInfoServiceImpl implements UserHomeService {


    private  Logger log = LoggerFactory.getLogger(HomeInfoServiceImpl.class);

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

    @Override
    public ResponseDTO userAddHome(NameDTO nameDTO) {
        Long uid =userInfoService.getUserId(true);
        UserHomeInfo userHomeInfo = new UserHomeInfo();
        userHomeInfo.setCreateDate(System.currentTimeMillis());
        userHomeInfo.setHomeName(nameDTO.getName());
        userHomeInfo.setStatus((short) 9);
        userHomeInfo.setUserId(uid);
        userHomeInfoRepository.save(userHomeInfo);

        // 添加默认房间
        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setUserId(uid);
        roomInfo.setRoomType((short) 1);
        roomInfo.setRoomName("默认");
        roomInfo.setHid(userHomeInfo.getHid());
        roomInfo.setCreateTime(System.currentTimeMillis());
        roomInfoRepository.save(roomInfo);
        return new ResponseDTO(userHomeInfo.getHid());
    }

    @Override
    public ResponseDTO userGetHomeList() {
        Long uid =userInfoService.getUserId(true);
        return new ResponseDTO(userHomeInfoRepository.byUserIdQueryList(uid));
    }
}
