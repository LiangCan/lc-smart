package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.pojo.ServiceLog;
import com.sykj.uusmart.service.ServiceLogService;
import com.sykj.uusmart.repository.ServiceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class ServiceLogServiceImpl implements ServiceLogService {

    @Autowired
    ServiceLogRepository serviceLogRepository;

    @Override
    public void addServiceLog(ServiceLog serviceLog) {
        serviceLogRepository.save(serviceLog);
    }
}
