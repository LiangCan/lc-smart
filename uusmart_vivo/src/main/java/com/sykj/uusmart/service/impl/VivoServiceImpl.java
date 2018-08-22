package com.sykj.uusmart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.controller.VivoController;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.vivo.VivoCommonRespDTO;
import com.sykj.uusmart.http.vivo.tokenManager.GetTokenRespDTO;
import com.sykj.uusmart.mqtt.MQTTUtils;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.pojo.RoomInfo;
import com.sykj.uusmart.pojo.ServiceLog;
import com.sykj.uusmart.pojo.UserHomeInfo;
import com.sykj.uusmart.pojo.UserInfo;
import com.sykj.uusmart.pojo.redis.UserLogin;
import com.sykj.uusmart.repository.RoomInfoRepository;
import com.sykj.uusmart.repository.UserHomeInfoRepository;
import com.sykj.uusmart.repository.UserInfoRepository;
import com.sykj.uusmart.service.VivoService;
import com.sykj.uusmart.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class VivoServiceImpl implements VivoService {

    private Logger log = LoggerFactory.getLogger(VivoServiceImpl.class);
//    @Autowired
//    VivoUserTokenRedisImpl vivoUserTokenRedis;

    @Autowired
    UserHomeInfoRepository userHomeInfoRepository;

    @Autowired
    RoomInfoRepository roomInfoRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${sykj.vivo.publicKey}")
    private String vivoPublicKey;

    @Value("${sykj.vivo.appId}")
    private String vivoAppId;

    @Value("${sykj.vivo.clientId}")
    private String vivoClientId;

    @Value("${sykj.vivo.clientSecret}")
    private String clientSecret;

    @Value("${sykj.vivo.appKey}")
    private String appKey;

    @Value("${sykj.vivo.reqAddress}")
    private String reqAddress;

    @Override
    public GetTokenRespDTO getTokenByVivoUser(String openId) {
        String accessToken = SYStringUtils.getUUIDNotExistSymbol();
        String refreshToken = SYStringUtils.getUUIDNotExistSymbol();

        GetTokenRespDTO respDTO = new GetTokenRespDTO();
        respDTO.setAccessToken(accessToken);
        respDTO.setRefreshToken(refreshToken);
//        respDTO.setExpireIn();
//        vivoUserTokenRedis.put(openId,respDTO,-1);
        redisTemplate.opsForValue().set(serviceConfig.getREDIS_ACCESS_TOKEN_NAME()+ Constants.specialSymbol.COLOM + openId, respDTO, serviceConfig.getREDIS_ACCESS_TOKEN_INVALID_TIME(), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(serviceConfig.getREDIS_REFRESH_TOKEN_NAME()+ Constants.specialSymbol.COLOM + openId, respDTO, serviceConfig.getREDIS_REFRESH_TOKEN_INVALID_TIME(), TimeUnit.SECONDS);
        return respDTO;
    }

    @Override
    public GetTokenRespDTO refreshVivoUserToken(String openId, String refreshToken) {
//        GetTokenRespDTO respDTO = vivoUserTokenRedis.get(openId);
        GetTokenRespDTO respDTO = new GetTokenRespDTO();
        String refreshTokenName = serviceConfig.getREDIS_REFRESH_TOKEN_NAME()+ Constants.specialSymbol.COLOM + openId;
        if(redisTemplate.hasKey(refreshTokenName)){
            respDTO.setAccessToken(SYStringUtils.getUUIDNotExistSymbol());
            redisTemplate.opsForValue().set(serviceConfig.getREDIS_ACCESS_TOKEN_NAME()+ Constants.specialSymbol.COLOM + openId, respDTO, serviceConfig.getREDIS_ACCESS_TOKEN_INVALID_TIME(), TimeUnit.SECONDS);
            respDTO.setCode("0");
            respDTO.setMsg("success");
        }else{
            respDTO.setCode("3156");
            respDTO.setMsg("refreshToken不存在");
        }
        return respDTO;
    }

    @Override
    public String getOpenIdForVivo(String code) {
        VivoCommonRespDTO respDTO = new VivoCommonRespDTO();
        JSONObject reqJson = new JSONObject();
        reqJson.put("clientId",vivoAppId);
//        bd153afbc08de203449dc4a36ca7d9d6
        byte[] bytes = RSAUtils.encryptByPublicKey( clientSecret.getBytes(), vivoPublicKey);
        String encryptedClientSecret = RSAUtils.binToHex(bytes);
        reqJson.put("clientSecret",encryptedClientSecret);
        reqJson.put("code",code);
        reqJson.put("encryptVersion","- RSA");
        reqJson.put("secretVersion","- V1");

        Map<String,String> headMap = new HashMap<>();
        headMap.put("appId",vivoAppId);
        headMap.put("timestamp",new Date().getTime()+"");
//        headMap.put("signature","");
        headMap.put("nonce",SYStringUtils.getUUIDNotExistSymbol());
        String result = VivoHttpUtils.httpPost(reqJson,reqAddress+"/user/getOpenId",headMap,appKey);
        return result;
    }

    @Override
    public GetTokenRespDTO userBinding(JSONObject getOpenIdJson) {
        GetTokenRespDTO result = new GetTokenRespDTO();
        //注册用户
        String vivoOpenId = getOpenIdJson.getJSONObject("data").getString("openId");
        String accessTokenCP = getOpenIdJson.getJSONObject("data").getString("accsssToken");
        int registerUserResult = this.registerUser(vivoOpenId);
        if (registerUserResult == 1 ){
            result = this.vivoUserLogin(vivoOpenId);
            String sendResult = this.sendUserBindingForVivo(result ,vivoOpenId , accessTokenCP);
        }
        return result;
    }

    private String sendUserBindingForVivo(GetTokenRespDTO reqDTO ,String vivoOpenId , String accessTokenCP) {
        String result = "";
        JSONObject reqJson = new JSONObject();
        reqJson.put("clientId",vivoAppId);
        reqJson.put("openId",vivoOpenId);
        reqJson.put("accessToken",reqDTO.getAccessToken());
        reqJson.put("refreshToken",reqDTO.getRefreshToken());
        reqJson.put("expireIn",reqDTO.getExpireIn());
        reqJson.put("accessTokenCP",accessTokenCP);

        Map<String,String> headMap = new HashMap<>();
        headMap.put("appId",vivoAppId);
        headMap.put("timestamp",new Date().getTime()+"");
        headMap.put("nonce",SYStringUtils.getUUIDNotExistSymbol());

        result = VivoHttpUtils.httpPost(reqJson,reqAddress+"/v1/user/getOpenId",headMap,appKey);
        return result;
    }

    @Override
    @Transactional
    public int registerUser(String vivoOpenId) {
        int result = 0 ;
        UserInfo userInfo = userInfoRepository.findUserInfoByVivoOpenId(vivoOpenId);
        if(userInfo == null){
//            UserInfo userInfo = new UserInfo();
            // 添加用户
//            userInfo.setEmail(registerDTO.getAccount());
//            userInfo.setPassword(MD5Utils.toMD5(registerDTO.getPassword()) );
//            userInfo.setUserName(registerDTO.getAccount());
            userInfo.setCreateTime(System.currentTimeMillis());
            userInfoRepository.save(userInfo);

            // 添加默认家庭
            UserHomeInfo userHomeInfo = new UserHomeInfo();
            userHomeInfo.setCreateDate(System.currentTimeMillis());
            userHomeInfo.setHomeName("默认家庭");
            userHomeInfo.setStatus((short) 1);
            userHomeInfo.setUserId(userInfo.getUserId());
            userHomeInfoRepository.save(userHomeInfo);

            // 添加默认房间
            RoomInfo roomInfo = new RoomInfo();
            roomInfo.setUserId(userInfo.getUserId());
            roomInfo.setRoomType((short) 1);
            roomInfo.setRoomName("默认");
            roomInfo.setHid(userHomeInfo.getHid());
            roomInfo.setCreateTime(System.currentTimeMillis());
            roomInfoRepository.save(roomInfo);
        }
        result = 1;
        return result;
    }

    @Override
    public GetTokenRespDTO vivoUserLogin(String vivoOpenId) {
        UserInfo userInfo = userInfoRepository.findUserInfoByVivoOpenId(vivoOpenId);

        String token = SYStringUtils.getUUIDNotExistSymbol();
        UserLogin userLogin = new UserLogin();
//        userLogin.setAccount(userLoginDTO.getAccount());
        userLogin.setUserId(userInfo.getUserId());
        userLogin.setLoginStatus((short) 1);
        userLogin.setToken(token);
        userLogin.setOs(getOs());
        userLogin.setCreateTime(System.currentTimeMillis());

        //获取之前登录信息,发送强制离线通知
//        String tokenPrefix = serviceConfig.getREDIS_USER_LOGIN_TOKEN() + Constants.specialSymbol.COLOM + userInfo.getLoginToken();
//        if(redisTemplate.hasKey(tokenPrefix)){
//            MqIotMessageDTO mqIotMessageDTO = new MqIotMessageDTO(CmdListEnum.diffLogin, serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.ONE)+ userInfo.getUserId(),System.currentTimeMillis());
//            MQTTUtils.push("u/" + userInfo.getLoginToken(), GsonUtils.toJSON(mqIotMessageDTO));
//            redisTemplate.delete(tokenPrefix);
//        }

        //保存新的token
        userInfoRepository.updateLoginTokenByUid(userLogin.getToken(), userInfo.getUserId());

        //保存到redis
        redisTemplate.opsForValue().set(serviceConfig.getREDIS_USER_LOGIN_TOKEN()+ Constants.specialSymbol.COLOM + userLogin.getToken(), userLogin, serviceConfig.getLONG_TOKEN_INVALID_TIME(), TimeUnit.DAYS);
        userInfo.setLoginToken(userLogin.getToken());

        GetTokenRespDTO respDTO = new GetTokenRespDTO();
        String refreshToken = SYStringUtils.getUUIDNotExistSymbol();
        respDTO.setAccessToken(token);
        respDTO.setRefreshToken(refreshToken);
        respDTO.setExpireIn(serviceConfig.getREDIS_ACCESS_TOKEN_INVALID_TIME().toString());
        redisTemplate.opsForValue().set(serviceConfig.getREDIS_ACCESS_TOKEN_NAME()+ Constants.specialSymbol.COLOM + vivoOpenId, respDTO, serviceConfig.getREDIS_ACCESS_TOKEN_INVALID_TIME(), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(serviceConfig.getREDIS_REFRESH_TOKEN_NAME()+ Constants.specialSymbol.COLOM + vivoOpenId, respDTO, serviceConfig.getREDIS_REFRESH_TOKEN_INVALID_TIME(), TimeUnit.SECONDS);
        return respDTO;
    }

    @Override
    public GetTokenRespDTO bindDerviceforVivo(String openId, String deviceId) {
        GetTokenRespDTO respDTO = new GetTokenRespDTO();
        respDTO.setMsg("");
        respDTO.setCode("0");

        JSONObject reqJson = new JSONObject();
        reqJson.put("openId",openId);
        reqJson.put("deviceId",deviceId);
//        reqJson.put("series",reqDTO.getRefreshToken());
//        reqJson.put("categoryCode",reqDTO.getExpireIn());

//        附件参数，OV服务端不做处理，如果要处理，传json格式的字符串；
//        reqJson.put("attachment",accessTokenCP);

        Map<String,String> headMap = new HashMap<>();
        headMap.put("appId",vivoAppId);
        headMap.put("timestamp",new Date().getTime()+"");
        headMap.put("nonce",SYStringUtils.getUUIDNotExistSymbol());

//        result = VivoHttpUtils.httpPost(reqJson,reqAddress+"/v1/user/getOpenId",headMap,appKey);

        return respDTO;
    }

    public Short getOs(){
        Short os = 0;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        ServiceLog serviceLog = (ServiceLog) request.getAttribute(serviceConfig.getSERVICE_LOG());
        if(serviceLog != null){
            os = serviceLog.getOs();
        }
        return os;

    }
}
