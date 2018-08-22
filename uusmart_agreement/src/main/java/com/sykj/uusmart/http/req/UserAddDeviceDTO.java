package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.req.input.AddDeivceDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
@ApiModel
public class UserAddDeviceDTO extends IdDTO {

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Min(value = 1)
    @Max(value = 4)
    @ApiModelProperty(example = "1", required = true, value = "设备分类 1.WIFI类型设备 2.蓝牙 3.摄像头 4.红外,S(1~4)")
    private Short classification;

    @NotNull(message = Constants.systemError.PARAM_MISS)
    @Size(max = 64, min = 2, message = Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "SmartHome2.4G", required=true, value = "wifi的SSID,L(2~64)")
    private String deviceWifiSSID;


    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Min(value = 1)
    @Max(value = 16)
    @ApiModelProperty(example = "1", required=true, value = "房间ID,L(1~16)")
    private Long roomId;

    @Valid
    @NotNull(message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty( required=true, value = "添加的集合,L(1~64)")
    List<AddDeivceDTO> addDeviceDTOList;

    public Short getClassification() {
        return classification;
    }

    public void setClassification(Short classification) {
        this.classification = classification;
    }

    public String getDeviceWifiSSID() {
        return deviceWifiSSID;
    }

    public void setDeviceWifiSSID(String deviceWifiSSID) {
        this.deviceWifiSSID = deviceWifiSSID;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<AddDeivceDTO> getAddDeviceDTOList() {
        return addDeviceDTOList;
    }

    public void setAddDeviceDTOList(List<AddDeivceDTO> addDeviceDTOList) {
        this.addDeviceDTOList = addDeviceDTOList;
    }
}
