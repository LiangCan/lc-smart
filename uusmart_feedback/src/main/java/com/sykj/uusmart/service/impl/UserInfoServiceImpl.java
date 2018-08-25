package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.AccountDTO;
import com.sykj.uusmart.http.NameDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.*;
import com.sykj.uusmart.mqtt.MQTTUtils;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.pojo.RoomInfo;
import com.sykj.uusmart.pojo.ServiceLog;
import com.sykj.uusmart.pojo.UserHomeInfo;
import com.sykj.uusmart.pojo.UserInfo;
import com.sykj.uusmart.pojo.redis.UserCheckCode;
import com.sykj.uusmart.pojo.redis.UserLogin;
import com.sykj.uusmart.repository.RoomInfoRepository;
import com.sykj.uusmart.repository.UserHomeInfoRepository;
import com.sykj.uusmart.repository.UserInfoRepository;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.utils.AliyunUtils;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.utils.MD5Utils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.sykj.uusmart.utils.TimeUtils.getSecondsNextEarlyMorning;

//import com.sykj.uusmart.service.PictureService;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class UserInfoServiceImpl implements UserInfoService  {


    private  Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    RoomInfoRepository roomInfoRepository;

    @Autowired
    UserHomeInfoRepository userHomeInfoRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    ServiceConfig serviceConfig;




    @Override
    public ResponseDTO updateInfo(NameDTO nameDTO) {
        Long userId = getUserId(true);
        userInfoRepository.updateUserNameByUid(nameDTO.getName(), userId);
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

//    @Override
//    public ResponseDTO uploadHeadIcon(MultipartFile multipartFile) {
//        Long userId = getUserId(true);
//        String url = pictureService.uploadPicture(multipartFile);
//        userInfoRepository.updateUserIconByUid(url,userId);
//        return new ResponseDTO(url);
//    }


    @Override
    public ResponseDTO userLoginOut(String token) {
        deleteTokenInfo(token);
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
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


    @Override
    public ResponseDTO checkAccount(AccountDTO acountDTO) {
        boolean result = true;
        Long uid = userInfoRepository.findUserInfoByAccount(acountDTO.getAccount());
        if (uid == null || uid < 1) {
            result = false;
        }

        return new ResponseDTO(result);
    }


    /**
     * 校验token
     * @param token
     * @param check 是否校验为空，如果是 会抛出异常
     * @return
     */
    @Override
    public  UserLogin getTokenInfo(String token, boolean check) {
        UserLogin userLogin = (UserLogin) redisTemplate.opsForValue().get(serviceConfig.getREDIS_USER_LOGIN_TOKEN() + Constants.specialSymbol.COLOM + token);

        if (userLogin == null ) {
            if(check){
                throw new CustomRunTimeException(Constants.resultCode.API_LOGIN_TOKEN_INVALID, Constants.systemError.API_LOGIN_TOKEN_INVALID);
            }
            return null;
        }
        //刷新token
        redisTemplate.opsForValue().set(serviceConfig.getREDIS_USER_LOGIN_TOKEN(), userLogin, serviceConfig.getLONG_TOKEN_INVALID_TIME(), TimeUnit.DAYS);
        return userLogin;
    }

    public void deleteTokenInfo(String token){
        redisTemplate.delete(serviceConfig.getREDIS_USER_LOGIN_TOKEN() + Constants.specialSymbol.COLOM + token);
    }

    /**
     * 获取uid
     * @param
     * @return
     */
    @Override
    public Long getUserId(boolean checkNull){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        ServiceLog serviceLog = (ServiceLog) request.getAttribute(serviceConfig.getSERVICE_LOG());
        CustomRunTimeException.checkNull(serviceLog,"serviceLog");

        if(checkNull){
            if(serviceLog.getUserId() == null){
                throw new CustomRunTimeException(Constants.resultCode.API_LOGIN_TOKEN_INVALID, Constants.systemError.API_LOGIN_TOKEN_INVALID);
            }
        }
        return serviceLog.getUserId();

    }

    @Override
    public ResponseDTO updatePassword(UserUpdatePasswdDTO userUpdatePasswdDTO) {
//        userInfoRepository.findUserInfoByEmailAndPassword( "", userUpdatePasswdDTO.getOldPassword() );
        Long userId = getUserId(true);
        UserInfo  userInfo = userInfoRepository.findUserInfoByUserId( userId );
//      如果输入密码和数据库密码一致，则修改；
        String oldPasswd =  MD5Utils.toMD5( userUpdatePasswdDTO.getOldPassword() );
        String newPasswd =  MD5Utils.toMD5( userUpdatePasswdDTO.getNewPassword() );
        if ( userInfo.getPassword() .equals( oldPasswd )){
            userInfoRepository.updateUserPasswdByUserId( newPasswd , userId );
            return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
        }else {
            return new ResponseDTO(Constants.resultCode.PARAM_VALUE_INVALID , Constants.systemError.PARAM_VALUE_INVALID);
        }
    }

    @Override
    public ResponseDTO userResetPasswd(UserResetPasswdDTO userResetPasswdDTO) {
        //redis 的 key 拼接。
        String redisEmil = serviceConfig.getREDIS_USER_CHECK_CODE() + ":" + userResetPasswdDTO.getAccount();
        Long uid = userInfoRepository.findUserInfoByAccount(userResetPasswdDTO.getAccount());
        //判断用户是否已经存在,存在就报异常
        CustomRunTimeException.checkNull(uid,"user");

        //校验校验码
        if(stringRedisTemplate.hasKey(redisEmil)){
            String checkCodeStr = stringRedisTemplate.opsForValue().get(redisEmil);
            UserCheckCode userCheckCode = GsonUtils.toObj(checkCodeStr,UserCheckCode.class);
            if(userCheckCode.getCodeType() != Constants.shortNumber.TWO){
                CustomRunTimeException.checkNull(null,"check_code");
            }else{
                stringRedisTemplate.delete(redisEmil);
            }
        }else{
            CustomRunTimeException.checkNull(null,"check_code");
        }
        String newPasswd = MD5Utils.toMD5( userResetPasswdDTO.getPassword() );
        userInfoRepository.updateUserPasswdByUserId( newPasswd , uid );
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }
}
