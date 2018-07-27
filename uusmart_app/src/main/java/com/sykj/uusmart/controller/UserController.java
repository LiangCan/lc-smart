package com.sykj.uusmart.controller;


import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.AccountDTO;
import com.sykj.uusmart.http.NameDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.req.*;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.utils.GsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@RestController
@Api(description = "用户API")
@RequestMapping(value = "/auth/user/", method = RequestMethod.POST)
public class UserController extends BaseController{

    @Autowired
    UserInfoService userInfoService;

    @ApiOperation(value="用户上传头像")
    @RequestMapping(value="upload/head/image.do")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hA", value = "请求Key, L(5)", example = "10000", required = true),
            @ApiImplicitParam(name = "hB",example = "1560927290321", required =true, value="请求时间戳, L(13)"),
            @ApiImplicitParam(name = "hC",value="用户token,  L(36)", example ="54d5f2b6-889c-40dd-bd62-2b16031ba474"),
            @ApiImplicitParam(name = "hD",value="签名,密钥Key,  L(36)", example ="bc5b3d5eaec85e4ec52a15d556142e498c63"),
            @ApiImplicitParam(name = "hE",required =true, value="请求类型,  S(1~4)" , example ="1"),
            @ApiImplicitParam(name = "hF",  required =true, value="加密方式[0/AES/MD5/..],  L(1~16)" , example ="0"),
            @ApiImplicitParam(name = "hH",  required =true, value="协议版本,  L(4~16)" , example ="0.0.1")
    }
    )
    public String UploadHeadImage( String hA, Long hB, String hC, String hD, short hE, String hF, String hH, @RequestParam("userIcon") MultipartFile file)throws CustomRunTimeException {
        return GsonUtils.toJSON(userInfoService.uploadHeadIcon(file));
    }


    @ApiOperation(value="校验注册帐号是否已经存在")
    @RequestMapping(value="check/register/account.do")
    public String checkRgisterAccount(@RequestBody @Valid ReqBaseDTO<AccountDTO> reqBaseDTO ,
                                      BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userInfoService.checkAccount(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="修改用户信息")
    @RequestMapping(value="update/info.do")
    public String updateInfo(@RequestBody @Valid ReqBaseDTO<NameDTO> reqBaseDTO ,
                                      BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userInfoService.updateInfo(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户注册帐号")
    @RequestMapping(value="/register.do", method = RequestMethod.POST)
    public String userRgister(@RequestBody @Valid ReqBaseDTO<UserRegisterDTO> reqBaseDTO , BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userInfoService.userRgister(reqBaseDTO.gethG()));
    }


    @ApiOperation(value="用户登录")
    @RequestMapping(value="/login.do", method = RequestMethod.POST)
    public String userLogin(@RequestBody @Valid ReqBaseDTO<UserLoginDTO> reqBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userInfoService.userLogin(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户退出登录")
    @RequestMapping(value="/login/out.do", method = RequestMethod.POST)
    public String userLoginOut(@RequestBody @Valid ReqBaseDTO reqBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult);
        return GsonUtils.toJSON(userInfoService.userLoginOut(reqBaseDTO.gethC()));
    }

    @ApiOperation(value="用户获取校验码")
    @RequestMapping(value="/get/checkCode.do", method = RequestMethod.POST)
    public String userGetCheckCode(@RequestBody @Valid ReqBaseDTO<UserGetCheckCodeDTO> reqBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userInfoService.userGetCheckCOde(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="修改密码")
    @RequestMapping(value="update/passwd.do")
    public String updatePassword (@RequestBody @Valid ReqBaseDTO< UserUpdatePasswdDTO > reqBaseDTO ,
                             BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userInfoService.updatePassword(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="重置密码")
    @RequestMapping(value="update/resetPasswd.do")
    public String resetPasswd (@RequestBody @Valid ReqBaseDTO<UserResetPasswdDTO> reqBaseDTO ,
                                  BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userInfoService.userResetPasswd( reqBaseDTO.gethG()) );
    }
}
