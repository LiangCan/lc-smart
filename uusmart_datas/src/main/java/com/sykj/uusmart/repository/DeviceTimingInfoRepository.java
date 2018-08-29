package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.DeviceTimingInfo;
import com.sykj.uusmart.pojo.NexusUserRoomDevice;
import com.sykj.uusmart.pojo.ProductInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_device_timing_info")
@Qualifier("deviceTimingInfoRepository")
public interface DeviceTimingInfoRepository extends CrudRepository<DeviceTimingInfo, Long> {

    @Query(" FROM DeviceTimingInfo WHERE deviceId = ? ")
    List<DeviceTimingInfo> queryByDeviceId(Long deviceId);

    @Query(" FROM DeviceTimingInfo WHERE deviceId = ? AND dtStatus = ?")
    List<DeviceTimingInfo> queryByDeviceIdAndDtStatus(Long deviceId, Short DtStatus);

    @Query(" FROM DeviceTimingInfo WHERE dtid = ? AND dtStatus = ?")
    DeviceTimingInfo findOneAndDtStauts(Long dtid, Short dtStatus);

    @Query(" FROM DeviceTimingInfo WHERE userId = ? AND dtId = ? ")
    DeviceTimingInfo queryByUserIdAndDtid(Long userId, Long dtId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM DeviceTimingInfo WHERE deviceId = ? ")
    void deleteByDeviceId(Long deviceId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE   DeviceTimingInfo SET dtStatus = ?  WHERE dtId = ? ")
    void updateDtStatusByDtId(Short dtStatus, Long dtId);
}
