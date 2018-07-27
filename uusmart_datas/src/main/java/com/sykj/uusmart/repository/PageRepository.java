package com.sykj.uusmart.repository;

import com.sykj.uusmart.utils.PageUtil;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface PageRepository<T> {

     /** 分页查询hql 总数 */
     Long  findHqlCount(String hql);

     /** 分页查询hql WorkOrder */
     List<T> findHqlPageQuery(String hql, PageUtil<T> pageUtil);

}
