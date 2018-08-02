package com.sykj.uusmart.service;


import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.req.UserAddDeviceDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.req.UserUpdateDeviceDTO;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Liang on 2016/12/23.
 */
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public interface DeviceInfoService {
    /** API 用户批量添加设备  */
    ResponseDTO userRisterDevice(UserAddDeviceDTO userAddDeviceDTO , ReqBaseDTO<IdDTO>timingReq);

    /** API 用户修改设备  */
    ResponseDTO userUpdateDevice(UserUpdateDeviceDTO userUpdateDeviceDTO);

    /** API 用户获取设备列表  */
    ResponseDTO userGetDeviceList(IdDTO idDTO);

    /** API 用户删除设备  */
    ResponseDTO userDelete(IdDTO idDTO ,ReqBaseDTO<IdDTO> reqDTO );

}
