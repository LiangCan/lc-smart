package com.sykj.uusmart.controller;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.vivo.VivoCommonReqDTO;
import com.sykj.uusmart.http.vivo.VivoCommonRespDTO;
import com.sykj.uusmart.http.vivo.bindDervice.BindDerviceReq;
import com.sykj.uusmart.http.vivo.deviceControl.DeviceControlReqDTO;
import com.sykj.uusmart.http.vivo.deviceControl.DeviceControlRespDTO;
import com.sykj.uusmart.http.vivo.deviceList.GetDeviceListForVivoRespDTO;
import com.sykj.uusmart.http.vivo.deviceList.GetDeviceListForVivoResponseDTO;
import com.sykj.uusmart.http.vivo.statusQuery.VivoStatusQueryReqDTO;
import com.sykj.uusmart.http.vivo.tokenManager.GetTokenRespDTO;
import com.sykj.uusmart.service.VivoDeviceService;
import com.sykj.uusmart.service.VivoService;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.utils.MapConvertBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(description = "vivo 设备")
@RestController
@RequestMapping(value = "vivo/device", method = RequestMethod.POST)
public class VivoDeviceController {
    private Logger log = LoggerFactory.getLogger(VivoDeviceController.class);

    @Value("${sykj.vivo.appId}")
    private String vivoAppId;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    VivoDeviceService vivoDeviceService;

    @Autowired
    VivoService vivoService;



    @ApiOperation(value="设备绑定或解绑，通知到Vivo 云平台 ")
    @RequestMapping(value="/bind", method = RequestMethod.POST)
    public String bindDerviceforVivo(@RequestBody @Valid BindDerviceReq reqDTO ) throws CustomRunTimeException {
        log.info("openId 为："+ reqDTO.getOpenId() + ";drevice 为 ："+reqDTO.getDeviceId() );
        GetTokenRespDTO respDTO = vivoDeviceService.bindDerviceforVivo(reqDTO.getOpenId() ,reqDTO.getDeviceId() );
        return GsonUtils.toJSON(respDTO);
    }



    @ApiOperation(value="获取设备列表接口 ")
    @RequestMapping(value="/deviceList", method = RequestMethod.POST)
    public VivoCommonRespDTO<GetDeviceListForVivoRespDTO> bindDerviceforVivo(@RequestBody @Valid VivoCommonReqDTO reqDTO , @RequestHeader(value="appId") String appId, @RequestHeader(value="timestamp") String timestamp
            , @RequestHeader(value="accessToken") String accessToken, @RequestHeader(value="nonce") String nonce , @RequestHeader(value="signature") String signature) throws CustomRunTimeException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if(! vivoService.checkAccsssTokenNicety(reqDTO.getOpenId(),accessToken) ) {
            return new VivoCommonRespDTO<GetDeviceListForVivoRespDTO>("3158","accessToken过期");
        }
        log.info("openId 为："+ reqDTO.getOpenId()   );
        List<Map<String , Object >> deviceList = vivoDeviceService.getDeviceListByOpenId(reqDTO.getOpenId());
        GetDeviceListForVivoRespDTO data = new GetDeviceListForVivoRespDTO();
        data.setDevices( MapConvertBean.transMap2Bean2(GetDeviceListForVivoResponseDTO.class, deviceList==null? new ArrayList(): deviceList)  );
        return new VivoCommonRespDTO<GetDeviceListForVivoRespDTO>("0" ,"success" ,data);
    }


    @ApiOperation(value="控制设备 ")
    @RequestMapping(value="/deviceControl", method = RequestMethod.POST)
    public VivoCommonRespDTO<DeviceControlRespDTO> deviceControl(@RequestBody @Valid DeviceControlReqDTO reqDTO , @RequestHeader(value="appId") String appId, @RequestHeader(value="timestamp") String timestamp
            , @RequestHeader(value="accessToken") String accessToken, @RequestHeader(value="nonce") String nonce , @RequestHeader(value="signature") String signature) throws CustomRunTimeException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if(! vivoService.checkAccsssTokenNicety(reqDTO.getOpenId(),accessToken) ) {
            return new VivoCommonRespDTO<DeviceControlRespDTO>("3158","accessToken过期");
        }
        log.info("openId 为："+ reqDTO.getOpenId() ,"接受的数据为："+reqDTO.toString());
        List<Map<String , String >> deviceList = vivoDeviceService.deviceControlForVivo(reqDTO);
        DeviceControlRespDTO data = new DeviceControlRespDTO();
        data.setDevices(deviceList);
        return new VivoCommonRespDTO<DeviceControlRespDTO>("0" ,"success" ,data);
    }

    @ApiOperation(value="设备状态查询 ")
    @RequestMapping(value="/statusQuery", method = RequestMethod.POST)
    public VivoCommonRespDTO<GetDeviceListForVivoRespDTO> statusQuery(@RequestBody @Valid VivoStatusQueryReqDTO reqDTO , @RequestHeader(value="appId") String appId, @RequestHeader(value="timestamp") String timestamp
            , @RequestHeader(value="accessToken") String accessToken, @RequestHeader(value="nonce") String nonce , @RequestHeader(value="signature") String signature) throws CustomRunTimeException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if(! vivoService.checkAccsssTokenNicety(reqDTO.getOpenId(),accessToken) ) {
            return new VivoCommonRespDTO<GetDeviceListForVivoRespDTO>("3158","accessToken过期");
        }
        log.info("openId 为："+ reqDTO.getOpenId() ,"接受的数据为："+reqDTO.toString());

        List<Map<String , Object >> deviceList = vivoDeviceService.statusQueryForVivo(reqDTO);
        GetDeviceListForVivoRespDTO data = new GetDeviceListForVivoRespDTO();
//        data.setDevices(deviceList);
        data.setDevices(MapConvertBean.transMap2Bean2(GetDeviceListForVivoResponseDTO.class, deviceList==null? new ArrayList(): deviceList));
        return new VivoCommonRespDTO<GetDeviceListForVivoRespDTO>("0" ,"success" ,data);
    }



}
