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
public class AddWisdomConditionDTO extends IdDTO{


    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Min(value = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value = 2, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "1", required = true, value = "条件类型,S(1-2) 1设备条件执行，2定时执行")
    private Short conditionType;

//    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Size(max = 32, min = 2, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "status", value = "条件字段,L(2~32)")
    private String conditionName;

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Size(max = 32, min = 2, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "equal", required=true, value = "条件约定,L(2~32)")
    private String appointment;

//    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Size(max = 32, min = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "1", required=true, value = "条件值,L(1~32)")
    private String conditionValue;


    public Short getConditionType() {
        return conditionType;
    }

    public void setConditionType(Short conditionType) {
        this.conditionType = conditionType;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }
}
