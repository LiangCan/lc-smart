package com.sykj.uusmart.service;

import com.sykj.uusmart.http.baidu.BDMsgBeanDTO;
import com.sykj.uusmart.http.baidu.RespBDBeanDTO;

/**
 * Created by Administrator on 2018/7/7 0007.
 */
public interface ToBaiDuService {
    /** API  指令的总入口*/
    RespBDBeanDTO baiduBaseDTO(BDMsgBeanDTO bdMsgBeanDTO);

}
