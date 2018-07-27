package com.sykj.uusmart.service;


import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.req.UserAddRoomDTO;
import com.sykj.uusmart.http.req.UserAddRoomDeviceDTO;

import java.util.List;


/**
 * Created by Liang on 2017/3/22.
 */
public interface UserRoomService {

    /** API  用户添加家庭下的房间 */
    ResponseDTO userAddRoom(UserAddRoomDTO userAddRoomDTO);

    /** API  用户删除房间 */
    ResponseDTO userDeleteRoom(IdDTO idDTO);

    /** API  用户获取家庭下的房间列表 */
    ResponseDTO userGetRoomList(IdDTO idDTO);

    /** API  用户修改家庭下的房间 */
    ResponseDTO  userUpdateRoom(UserAddRoomDTO userAddRoomDTO);

    /** API  用户设置设备到房间里面 */
    ResponseDTO userSetRoomDevice(List<UserAddRoomDeviceDTO> userAddRoomDeviceDTO);
}
