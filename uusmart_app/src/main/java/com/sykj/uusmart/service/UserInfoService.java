package com.sykj.uusmart.service;


import com.sykj.uusmart.http.NameDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.AccountDTO;
import com.sykj.uusmart.http.req.*;
import com.sykj.uusmart.pojo.redis.UserLogin;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户的服务层
 */
public interface UserInfoService {

    /** API 用户修改信息 */
    ResponseDTO updateInfo(NameDTO nameDTO);

    /** API 用户上传头像 */
    ResponseDTO uploadHeadIcon(MultipartFile multipartFile);

    /** API 校验手机号码 */
    ResponseDTO checkAccount(AccountDTO acountDTO);

    /** API 用户注册 */
    ResponseDTO userRgister(UserRegisterDTO registerDTO);

    /** API  获取校验码*/
    ResponseDTO userGetCheckCOde(UserGetCheckCodeDTO userGetCheckCodeDTO);

    /** API 用户登录 */
    ResponseDTO userLogin(UserLoginDTO userLoginDTO);

    /** API 用户退出登录 */
    ResponseDTO userLoginOut(String token);

    UserLogin getTokenInfo(String token, boolean checkNull);

    Long getUserId(boolean checkNull);

    ResponseDTO updatePassword(UserUpdatePasswdDTO userUpdatePasswdDTO);

    ResponseDTO userResetPasswd(UserResetPasswdDTO userResetPasswdDTO);
//
//    /** 用户退出登录 */
//    ResponseDTO userOutLogin(TokenBaseDTO tokenBaseDTO);
//
//    /** 校验token 用户登录信息*/
//    UserLogin checkToken(String token, boolean check);
//
//    UserInfo byTokenFindUserInfo(String token);
//
//    /** API 用户修改信息 */
//    ResponseDTO userUpdateInfo(UserUpdateInfoDTO userUpdateInfoDTO);
//
//    /** API 用户修改密码 */
//    ResponseDTO userUpdatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO);
//
//    /** API 修改状态 */
//    ResponseDTO  badingDingDong(UserBindingDingDongDTO userBindingDingDongDTO);
//
//    /** API 修改状态 */
//    ResponseDTO  userUpdateStatus(HandeTypeDTO handeTypeDTO);
//
//    /** API 用户上传头像 */
//    ResponseDTO userUploadIcon(TokenBaseDTO tokenBaseDTO, MultipartFile multipartFile);
//
//    /** API 推送消息到APP */
//    ResponseDTO pushAppMessage(PushAppMsgDTO pushAppMsgDTO);
//



}
