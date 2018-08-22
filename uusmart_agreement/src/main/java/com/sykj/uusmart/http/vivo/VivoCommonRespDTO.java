package com.sykj.uusmart.http.vivo;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.utils.RegularExpressionUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;

public class VivoCommonRespDTO {
    @ApiModelProperty(example = "返回码", required =true, value="返回码")
    @Pattern(regexp= RegularExpressionUtils.PEGEX_EX_STR , message= Constants.systemError.PARAM_VALUE_INVALID )
    private String code ;
    @ApiModelProperty(example = "返回消息", required =true, value="返回消息 ")
    @Pattern(regexp= RegularExpressionUtils.PEGEX_EX_STR , message= Constants.systemError.PARAM_VALUE_INVALID )
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
