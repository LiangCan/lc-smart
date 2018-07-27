package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.DeviceVersionInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
;
import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_device_version_info")
@Qualifier("deviceVersionInfoRepository")
public interface DeviceVersionInfoRepository extends JpaRepository<DeviceVersionInfo,Long> {

    @Modifying(clearAutomatically =  true)
    @Query(" UPDATE DeviceVersionInfo SET versionState = ? WHERE id = ?")
    void updateState(short versionState, Long id);

    @Query(" FROM DeviceVersionInfo  WHERE pid = ? AND versionState = 0 ")
    List<DeviceVersionInfo> selectVersionInfoPidState(Long pid);

    @Query(value = "FROM DeviceVersionInfo WHERE pid = :pid ",
            countQuery = "SELECT COUNT(1) FROM DeviceVersionInfo WHERE pid = :pid"
            )
    Page<DeviceVersionInfo> findPidNewVersion(@Param(value = "pid") Long pid, Pageable pageable);

//
//    Page<DeviceVersionInfo> findAll(Pageable pageable);
}
