package com.sykj.uusmart.controller;


import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.service.DeviceService;
import com.sykj.uusmart.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@RestController
@RequestMapping(value = "/auth/device/", method = RequestMethod.POST)
public class DeviceController {

    @Autowired
    DeviceService deviceService;

//    @RequestMapping(value="/push/cmd.do", method = RequestMethod.POST)
//    public String devicePush(MqIotMessageDTO mqIotMessageDTO) throws CustomRunTimeException {
//
//        return GsonUtils.toJSON(deviceService.pushCmdToDevice(mqIotMessageDTO));
//    }

}
