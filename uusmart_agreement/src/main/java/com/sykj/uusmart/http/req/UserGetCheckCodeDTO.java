package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.AccountDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

/**
 * 用户发送短信校验码DTO
 */
@ApiModel
public class UserGetCheckCodeDTO extends AccountDTO{


    /**
     * 码类型
     */
    @NotNull(message = Constants.systemError.PARAM_MISS)
    @Min(value = 1,  message = Constants.systemError.PARAM_VALUE_INVALID)
    @Max(value = 2,  message = Constants.systemError.PARAM_VALUE_INVALID)
    @ApiModelProperty(example = "1", value = "校验码类型,S(1~2)")
    private Short codeType;

    public UserGetCheckCodeDTO() {
    }

    public Short getCodeType() {
        return codeType;
    }

    public void setCodeType(Short codeType) {
        this.codeType = codeType;
    }
}