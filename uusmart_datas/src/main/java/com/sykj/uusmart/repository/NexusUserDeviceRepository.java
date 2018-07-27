package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.NexusUserDevice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_nexus_user_device")
@Qualifier("nexusUserDeviceRepository")
public interface NexusUserDeviceRepository extends CrudRepository<NexusUserDevice, Long>{

    @Query("SELECT userId FROM NexusUserDevice  WHERE  deviceId = :deviceId ")
    List<Long> byDeviceIdQueryUserId(@Param("deviceId") Long deviceId);

    @Query("SELECT deviceId FROM NexusUserDevice  WHERE  userId = :userId ")
    List<Long> byUserIdQueryDeviceId(@Param("userId") Long userId);

    @Query(" FROM NexusUserDevice  WHERE  hid = :homeId ")
    List<NexusUserDevice> byHomeIdQueryDeviceId(@Param("homeId") Long homeId);

    @Query(" FROM NexusUserDevice  WHERE  shareInfoId = :shareInfoId ")
    List<NexusUserDevice> byShareIdQueryDevice(@Param("shareInfoId") Long shareInfoId);

    @Query(" FROM NexusUserDevice  WHERE  userId = :userId AND hid = :hid")
    List<NexusUserDevice> findByUserIdAndHomeID(@Param("userId") Long userId, @Param("hid") Long hid);

    @Query(" FROM NexusUserDevice  WHERE  userId = :userId AND role = :role ")
    List<NexusUserDevice> findByUserIdAndRole(@Param("userId") Long userId, @Param("role") Short role);

    @Query(" FROM NexusUserDevice  WHERE  userId = ? AND deviceId = ? AND role = ? ")
    NexusUserDevice findByUserIdAndDeviceIdAndRole(Long userId, Long deviceId, Short role);

    @Query(" FROM NexusUserDevice  WHERE  userId = ? AND deviceId = ?")
    NexusUserDevice findByUserIdAndDeviceId(Long userId, Long deviceId);

    @Modifying(clearAutomatically = true)
    @Query(" DELETE FROM NexusUserDevice WHERE userId =? AND deviceId=? ")
    void  deleteByUserIdAndDeviceId(Long userId, Long deviceId);

    @Modifying(clearAutomatically = true)
    @Query(" DELETE FROM NexusUserDevice WHERE deviceId=? ")
    void deleteByDeviceId(Long deviceId);


    @Query("SELECT userId FROM NexusUserDevice  WHERE  deviceId = ? AND role =? ")
    List<Long> byDeviceIdAndRoleQueryUserId(Long deviceId, Short role);

    @Query("FROM NexusUserDevice  WHERE  deviceId = ? AND role =? ")
    NexusUserDevice  findDeviceIdAndRoleQueryUserId(Long deviceId, Short role);

//    @Query("SELECT userId FROM NexusUserDevice  WHERE  deviceId = ? AND  role =? AND hid = ? ")
//    List<Long> byDeviceIdAndRoleAndHidQueryUserId( Short role, Long hid, Long deviceId);

    @Modifying(clearAutomatically = true)
    @Query(" DELETE FROM NexusUserDevice WHERE shareInfoId=? ")
    void deleteByShareInfoId(Long shareInfoId);

    @Modifying(clearAutomatically = true)
    @Query(" UPDATE NexusUserDevice SET deviceIcon=:deviceIcon,remarks=:remarks  WHERE nudId=:nudId ")
    void updateRemarksAndIcon(@Param("remarks")String remarks, @Param("deviceIcon")String deviceIcon, @Param("nudId")Long nudId);


    @Query("UPDATE NexusUserDevice SET roomId =:roomId WHERE nudId = :nudId  ")
    @Modifying(clearAutomatically = true)
    void updatDeviceRoomId(@Param("roomId") Long roomId,@Param("nudId") Long nudId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE  NexusUserDevice SET roomId = ? WHERE roomId = ? ")
    void updateRoomIdByRoomId(Long nowRoomId, Long roomId);
}
