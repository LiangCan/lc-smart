package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
public class UserUpdatePasswdDTO {


    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "123456", required = true, value = "用户 旧 密码,L(6~16)")
    @Size(max = 16, min = 6, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    private String oldPassword;

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "123456", required = true, value = "用户 新 密码,L(6~16)")
    @Size(max = 16, min = 6, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
