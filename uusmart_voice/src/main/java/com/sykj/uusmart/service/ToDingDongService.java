package com.sykj.uusmart.service;

import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.dingdong.DingDongPushCmdDTO;
import com.sykj.uusmart.http.dingdong.ReqDDBaDingUserDTO;
import com.sykj.uusmart.http.dingdong.RespDingDongCmd;

/**
 * Created by Administrator on 2018/7/12 0012.
 */
public interface ToDingDongService {

     ResponseDTO badingDingDong(ReqDDBaDingUserDTO reqDDBaDingUserDTO) ;

     RespDingDongCmd dingDongPushCmd(DingDongPushCmdDTO dingDongPushCmdDTO) ;
}
