package com.sykj.uusmart.service;


import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserAddWisdomDTO;
import com.sykj.uusmart.http.tianmao.ReqTianMaoBaseDTO;
import com.sykj.uusmart.http.tianmao.TMDiscoveryRespDTO;


/**
 * Created by Liang on 2017/3/22.
 */
public interface ToTiamMaoService {

    /** API  指令的总入口*/
    TMDiscoveryRespDTO tianMaoBaseDTO(ReqTianMaoBaseDTO reqTianMaoBaseDTO);

    /** SERVER 根据token查找用户ID*/
    Long redisFindId(String token);
}
