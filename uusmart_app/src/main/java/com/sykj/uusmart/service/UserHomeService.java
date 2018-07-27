package com.sykj.uusmart.service;


import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.NameDTO;


/**
 * Created by Liang on 2017/3/22.
 */
public interface UserHomeService {

    /** API  用户添加家庭*/
    ResponseDTO userAddHome(NameDTO nameDTO);

    /** API  用户获取家庭列表*/
    ResponseDTO userGetHomeList();
}
