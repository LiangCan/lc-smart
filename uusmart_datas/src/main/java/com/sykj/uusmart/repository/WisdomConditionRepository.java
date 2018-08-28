package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.Wisdom;
import com.sykj.uusmart.pojo.WisdomCondition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_wisdom_condition")
@Qualifier("wisdomConditionRepository")
public interface WisdomConditionRepository extends CrudRepository<WisdomCondition,Long> {

    @Query(" FROM WisdomCondition WHERE wid = ? ")
    List<WisdomCondition> findAllByWid(Long wid);

    @Query(" FROM WisdomCondition WHERE wid = ? AND id = ? AND conditionType = 2 AND conditionStatus = 1")
    List<WisdomCondition> findAllByWidAndId(Long wid, Long id);

    /**
     * 查找设备的所有相关的WID
     */
    @Query("SELECT DISTINCT(wid) FROM WisdomCondition WHERE id = ? AND conditionType = 2")
    List<Long> findWidByDid(Long did);


    @Query("SELECT   NEW WisdomCondition(id, wcid, conditionType) FROM WisdomCondition WHERE wid = ?  AND conditionStatus = 1 AND  conditionType = 2 ")
    List<WisdomCondition> findIdsAllByWid(Long wid);

    @Query("FROM WisdomCondition WHERE wid = ?  AND conditionStatus = 1 AND  conditionType = 2 ")
    List<WisdomCondition>  findByWidAndImplementTypeAndImplementStatus(Long wid);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM WisdomCondition WHERE id = ? AND wid = ? ")
    void deleteByIdAndWid(Long id, Long wid);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM WisdomCondition WHERE  wid = ? ")
    void deleteByWid( Long wid);

    /**
     * 删除设备的所有相关的条件
     */
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM WisdomCondition WHERE id = ? AND conditionType = 2 ")
    void deleteByDid(Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE  WisdomCondition  SET conditionStatus = ? WHERE id = ? AND wid = ? ")
    void updateStatusByIdAndWid(short status, Long id, Long wid);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE  WisdomCondition  SET conditionStatus = ? WHERE id = ? AND conditionType = 2 ")
    void updateStatusByDId(short status, Long did);
}
