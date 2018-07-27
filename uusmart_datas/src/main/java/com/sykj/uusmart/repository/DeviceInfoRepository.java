package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.DeviceInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_device_info")
@Qualifier("deviceInfoRepository")
public interface DeviceInfoRepository extends CrudRepository<DeviceInfo, Long>{

    @Query(" FROM DeviceInfo WHERE  deviceAddress = :deviceAddress ")
    DeviceInfo findDeviceInfoByAddress(@Param("deviceAddress") String deviceAddress);

    @Query(" FROM DeviceInfo WHERE  did = ? AND deviceStatus = ?")
    DeviceInfo findDeviceInfoByDidAndDeviceStatus(Long did, short status);

    @Query(" FROM DeviceInfo WHERE  userId = :userId ")
    List<DeviceInfo> findDeviceInfoByUserId(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DeviceInfo SET deviceStatus =:deviceStatus WHERE deviceId=:deviceId  ")
    void updateDeviceStatus(@Param("deviceId") Long deviceId, @Param("deviceStatus") Short deviceStatus);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DeviceInfo SET deviceStatus =:deviceStatus , useTotalTime = :useTotalTime WHERE  deviceId=:deviceId  ")
    void updateDeviceStatusAndUseTotalTime(@Param("deviceId") Long deviceId, @Param("useTotalTime") Long useTotalTime, @Param("deviceStatus") Short deviceStatus);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DeviceInfo SET statusInfo =:statusInfo WHERE deviceId=:deviceId  ")
    void updateDeviceStatusPackage(@Param("deviceId") Long deviceId, @Param("statusInfo") String statusInfo);


    @Query("UPDATE DeviceInfo SET deviceStatus =:deviceStatus, latelyLoginTime = :latelyLoginTime, versionInfo = :versionInfo WHERE deviceId=:deviceId  ")
    @Modifying(clearAutomatically = true)
    void updateDeviceStatusAndVersionInfoAndLatelyLoginTime(@Param("deviceId") Long deviceId, @Param("deviceStatus") Short deviceStatus, @Param("latelyLoginTime") Long latelyLoginTime, @Param("versionInfo") String versionInfo);

    @Query("UPDATE DeviceInfo SET mainDeviceId =:mainDeviceId WHERE deviceId=:deviceId  ")
    @Modifying(clearAutomatically = true)
    void updateMainDeviceId(@Param("deviceId") Long deviceId, @Param("mainDeviceId") Long mainDeviceId);


}
