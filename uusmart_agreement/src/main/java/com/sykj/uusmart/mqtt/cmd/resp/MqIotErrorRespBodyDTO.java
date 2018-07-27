package com.sykj.uusmart.mqtt.cmd.resp;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.utils.AliyunUtils;
import com.sykj.uusmart.utils.RegularExpressionUtils;
import com.sykj.uusmart.validator.CheckLong;

import javax.validation.constraints.*;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotErrorRespBodyDTO {

    private Integer code;

    private String errorMsg;

    public MqIotErrorRespBodyDTO() {
    }

    public MqIotErrorRespBodyDTO(Integer code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
