package com.sykj.uusmart.controller;


import com.alibaba.fastjson.JSONObject;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.vivo.VivoCommonReqDTO;
import com.sykj.uusmart.http.vivo.VivoCommonRespDTO;
import com.sykj.uusmart.http.vivo.getOpenId.GetOpenIdReqDTO;
import com.sykj.uusmart.http.vivo.tokenManager.GetTokenRespDTO;
import com.sykj.uusmart.service.VivoService;
import com.sykj.uusmart.utils.GsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@RestController
@Api(description = "Vivo对接API")
public class VivoController extends BaseController{
    private Logger log = LoggerFactory.getLogger(VivoController.class);

    @Autowired
    VivoService vivoService;



    @ApiOperation(value="获取用户访问令牌")
    @RequestMapping(value="/user/getOpenId", method = RequestMethod.POST)
    public String getOpenId(@RequestBody @Valid GetOpenIdReqDTO reqDTO   ) throws CustomRunTimeException {
        String getOpenIdResult = vivoService.getOpenIdForVivo(reqDTO.getCode());
        JSONObject getOpenIdJson = JSONObject.parseObject( getOpenIdResult );

        GetTokenRespDTO result = new GetTokenRespDTO();
        if(getOpenIdJson.getString("code").equals("0")){
            //成功
            result = vivoService.userBinding(getOpenIdJson);
            result.setCode("0");
            result.setMsg("success");
        }else{
            //失败；
            result.setCode("-1");
            result.setMsg("error!");
        }
        return GsonUtils.toJSON(result);
//        return GsonUtils.toJSON(respDTO);
    }

    @ApiOperation(value="获取用户访问令牌")
    @RequestMapping(value="user/token/get", method = RequestMethod.POST)
    public String getUserToken(@RequestBody @Valid VivoCommonReqDTO reqDTO ,@RequestHeader(value="appId") String appId,@RequestHeader(value="timestamp") String timestamp ) throws CustomRunTimeException {
//        redisTemplate.opsForList().leftPush( "", )
        log.info(appId);
        log.info(reqDTO.getOpenId()+"-------------------------------------");
//        log.info(" reqPam "+ GsonUtils.toJSON(pushDeviceMsgDTO));
        GetTokenRespDTO respDTO = vivoService.vivoUserLogin(reqDTO.getOpenId());
        return GsonUtils.toJSON(respDTO);
    }

    @ApiOperation(value="更新用户访问令牌")
    @RequestMapping(value="user/token/refresh", method = RequestMethod.POST)
    public String refreshToken(@RequestBody @Valid VivoCommonReqDTO reqDTO ,@RequestHeader(value="appId") String appId,
                               @RequestHeader(value="timestamp") String timestamp ,@RequestHeader(value="refreshToken") String refreshToken) throws CustomRunTimeException {
//        redisTemplate.opsForList().leftPush( "", )
        log.info(appId);
        log.info(reqDTO.getOpenId()+"-------------------------------------");
//        log.info(" reqPam "+ GsonUtils.toJSON(pushDeviceMsgDTO));
        GetTokenRespDTO respDTO = vivoService.refreshVivoUserToken(reqDTO.getOpenId() ,refreshToken);
        return GsonUtils.toJSON(respDTO);
    }
}
