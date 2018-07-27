package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.CacheMessage;
import com.sykj.uusmart.pojo.DeviceMesgLog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name = "t_cache_msg")
@Qualifier("cacheMessageRepository")
public interface CacheMessageRepository extends CrudRepository<CacheMessage, Long> {

    @Query("FROM CacheMessage WHERE destId = ?")
    List<CacheMessage> queryByDestId(String destId);


    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM CacheMessage WHERE destId = ? ")
    void deleteByDestId(String destId);
}
