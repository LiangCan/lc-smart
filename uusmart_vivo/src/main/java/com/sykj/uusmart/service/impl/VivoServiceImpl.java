package com.sykj.uusmart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.vivo.VivoCommonRespDTO;
import com.sykj.uusmart.http.vivo.eventReport.EventReportDeviceReqDTO;
import com.sykj.uusmart.http.vivo.tokenManager.GetTokenRespDTO;
import com.sykj.uusmart.http.vivo.vivoUserLogin.VivoUserLoginRespDTO;
import com.sykj.uusmart.pojo.*;
import com.sykj.uusmart.pojo.redis.UserLogin;
import com.sykj.uusmart.repository.DeviceInfoRepository;
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
import java.util.*;
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

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

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
        if(redisTemplate.hasKey(refreshTokenName) ){
            GetTokenRespDTO getTokenRespDTO = (GetTokenRespDTO)redisTemplate.opsForValue().get(refreshTokenName) ;
            if(refreshToken.equals(getTokenRespDTO.getRefreshToken())){
                respDTO.setAccessToken(SYStringUtils.getUUIDNotExistSymbol());
                redisTemplate.opsForValue().set(serviceConfig.getREDIS_ACCESS_TOKEN_NAME()+ Constants.specialSymbol.COLOM + openId, respDTO, serviceConfig.getREDIS_ACCESS_TOKEN_INVALID_TIME(), TimeUnit.SECONDS);
                respDTO.setCode("0");
                respDTO.setMsg("success");

                UserInfo userInfo = userInfoRepository.findUserInfoByVivoOpenId( openId);
                userInfoRepository.updateUserTokenByUserId(respDTO.getAccessToken() , userInfo.getUserId());
                return  respDTO;
            }
        }
        respDTO.setCode("3156");
        respDTO.setMsg("refreshToken不存在");

        return respDTO;
    }

    @Override
    public String getOpenIdForVivo(String code) {
        VivoCommonRespDTO respDTO = new VivoCommonRespDTO();
        JSONObject reqJson = new JSONObject();
        reqJson.put("clientId",vivoClientId);
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
//        String result = "{\"code\":0,\"msg\":\"success\",\"data\":{\"openId\":\"81b7b1b84ca577dfe726347e7844b80a\",\"accessToken\":\"f60a29863c776f65740f9d2c5ee66b50\"}}";
        return result;
    }

    @Override
    @Transactional
    public ResponseDTO userBinding(JSONObject getOpenIdJson) {
        ResponseDTO result = new ResponseDTO();
        String vivoOpenId = getOpenIdJson.getJSONObject("data").getString("openId");
        String accessTokenCP = getOpenIdJson.getJSONObject("data").getString("accsssToken");
        //注册用户
        UserInfo registerUserResult = this.registerUser(vivoOpenId);
        if (registerUserResult != null ){
            result = this.vivoUserLogin(registerUserResult , vivoOpenId);
            String sendResult = this.sendUserBindingForVivo(result ,vivoOpenId , accessTokenCP);
        }
        return result;
    }

    private String sendUserBindingForVivo(ResponseDTO reqDTO ,String vivoOpenId , String accessTokenCP) {
        String result = "";
//        JSONObject reqDTOJson = JSONObject.parseObject(reqDTO.gethRC() );
        VivoUserLoginRespDTO vivoUserLoginRespDTO = (VivoUserLoginRespDTO)reqDTO.gethRC();
        JSONObject reqJson = new JSONObject();
        reqJson.put("clientId",vivoAppId);
        reqJson.put("openId",vivoOpenId);
//        reqJson.put("accessToken",reqDTO.);
//        reqJson.put("refreshToken",reqDTO.getRefreshToken());
//        reqJson.put("expireIn",reqDTO.getExpireIn());
        reqJson.put("accessToken",vivoUserLoginRespDTO.getLoginToken());
        reqJson.put("refreshToken",vivoUserLoginRespDTO.getRefreshToken());
        reqJson.put("expireIn",serviceConfig.getREDIS_ACCESS_TOKEN_INVALID_TIME().toString());


        reqJson.put("accessTokenCP",accessTokenCP);

        Map<String,String> headMap = new HashMap<>();
        headMap.put("appId",vivoAppId);
        headMap.put("timestamp",new Date().getTime()+"");
        headMap.put("nonce",SYStringUtils.getUUIDNotExistSymbol());

        result = VivoHttpUtils.httpPost(reqJson,reqAddress+"/user/userBinding",headMap,appKey);
        return result;
    }

    @Override
//    @Transactional
    public UserInfo registerUser(String vivoOpenId) {
//        Long result = Long.valueOf(0);
        UserInfo userInfo = userInfoRepository.findUserInfoByVivoOpenId(vivoOpenId);
        if(userInfo == null){
            userInfo = new UserInfo();
//            UserInfo userInfo = new UserInfo();
            // 添加用户
//            userInfo.setEmail(registerDTO.getAccount());
//            userInfo.setPassword(MD5Utils.toMD5(registerDTO.getPassword()) );
//            userInfo.setUserName(registerDTO.getAccount());
            userInfo.setCreateTime(System.currentTimeMillis());
            userInfo.setVivoOpenId( vivoOpenId  );
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
//            result = userInfo.getUserId();
        }
        return userInfo;
    }

    @Override
    public ResponseDTO vivoUserLogin(UserInfo userInfo , String vivoOpenId) {
        if(userInfo == null ){
           userInfo = userInfoRepository.findUserInfoByVivoOpenId(vivoOpenId);
        }

        String token = SYStringUtils.getUUIDNotExistSymbol();
        UserLogin userLogin = new UserLogin();
//        userLogin.setAccount(userLoginDTO.getAccount());
        userLogin.setUserId( userInfo.getUserId() );
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
        Gson gs = new Gson();  //需要下载Google的gson包  需要源码包的
        String userInfoStringData = gs.toJson(userInfo);
        VivoUserLoginRespDTO vivoUserLoginRespDTO = gs.fromJson(userInfoStringData, VivoUserLoginRespDTO.class);
        vivoUserLoginRespDTO.setRefreshToken( refreshToken);

        return new ResponseDTO(vivoUserLoginRespDTO);
    }



    @Override
    public boolean checkAccsssTokenNicety(String openId, String accessToken) {
        boolean result = false;
        String accessTokenName =  serviceConfig.getREDIS_ACCESS_TOKEN_NAME()+ Constants.specialSymbol.COLOM + openId;
        if(redisTemplate.hasKey(accessTokenName)){
            UserInfo userInfo = (UserInfo)redisTemplate.opsForValue().get(accessTokenName);
            if(accessToken.equals(userInfo.getLoginToken())){
                result = true;
            }
        }
        return result;
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
