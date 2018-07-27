package com.sykj.uusmart.controller;


import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.tianmao.ReqTianMaoBaseDTO;
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
@Api(description = "天猫精灵对接API")
@RequestMapping(value = "/auth/device/direct/tianmao", method = RequestMethod.POST)
public class TianMaoController extends BaseController{

    @Autowired
    ToTiamMaoService toTiamMaoService;

    @ApiOperation(value="指令入口网关")
    @RequestMapping(value="cmd")
    public String tiamMaoPushCmd(@RequestBody @Valid ReqTianMaoBaseDTO reqTianMaoBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.setAttribute("reqTianMaoBaseDTO", reqTianMaoBaseDTO);
        ValidatorUtils.validata(reqTianMaoBaseDTO);
        return GsonUtils.toJSON(toTiamMaoService.tianMaoBaseDTO(reqTianMaoBaseDTO));
    }

}
