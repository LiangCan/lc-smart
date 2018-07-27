package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.UserHomeInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_user_home_info")
@Qualifier("userHomeInfoRepository")
public interface UserHomeInfoRepository extends CrudRepository<UserHomeInfo,Long> {

	@Query("FROM UserHomeInfo WHERE userId = :userId")
	List<UserHomeInfo> byUserIdQueryList(@Param("userId") Long userId);

	@Query("FROM UserHomeInfo WHERE userId = :userId AND hid = :hid ")
	UserHomeInfo byUserIdAndHidQueryOne(@Param("userId") Long userId, @Param("hid") Long hid);

	@Query("FROM UserHomeInfo WHERE userId = :userId AND status = 1")
	UserHomeInfo byUserIdQueryUseOne(@Param("userId") Long userId);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE UserHomeInfo SET status = :status WHERE userId = :userId")
	void updateUserHomeStatus(@Param("userId") Long userId, @Param("status") Short status);

}
