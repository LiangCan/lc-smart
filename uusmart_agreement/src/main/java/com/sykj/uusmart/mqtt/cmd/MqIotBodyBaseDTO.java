package com.sykj.uusmart.mqtt.cmd;

import com.sykj.uusmart.Constants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/6/29 0029.
 */
public class MqIotBodyBaseDTO {

    //设备类型
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value=0, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value=99, message= Constants.systemError.PARAM_VALUE_LENGTH)
    private short productClass;

    //重发次数
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value=0, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value=5, message= Constants.systemError.PARAM_VALUE_LENGTH)
    private short retryCount;

    public short getProductClass() {
        return productClass;
    }

    public void setProductClass(short productClass) {
        this.productClass = productClass;
    }

    public short getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(short retryCount) {
        this.retryCount = retryCount;
    }
}
