package com.sykj.uusmart.service;


import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.req.UserAddDeviceDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.req.UserAddFeedbackDTO;
import com.sykj.uusmart.http.req.UserUpdateDeviceDTO;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Liang on 2016/12/23.
 */
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public interface FeedbackInfoService {

    ResponseDTO userAddFeedback(UserAddFeedbackDTO userAddFeedbackDTO);
}
