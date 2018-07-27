package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.AccountDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
public class UserResetPasswdDTO extends AccountDTO  {

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "123456", required = true, value = "用户密码,L(6~16)")
    @Size(max = 16, min = 6, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    private String password;

    @NotNull(message = Constants.systemError.PARAM_MISS)
    @Min(value= 100000, message = Constants.systemError.PARAM_VALUE_INVALID)
    @Max(value= 999999, message = Constants.systemError.PARAM_VALUE_INVALID)
    @ApiModelProperty(example = "123456", value = "校验码,L(6)")
    private Integer checkCode;

    public Integer getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(Integer checkCode) {
        this.checkCode = checkCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
