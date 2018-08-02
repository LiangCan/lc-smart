package com.sykj.uusmart.controller;


import com.google.gson.Gson;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.req.UserAddDeviceDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.req.UserUpdateDeviceDTO;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.service.DeviceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@Api(description = "设备API")
@RestController
@RequestMapping(value = "/auth/device/", method = RequestMethod.POST)
public class DeviceController extends BaseController{

    @Autowired
    DeviceInfoService deviceInfoService;

    @ApiOperation(value="用户批量添加设备")
    @RequestMapping(value="/user/add/list.do", method = RequestMethod.POST)
    public String userAddDeviceList(@RequestBody ReqBaseDTO<UserAddDeviceDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        Gson gs = new Gson();
        String listStr = gs.toJson( reqBaseDTO );
        ReqBaseDTO<IdDTO> timingReq = gs.fromJson(listStr, ReqBaseDTO.class);

        return GsonUtils.toJSON(deviceInfoService.userRisterDevice(reqBaseDTO.gethG(), timingReq ));
    }

    @ApiOperation(value="用户修改设备信息接口")
    @RequestMapping(value="/user/update.do", method = RequestMethod.POST)
    public String userUpdateDeviceList(@RequestBody ReqBaseDTO<UserUpdateDeviceDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceInfoService.userUpdateDevice(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户查看设备")
    @RequestMapping(value="/user/get/list.do", method = RequestMethod.POST)
    public String userGetDeviceList(@RequestBody  ReqBaseDTO<IdDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceInfoService.userGetDeviceList(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户删除设备")
    @RequestMapping(value="/user/delete.do", method = RequestMethod.POST)
    public String userDelete(@RequestBody  ReqBaseDTO<IdDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());

        Gson gs = new Gson();
        String listStr = gs.toJson( reqBaseDTO );
        ReqBaseDTO<IdDTO> reqDTO = gs.fromJson(listStr, ReqBaseDTO.class);
        return GsonUtils.toJSON(deviceInfoService.userDelete(reqBaseDTO.gethG() , reqDTO  ) );
    }

    @ApiOperation(value="test")
    @RequestMapping(value="/test.do", method = RequestMethod.POST)
    public String test(@RequestBody ReqBaseDTO<NameAndIdDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult, reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceInfoService.test(reqBaseDTO));
    }
}
