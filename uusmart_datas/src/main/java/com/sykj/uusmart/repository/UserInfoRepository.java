package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name="t_user_info")
@Qualifier("userInfoRepository")
public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {

	@Query("SELECT userId  AS userId FROM UserInfo  WHERE email = :email ")
	Long findUserInfoByAccount(@Param("email") String email);

	@Query(" FROM UserInfo  WHERE email = :email AND password = :password ")
	UserInfo findUserInfoByEmailAndPassword(@Param("email") String mobile, @Param("password") String password);

	@Modifying(clearAutomatically = true)
	@Query(" UPDATE UserInfo SET loginToken = ?  WHERE userId = ? ")
	void updateLoginTokenByUid(String loginToken, Long userId);


	@Modifying(clearAutomatically = true)
	@Query(" UPDATE UserInfo SET iconUrl = ?  WHERE userId = ? ")
	void updateUserIconByUid(String iconUrl, Long userId);


	@Modifying(clearAutomatically = true)
	@Query(" UPDATE UserInfo SET userName = ?  WHERE userId = ? ")
	void updateUserNameByUid(String userName, Long userId);

	@Query("FROM UserInfo  WHERE userId = :userId ")
	UserInfo findUserInfoByUserId(@Param("userId") Long id);

	@Modifying(clearAutomatically = true)
	@Query(" UPDATE UserInfo SET password = ?  WHERE userId = ? ")
	void updateUserPasswdByUserId(String password, Long userId);

	@Query(" FROM UserInfo  WHERE vivo_open_id = :vivoOpenId   ")
	UserInfo findUserInfoByVivoOpenId(@Param("vivoOpenId") String vivoOpenId);
}
