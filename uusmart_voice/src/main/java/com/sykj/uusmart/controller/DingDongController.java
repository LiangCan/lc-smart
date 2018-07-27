package com.sykj.uusmart.controller;


import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.dingdong.DingDongPushCmdDTO;
import com.sykj.uusmart.http.dingdong.ReqDDBaDingUserDTO;
import com.sykj.uusmart.http.tianmao.ReqTianMaoBaseDTO;
import com.sykj.uusmart.service.ToDingDongService;
import com.sykj.uusmart.service.ToTiamMaoService;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.validator.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@RestController
@Api(description = "叮咚对接API")
public class DingDongController extends BaseController{

    @Autowired
    ToDingDongService toDingDongService;

    @ApiOperation(value="绑定帐号token")
    @RequestMapping(value="/auth/user/bading/dingdong.do", method = RequestMethod.POST)
    public String dingDongBdingUser(@RequestBody @Valid ReqDDBaDingUserDTO reqTianMaoBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult);
        return GsonUtils.toJSON(toDingDongService.badingDingDong(reqTianMaoBaseDTO));
    }

    @ApiOperation(value="叮咚平台发送指令过来")
    @RequestMapping(value="auth/device/direct/push/cmd", method = RequestMethod.POST)
    public String dingDongPushCmd(@RequestBody @Valid DingDongPushCmdDTO dingDongPushCmdDTO, BindingResult bindingResult) throws CustomRunTimeException {
        return GsonUtils.toJSON(toDingDongService.dingDongPushCmd(dingDongPushCmdDTO));
    }
}
