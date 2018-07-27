//package com.sykj.uusmart.repository.impl;
//
//
//import com.sykj.uusmart.pojo.DeviceVersionInfo;
//import com.sykj.uusmart.repository.DeviceVersionInfoRepository;
//import com.sykj.uusmart.repository.PageRepository;
//import com.sykj.uusmart.utils.PageUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import java.util.List;
//
//
///**
// * Created by Liang on 2016/12/29.
// */
//@Repository("pageRepositoryImpl")
//public class DeviceVersionInfoRepositoryImpl<T> implements DeviceVersionInfoRepository{
//
//
//    @Autowired(required = true)
//    private EntityManager em;
//
//
//    public DeviceVersionInfo findNewVersionInfo(Long id) {
//        String hql = "";
//        Query query = em.createQuery(hql, DeviceVersionInfo.class);
//        query.setFirstResult(0);
//        query.setMaxResults(1);
//        DeviceVersionInfo  deviceVersionInfo = query.getSingleResult();
//        return deviceVersionInfo;
//    }
//
//}
