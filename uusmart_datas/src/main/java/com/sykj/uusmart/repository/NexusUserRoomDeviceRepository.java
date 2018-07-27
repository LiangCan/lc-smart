package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.NexusUserRoomDevice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_nexus_user_room_device")
@Qualifier("nexusUserRoomDeviceRepository")
public interface NexusUserRoomDeviceRepository extends CrudRepository<NexusUserRoomDevice,Long>{

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM NexusUserRoomDevice WHERE userId = ? AND roomId = ? ")
    void deleteNexusByUserAndRoomId(Long userId, Long roomId);


    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM NexusUserRoomDevice WHERE deviceId = ? ")
    void deleteNexusByDeviceId(Long deviceId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM NexusUserRoomDevice WHERE userId = ? AND deviceId = ? ")
    void deleteNexusByUserAndDeviceId(Long userId, Long deviceId);

    @Query("FROM NexusUserRoomDevice WHERE userId = ? AND roomId = ? ")
    List<NexusUserRoomDevice> queryNexusByUserAndRoomId(Long userId, Long roomId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM NexusUserRoomDevice WHERE shareId = ? ")
    void deleteNexusByShareId(Long shareId);


}
