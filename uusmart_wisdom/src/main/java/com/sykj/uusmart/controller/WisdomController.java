package com.sykj.uusmart.controller;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserAddWisdomDTO;
import com.sykj.uusmart.service.WisdomService;
import com.sykj.uusmart.utils.GsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@RestController
@Api(description = "智能API")
@RequestMapping(value = "/auth/wisdom/", method = RequestMethod.POST)
public class WisdomController extends BaseController{

    @Autowired
    WisdomService wisdomService;

    @ApiOperation(value="test ...")
    @RequestMapping(value="test.do")
    public String test(@RequestBody @Valid ReqBaseDTO<IdDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(wisdomService.testDelete(reqBaseDTO.gethG()));
    }
    @ApiOperation(value="用户删除某个设备的智能")
    @RequestMapping(value="user/delete/device.do")
    public String userDeleteDeviceWisdom(@RequestBody @Valid ReqBaseDTO<IdDTO> reqBaseDTO , BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(wisdomService.userDeleteDeviceWisdom(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户创建一个智能")
    @RequestMapping(value="user/add.do")
    public String userAddWisdom(@RequestBody @Valid ReqBaseDTO<UserAddWisdomDTO> reqBaseDTO , BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        validata(reqBaseDTO.gethG().getWisdomConditionDTOList());
        validata(reqBaseDTO.gethG().getWisdomImplementDTOList());
        return GsonUtils.toJSON(wisdomService.userAddWisdom(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户查看智能列表")
    @RequestMapping(value="user/get/list.do")
    public String userGetWisdom(@RequestBody @Valid ReqBaseDTO reqBaseDTO , BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult);
        return GsonUtils.toJSON(wisdomService.userGetWisdomList());
    }

    @ApiOperation(value="用户删除智能")
    @RequestMapping(value="user/delete.do")
    public String userDeleteWisdom(@RequestBody @Valid ReqBaseDTO<IdDTO> reqBaseDTO , BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(wisdomService.userDeleteWisdom(reqBaseDTO.gethG()));
    }




    @ApiOperation(value="用户修改某个智能消息")
    @RequestMapping(value="user/update/info.do")
    public String useUpdateWisdom(@RequestBody @Valid ReqBaseDTO<UserAddWisdomDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult);
        return GsonUtils.toJSON(new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS));
    }

}
