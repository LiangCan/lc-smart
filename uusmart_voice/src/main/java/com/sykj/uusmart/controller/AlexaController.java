package com.sykj.uusmart.controller;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.alexa.AleaxGetDeviceListDTO;
import com.sykj.uusmart.http.alexa.AleaxPushDeviceMsgDTO;
import com.sykj.uusmart.http.alexa.SaveAlexaOauthInfoDTO;
import com.sykj.uusmart.http.dingdong.DingDongPushCmdDTO;
import com.sykj.uusmart.http.dingdong.ReqDDBaDingUserDTO;
import com.sykj.uusmart.service.ToAleaxService;
import com.sykj.uusmart.service.ToDingDongService;
import com.sykj.uusmart.service.impl.ToBaiDuServiceImpl;
import com.sykj.uusmart.utils.GsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@RestController
@Api(description = "Aleax对接API")
public class AlexaController extends BaseController{
    private Logger log = LoggerFactory.getLogger(AlexaController.class);

    @Autowired
    ToAleaxService toAleaxService;

    @ApiOperation(value="Alexa保持token")
    @RequestMapping(value="/alexa/save/token", method = RequestMethod.POST)
    public String alexaSaveToken(@RequestBody @Valid SaveAlexaOauthInfoDTO saveAlexaOauthInfoDTO, BindingResult bindingResult) throws CustomRunTimeException {

        return GsonUtils.toJSON(toAleaxService.alexaSaveToken(saveAlexaOauthInfoDTO));
    }

    @ApiOperation(value="Alexa获取设备列表")
    @RequestMapping(value="alexa/query/list", method = RequestMethod.POST)
    public String alexaGetDeviceList(@RequestBody @Valid AleaxGetDeviceListDTO aleaxGetDeviceListDTO, BindingResult bindingResult) throws CustomRunTimeException {
        //校验参数
        return GsonUtils.toJSON(toAleaxService.alexaGetDeviceList(aleaxGetDeviceListDTO)).
                replace("\\","").replace("\"properties\":\"","\"properties\":").
                replace("\"retrievable\":true}\"","\"retrievable\":true}").replace("}}\"}","}}");

    }


    @ApiOperation(value="Alexa用户发送消息到设备中去")
    @RequestMapping(value="alexa/push/msg", method = RequestMethod.POST)
    public String aleaxPushMessage(@RequestBody @Valid AleaxPushDeviceMsgDTO pushDeviceMsgDTO, BindingResult bindingResult) throws CustomRunTimeException {
        log.info(" reqPam "+ GsonUtils.toJSON(pushDeviceMsgDTO));
        return GsonUtils.toJSON(toAleaxService.aleaxPushMessage(pushDeviceMsgDTO));
    }
}
