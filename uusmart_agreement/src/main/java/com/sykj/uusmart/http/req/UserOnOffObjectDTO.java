package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.validator.CheckLong;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashMap;

/**
 * 开关定时，智能 2018/7/10 0010.
 */
public class UserOnOffObjectDTO extends IdDTO{

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value = 1)
    @Max(value = 2)
    @ApiModelProperty(example = "1", required =true, value="状态,L(1~2)")
    private Short status;

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
