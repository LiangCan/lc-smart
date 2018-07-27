package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.NameAndIdDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
public class UserAddRoomDTO extends NameAndIdDTO {


    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "1.png", required = true, value = "房间图片标识,L(1~16)")
    @Size(max = 16, min = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    private String roomIcon;

    public String getRoomIcon() {
        return roomIcon;
    }

    public void setRoomIcon(String roomIcon) {
        this.roomIcon = roomIcon;
    }
}
