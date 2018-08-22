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
    public ResponseDTO userGetCheckCOde(UserGetCheckCodeDTO userGetCheckCodeDTO) {

        String redisEmil = serviceConfig.getREDIS_USER_EMAIL_NUMBER() + ":" +userGetCheckCodeDTO.getAccount();

        //3.是测试专用类型
        Long uid = userInfoRepository.findUserInfoByAccount(userGetCheckCodeDTO.getAccount());
        if(userGetCheckCodeDTO.getCodeType() == 1){
//             || userGetCheckCodeDTO.getCodeType() == 3
            //判断用户是否已经存在,存在就报异常
            CustomRunTimeException.checkNotNull(uid,"user");

//            if( userGetCheckCodeDTO.getCodeType() == 3){
//                return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
//            }
        }else{
            //判断用户是否不存在,不存在就报异常
            CustomRunTimeException.checkNull(uid,"user");
        }

        //获取该邮箱发送次数
        String pushNumberStr = stringRedisTemplate.opsForValue().get(redisEmil);
        int pushNumber = 0;
        if(!TextUtils.isEmpty(pushNumberStr)){
            pushNumber = Integer.parseInt(pushNumberStr);
        }


        //一天次数已经满了
        if(pushNumber >= serviceConfig.getCHECK_CODE_INVALID_NUMBER()) {

            log.error(userGetCheckCodeDTO.getAccount() + " 该邮箱发送频繁");
            throw new CustomRunTimeException(Constants.resultCode.API_PUSH_EMAIL_INVALID, Constants.systemError.API_PUSH_EMAIL_INVALID);
        }

        if(pushNumber != 0){
            pushNumber = pushNumber + 1;
        } else{
            pushNumber = 1;
        }

        //24小时只允许发5条
//        stringRedisTemplate.opsForValue().set(redisEmil, String.valueOf(pushNumber), 1 , TimeUnit.DAYS);
//       建议 key 最好 加上日期；时间如果保存 为一天 （86400） 的话，可能导致第二天的次数没重置为零。故而修改保存秒 数为第二天凌晨的秒数。 lgf 2018年7月18日09:43:16
        stringRedisTemplate.opsForValue().set(redisEmil, String.valueOf(pushNumber), getSecondsNextEarlyMorning() , TimeUnit.SECONDS);

        //创建发送校验码信息
        Integer checkCode = AliyunUtils.getCheckUtil(6);
        Short checkType = userGetCheckCodeDTO.getCodeType();
        UserCheckCode userCheckCode = new UserCheckCode();
        userCheckCode.setCreateTime(System.currentTimeMillis());
        userCheckCode.setCheckCode(checkCode);
        userCheckCode.setCodeType(checkType);
        userCheckCode.setAddress(userGetCheckCodeDTO.getAccount());
        userCheckCode.setCodeStatus(Constants.shortNumber.ONE);
        AliyunUtils.sample(userGetCheckCodeDTO.getAccount(), checkType, checkCode);
        //保存到redis ，过期删除
        stringRedisTemplate.opsForValue().set(serviceConfig.getREDIS_USER_CHECK_CODE() + ":" + userGetCheckCodeDTO.getAccount(), GsonUtils.toJSON(userCheckCode), serviceConfig.getCHECK_CODE_INVALID_TIME() , TimeUnit.MINUTES);

        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    public ResponseDTO userLogin(UserLoginDTO userLoginDTO) {
        //校验用户名和密码
        UserInfo userInfo = userInfoRepository.findUserInfoByEmailAndPassword(userLoginDTO.getAccount(), MD5Utils.toMD5(userLoginDTO.getPassword()));
        CustomRunTimeException.checkNull(userInfo,"user");
        //生成新的token
        String token = UUID.randomUUID().toString();
        UserLogin userLogin = new UserLogin();
        userLogin.setAccount(userLoginDTO.getAccount());
        userLogin.setUserId(userInfo.getUserId());
        userLogin.setLoginStatus((short) 1);
        userLogin.setToken(token);
        userLogin.setOs(getOs());
        userLogin.setCreateTime(System.currentTimeMillis());

        //获取之前登录信息,发送强制离线通知
        String tokenPrefix = serviceConfig.getREDIS_USER_LOGIN_TOKEN() + Constants.specialSymbol.COLOM + userInfo.getLoginToken();
       if(redisTemplate.hasKey(tokenPrefix)){
           MqIotMessageDTO mqIotMessageDTO = new MqIotMessageDTO(CmdListEnum.diffLogin, serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.ONE)+ userInfo.getUserId(),System.currentTimeMillis());
           MQTTUtils.push("u/" + userInfo.getLoginToken(), GsonUtils.toJSON(mqIotMessageDTO));
           redisTemplate.delete(tokenPrefix);
       }

       //保存新的token
        userInfoRepository.updateLoginTokenByUid(userLogin.getToken(), userInfo.getUserId());

       //保存到redis
        redisTemplate.opsForValue().set(serviceConfig.getREDIS_USER_LOGIN_TOKEN()+ Constants.specialSymbol.COLOM + userLogin.getToken(), userLogin, serviceConfig.getLONG_TOKEN_INVALID_TIME(), TimeUnit.DAYS);
        userInfo.setLoginToken(userLogin.getToken());
        return new ResponseDTO(userInfo);
    }

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

    @Override
    public ResponseDTO userRgister(UserRegisterDTO registerDTO) {
        String redisEmil = serviceConfig.getREDIS_USER_CHECK_CODE() + ":" + registerDTO.getAccount();

        Long uid = userInfoRepository.findUserInfoByAccount(registerDTO.getAccount());
        //判断用户是否已经存在,存在就报异常
        CustomRunTimeException.checkNotNull(uid,"user");

        //校验校验码
        if(stringRedisTemplate.hasKey(redisEmil)){
            String checkCodeStr = stringRedisTemplate.opsForValue().get(redisEmil);
            UserCheckCode userCheckCode = GsonUtils.toObj(checkCodeStr,UserCheckCode.class);
            if(userCheckCode.getCodeType() != Constants.shortNumber.ONE){
                CustomRunTimeException.checkNull(null,"check_code");
            }else{
                stringRedisTemplate.delete(redisEmil);
            }
        }else{
            CustomRunTimeException.checkNull(null,"check_code");
        }

        UserInfo userInfo = new UserInfo();
        // 添加用户
        userInfo.setEmail(registerDTO.getAccount());
        userInfo.setPassword(MD5Utils.toMD5(registerDTO.getPassword()) );
        userInfo.setUserName(registerDTO.getAccount());
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
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
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
