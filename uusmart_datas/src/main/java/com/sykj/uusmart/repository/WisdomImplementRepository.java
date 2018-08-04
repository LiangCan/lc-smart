package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.Wisdom;
import com.sykj.uusmart.pojo.WisdomImplement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_wisdom_implement")
@Qualifier("wisdomImplementRepository")
public interface WisdomImplementRepository extends CrudRepository<WisdomImplement,Long> {
    @Query(" FROM WisdomImplement WHERE wid = ? ")
    List<WisdomImplement> findAllByWid(Long wid);

    @Query(" FROM WisdomImplement WHERE wid = ?  AND id = ? AND implementType = 2 ")
    List<WisdomImplement>  findAllByWidAndId(Long wid, Long did);

    @Query("SELECT NEW WisdomImplement(id, wiid, implementType) FROM WisdomImplement WHERE wid = ? ")
    List<WisdomImplement> findIdsAllByWid(Long wid);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM WisdomImplement WHERE id = ? AND wid = ? ")
    void deleteByIdAndWid(Long id, Long wid);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE  WisdomImplement  SET implementStatus = ? WHERE id = ? AND wid = ? ")
    void updateStatusByIdAndWid(short status, Long id, Long wid);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE  WisdomImplement  SET implementStatus = ? WHERE id = ? AND implementType = 2 ")
    void updateStatusByDId(short status, Long id);


    /**
     * 查找设备的所有相关的WID
     */
    @Query("SELECT DISTINCT(wid) FROM WisdomImplement WHERE id = ? AND implementType = 2")
    List<Long> findWidByDid(Long did);

    /**
     * 删除设备的所有相关的执行
     */
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM WisdomImplement WHERE id = ? AND implementType = 2 ")
    void deleteByDid(Long id);

    @Query(value = "SELECT i.id,  i.implement_type, i.implement_name, i.implement_value  FROM t_wisdom_implement AS i JOIN t_wisdom_condition AS c ON (c.wid= i.wid) WHERE c.id = :id AND  c.condition_name=:conditionName AND c.appointment='equal' AND  c.condition_type= :conditionType AND c.condition_value= :conditionValue ", nativeQuery=true)
    List<Object[]> findImplementByCondition(@Param("id") Long id,
                                            @Param("conditionName") String conditionName,
                                            @Param("conditionType") Short conditionType,
                                            @Param("conditionValue") String conditionValue);

}
