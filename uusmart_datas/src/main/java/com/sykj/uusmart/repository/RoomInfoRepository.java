package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.RoomInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_room_info")
@Qualifier("roomInfoRepository")
public interface RoomInfoRepository extends CrudRepository<RoomInfo, Long> {

    @Query("FROM RoomInfo WHERE userId = ? AND hid = ? ")
    List<RoomInfo> byUserIdAndHidQueryRoomList(Long userId, Long hid);


    @Query("FROM RoomInfo WHERE userId = ?")
    List<RoomInfo> byUserIdQueryRoomList(Long userId);

    @Query("FROM RoomInfo WHERE userId = ? AND roomId = ? ")
    RoomInfo byUserIdAndRoomId(Long userId, Long roomId);

    @Query("FROM RoomInfo WHERE userId = ? AND roomType = 1")
    RoomInfo byUserIdAndDefault(Long userId);

    @Override
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM RoomInfo WHERE roomId = ? ")
    void delete(Long Long);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM RoomInfo WHERE hid = ? ")
    void deleteRoomByHid(Long hid);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM RoomInfo WHERE hid = ? AND userId = ?")
    void deleteRoomByHidAndUserId(Long hid, Long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE  RoomInfo SET roomName = ? WHERE roomId = ? ")
    void updateName(String name, Long roomId);


}
