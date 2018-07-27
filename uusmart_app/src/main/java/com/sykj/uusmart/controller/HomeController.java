package com.sykj.uusmart.controller;


import com.sykj.uusmart.http.NameDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.service.UserHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@RestController
@Api(description = "家庭API")
@RequestMapping(value = "/auth/home/", method = RequestMethod.POST)
public class HomeController extends BaseController{

    @Autowired
    UserHomeService userHomeService;


    @ApiOperation(value="用户添加家庭")
    @RequestMapping(value="user/add.do")
    public String userAddHome(@RequestBody @Valid ReqBaseDTO<NameDTO> reqBaseDTO , BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userHomeService.userAddHome(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户查看家庭")
    @RequestMapping(value="user/get/list.do")
    public String userGetHomeList(@RequestBody @Valid ReqBaseDTO reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult);
        return GsonUtils.toJSON(userHomeService.userGetHomeList());
     }

}
