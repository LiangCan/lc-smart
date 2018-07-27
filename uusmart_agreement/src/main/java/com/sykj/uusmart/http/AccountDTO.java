package com.sykj.uusmart.http;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.utils.RegularExpressionUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2018/5/15 0015.
 */
public class AccountDTO {

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=4, max =255, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "heiheihei@test.com", required =true, value="邮箱,L(4~255)")
    @Pattern(regexp= RegularExpressionUtils.REGEX_EMAIL , message= Constants.systemError.PARAM_VALUE_INVALID )
    public String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
