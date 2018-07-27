package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.UserHomeInfo;
import com.sykj.uusmart.pojo.Wisdom;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_wisdom_info")
@Qualifier("wisdomRepository")
public interface WisdomRepository extends CrudRepository<Wisdom,Long> {
	@Query("FROM Wisdom WHERE userId = :userId")
	List<Wisdom> byUserIdQueryList(@Param("userId") Long userId);

	@Query("FROM Wisdom WHERE userId = :userId AND wid = :wid")
	Wisdom byUserIdAndWidQuery(@Param("userId") Long userId,@Param("wid") Long wid);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Wisdom SET wisdomStatus = :status WHERE  wid = :wid")
	void updateStatus(@Param("status") Short status,@Param("wid") Long wid);
}
