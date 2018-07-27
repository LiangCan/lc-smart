package com.sykj.uusmart.http.req.input;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.NameDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2018/5/10 0010.
 */
@ApiModel
public class AddWisdomImplementDTO extends IdDTO{


    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Min(value = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value = 2, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "1", required = true, value = "条件类型,S(1-2)")
    private Short implementType;

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Size(max = 32, min = 2, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "status", required=true, value = "执行字段,L(2~32)")
    private String implementName;

//    @NotNull(message =  Constants.systemError.PARAM_MISS)
//    @Size(max = 32, min = 2, message =  Constants.systemError.PARAM_VALUE_LENGTH)
//    @ApiModelProperty(example = "equal", required=true, value = "执行约定,L(2~32)")
//    private String appointment;

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Size(max = 32, min = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "1", required=true, value = "执行的值,L(1~32)")
    private String implementValue;

    public AddWisdomImplementDTO() {
    }

    public AddWisdomImplementDTO(Long id, Short implementType, String implementName, String implementValue) {
        super.setId(id);
        this.implementType = implementType;
        this.implementName = implementName;
        this.implementValue = implementValue;
    }

    public Short getImplementType() {
        return implementType;
    }

    public void setImplementType(Short implementType) {
        this.implementType = implementType;
    }

    public String getImplementName() {
        return implementName;
    }

    public void setImplementName(String implementName) {
        this.implementName = implementName;
    }

//    public String getAppointment() {
//        return appointment;
//    }
//
//    public void setAppointment(String appointment) {
//        this.appointment = appointment;
//    }

    public String getImplementValue() {
        return implementValue;
    }

    public void setImplementValue(String implementValue) {
        this.implementValue = implementValue;
    }
}
