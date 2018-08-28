package com.sykj.uusmart.service;

import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.alexa.AleaxGetDeviceListDTO;
import com.sykj.uusmart.http.alexa.AleaxPushDeviceMsgDTO;
import com.sykj.uusmart.http.alexa.SaveAlexaOauthInfoDTO;
import com.sykj.uusmart.http.dingdong.DingDongPushCmdDTO;
import com.sykj.uusmart.http.dingdong.ReqDDBaDingUserDTO;
import com.sykj.uusmart.http.dingdong.RespDingDongCmd;

/**
 * Created by Administrator on 2018/7/12 0012.
 */
public interface ToAleaxService {
     ResponseDTO userQueryStatus(IdDTO dto);

     ResponseDTO alexaSaveToken(SaveAlexaOauthInfoDTO saveAlexaOauthInfoDTO);

     ResponseDTO alexaGetDeviceList(AleaxGetDeviceListDTO aleaxGetDeviceListDTO) ;

     ResponseDTO aleaxPushMessage(AleaxPushDeviceMsgDTO aleaxPushDeviceMsgDTO) ;
}
