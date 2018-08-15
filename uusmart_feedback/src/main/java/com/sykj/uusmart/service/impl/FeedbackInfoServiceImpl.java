package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserAddFeedbackDTO;
import com.sykj.uusmart.pojo.FeedbackInfo;
import com.sykj.uusmart.repository.FeedbackInfoRepository;
import com.sykj.uusmart.service.FeedbackInfoService;
import com.sykj.uusmart.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class FeedbackInfoServiceImpl implements FeedbackInfoService {


    private Logger log = LoggerFactory.getLogger(FeedbackInfoServiceImpl.class);

    @Autowired
    FeedbackInfoRepository feedbackInfoRepository;

    @Autowired
    UserInfoService userInfoService;

    @Override
    public ResponseDTO userAddFeedback(UserAddFeedbackDTO userAddFeedbackDTO) {
//        return null;
        Long userId = userInfoService.getUserId(true);
        FeedbackInfo entity = new FeedbackInfo();
        entity.setContactWay( userAddFeedbackDTO.getContactWay());
        entity.setFeedbackContent( userAddFeedbackDTO.getFeedbackContent());
        entity.setFeedbackType( Constants.shortNumber.ZERO );
        entity.setFeedbackStatus( Constants.shortNumber.ZERO );
        entity.setCreateTime( System.currentTimeMillis() );
        entity.setUserId( userId );
//        entity.setFeedbackId();
        /**
         *  NexusUserDevice nexusUserDevic = new NexusUserDevice();
         *             nexusUserDevic.setDeviceId(deviceInfo.getDeviceId());
         *             nexusUserDevic.setUserId(uid);
         *             nexusUserDevic.setCreateTime(System.currentTimeMillis());
         *             nexusUserDevic.setNudStatus(Constants.shortNumber.ONE);
         *             nexusUserDevic.setHid(hid);
         *             nexusUserDevic.setDeviceIcon(deviceIcon);
         *             nexusUserDevic.setRoomId(userAddDeviceDTO.getRoomId());
         *             nexusUserDevic.setRole(Constants.shortNumber.ONE);
         *             nexusUserDevic.setRemarks(deviceInfo.getDeviceName());
         */
        feedbackInfoRepository.save( entity );
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

}
