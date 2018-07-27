package com.sykj.uusmart.repository;

import com.sykj.uusmart.pojo.TestInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name="t_test_info")
@Qualifier("testInfoRepository")
public interface TestInfoRepository extends CrudRepository<TestInfo, Long>{


}
