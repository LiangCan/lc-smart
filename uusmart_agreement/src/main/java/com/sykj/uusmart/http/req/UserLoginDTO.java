package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.AccountDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
public class UserLoginDTO extends AccountDTO {


    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "123456", required = true, value = "用户密码,L(6~16)")
    @Size(max = 16, min = 6, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
