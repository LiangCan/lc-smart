package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.ProductInfo;
import com.sykj.uusmart.pojo.RoomInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="t_product_info")
@Qualifier("productInfoRepository")
public interface ProductInfoRepository extends CrudRepository<ProductInfo, Long> {

    @Query("SELECT productIcon FROM ProductInfo WHERE pid = ? ")
    String queryProductIconById(Long pid);

}
