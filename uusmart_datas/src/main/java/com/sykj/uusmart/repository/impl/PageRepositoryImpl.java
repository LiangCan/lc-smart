package com.sykj.uusmart.repository.impl;


import com.sykj.uusmart.repository.PageRepository;
import com.sykj.uusmart.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


/**
 * Created by Liang on 2016/12/29.
 */
@Repository("pageRepositoryImpl")
public class PageRepositoryImpl<T> implements PageRepository {


    @Autowired(required = true)
//    @PersistenceContext(unitName = "entityManagerFactoryPrimary")
    private EntityManager em1;


//    @Autowired(required = true)
//    public void setEm1(@Qualifier(value="entityManagerPrimary")EntityManager em1) {
//        this.em1 = em1;
//    }

    @Override
    public Long findHqlCount(String hql) {

        Long count = null;
        Query query = em1.createQuery(hql);
        count = (Long) query.getSingleResult();

        return count;
    }


    @Override
    public List<T> findHqlPageQuery(String hql, PageUtil pageUtil) {
        List<T> result = null;

        Query query = em1.createQuery(hql);
        query.setFirstResult((int) ((pageUtil.getPageNumber() - 1) * pageUtil.getLimit()));
        query.setMaxResults(pageUtil.getLimit());
        result = (List<T>) query.getResultList();

        return result;
    }

}
