package com.sykj.uusmart.mqtt.cmd.resp;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotRespDeviceLoginDTO {
    private int code;

    private short retryCount;

    private short productClass;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public short getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(short retryCount) {
        this.retryCount = retryCount;
    }

    public short getProductClass() {
        return productClass;
    }

    public void setProductClass(short productClass) {
        this.productClass = productClass;
    }
}
