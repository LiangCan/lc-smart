package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.DeviceMesgLog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name = "t_device_msg_log")
@Qualifier("deviceMesgLogRepository")
public interface DeviceMesgLogRepository extends CrudRepository<DeviceMesgLog, Long> {

    @Modifying(clearAutomatically = true)
    @Query(" UPDATE DeviceMesgLog SET resultCode= ? , remarks= ?   WHERE deviceId=? ")
    void upRespCodeAndRemarks(String resultCode, String remarks);


}
