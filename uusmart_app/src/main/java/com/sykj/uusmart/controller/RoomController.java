package com.sykj.uusmart.controller;

import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.req.UserAddRoomDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.req.UserAddRoomDeviceDTO;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.service.UserRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@RestController
@Api(description = "房间API")
@RequestMapping(value = "/auth/room/", method = RequestMethod.POST)
public class RoomController extends BaseController {

    @Autowired
    UserRoomService userRoomService;


    @ApiOperation(value = "用户添加房间")
    @RequestMapping(value = "user/add.do")
    public String userAddRoom(@RequestBody @Valid ReqBaseDTO<UserAddRoomDTO> reqBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userRoomService.userAddRoom(reqBaseDTO.gethG()));
    }

    @ApiOperation(value = "用户修改房间名称,图片")
    @RequestMapping(value = "user/update.do")
    public String userUpdateRoom(@RequestBody @Valid ReqBaseDTO<UserAddRoomDTO> reqBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userRoomService.userUpdateRoom(reqBaseDTO.gethG()));
    }

    @ApiOperation(value = "用户删除房间")
    @RequestMapping(value = "user/delete.do")
    public String userDeleteRoom(@RequestBody @Valid ReqBaseDTO<IdDTO> reqBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userRoomService.userDeleteRoom(reqBaseDTO.gethG()));
    }

    @ApiOperation(value = "用户查看家庭下的房间列表")
    @RequestMapping(value = "user/get/list.do")
    public String userGetRoomList(@RequestBody @Valid ReqBaseDTO<IdDTO> reqBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userRoomService.userGetRoomList(reqBaseDTO.gethG()));
    }

    @ApiOperation(value = "用户添加设备到房间")
    @RequestMapping(value = "user/add/device.do")
    public String userSetRoomDevice(@RequestBody @Valid ReqBaseDTO<List<UserAddRoomDeviceDTO>> reqBaseDTO, BindingResult bindingResult) throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(userRoomService.userSetRoomDevice(reqBaseDTO.gethG()));
    }
}
