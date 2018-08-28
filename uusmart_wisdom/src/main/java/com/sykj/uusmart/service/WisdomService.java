package com.sykj.uusmart.service;


import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserAddWisdomDTO;
import com.sykj.uusmart.http.req.UserOnOffObjectDTO;
import com.sykj.uusmart.http.req.UserUpdateWisdomDTO;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.cmd.MqIotSysObjectDTO;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.utils.MessageUtils;

import java.util.Map;


/**
 * Created by Liang on 2017/3/22.
 */
public interface WisdomService {
    ResponseDTO userOnOffWisdom(UserOnOffObjectDTO userOnOffObjectDTO);

    /** API  测试事务接口*/
    ResponseDTO testDelete(IdDTO idDTO);

    /** API  用户修改智能*/
    ResponseDTO userUpdateWisdom(UserUpdateWisdomDTO userUpdateWisdomDTO);

    /** API  用户添加*/
    ResponseDTO userAddWisdom(UserAddWisdomDTO userAddWisdomDTO);

    /** API  用户获取智能列表*/
    ResponseDTO userGetWisdomList();

    /** API  用户删除智能*/
    ResponseDTO userDeleteWisdom(IdDTO idDTO);

    /** API  用户删除某个设备的智能*/
    ResponseDTO userDeleteDeviceWisdom(IdDTO idDTO);

    /** SERVER  情景指令通知*/
    void notifyWisdom(String msg, Long wid);

    /** SERVER 同步智能 */
    void synWisdom(MqIotSysObjectDTO mqIotSysObjectDTO, DeviceInfo deviceInfo);

}
