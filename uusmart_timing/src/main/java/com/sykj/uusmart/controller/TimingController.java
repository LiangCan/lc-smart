package com.sykj.uusmart.controller;


import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserAddDeviceDTO;
import com.sykj.uusmart.http.req.UserAddDeviceTimingDTO;
import com.sykj.uusmart.http.req.UserOnOffObjectDTO;
import com.sykj.uusmart.http.req.UserUpdateDeviceTimingDTO;
import com.sykj.uusmart.http.tianmao.ReqTianMaoBaseDTO;
import com.sykj.uusmart.service.DeviceTimingInfoService;
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
import java.util.List;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@RestController
@Api(description = "设备定时API")
@RequestMapping(value = "/auth/timing", method = RequestMethod.POST)
public class TimingController extends BaseController{


    @Autowired
    DeviceTimingInfoService deviceTimingInfoService;

    @ApiOperation(value="test")
    @RequestMapping(value="/test.do", method = RequestMethod.POST)
    public String test(@RequestBody ReqBaseDTO<NameAndIdDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult, reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceTimingInfoService.test(reqBaseDTO));
    }

    @ApiOperation(value="用户添加设备定时")
    @RequestMapping(value="/user/add.do", method = RequestMethod.POST)
    public String userAddDeviceTiming(@RequestBody ReqBaseDTO<UserAddDeviceTimingDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult, reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceTimingInfoService.userAddDeviceTiming(reqBaseDTO.gethG()));
    }


    @ApiOperation(value="用户修改设备的定时")
    @RequestMapping(value="/user/update.do", method = RequestMethod.POST)
    public String userUpdateDeviceTiming(@RequestBody ReqBaseDTO<UserUpdateDeviceTimingDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult, reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceTimingInfoService.userUpdateDeviceTiming(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户打开关闭设备的定时")
    @RequestMapping(value="/user/on/off.do", method = RequestMethod.POST)
    public String userOnOffDeviceTiming(@RequestBody ReqBaseDTO<UserOnOffObjectDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult, reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceTimingInfoService.userOnOffDeviceTiming(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户查看设备的定时")
    @RequestMapping(value="/user/query/list.do", method = RequestMethod.POST)
    public String userQueryDeviceTiming(@RequestBody ReqBaseDTO<IdDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult, reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceTimingInfoService.userQueryDeviceTiming(reqBaseDTO.gethG()));
    }

    @ApiOperation(value="用户删除设备的定时")
    @RequestMapping(value="/user/delete.do", method = RequestMethod.POST)
    public String useDeleteDeviceTiming(@RequestBody ReqBaseDTO<IdDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult, reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceTimingInfoService.userDeleteDeviceTiming(reqBaseDTO.gethG()));
    }


    @ApiOperation(value="用户根据设备删除设备的所有定时")
    @RequestMapping(value="/user/by/device/delete.do", method = RequestMethod.POST)
    public String useDeleteDeviceAllTiming(@RequestBody ReqBaseDTO<IdDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult, reqBaseDTO.gethG());
        return GsonUtils.toJSON(deviceTimingInfoService.userDeleteDeviceTimingAll(reqBaseDTO.gethG()));
    }
}
