package com.sykj.uusmart.mqtt;

import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotMessageDTO<T> {

    private MqIotHeaderDTO header;

    private T body;

    private Object append;


    public MqIotMessageDTO(CmdListEnum actionType, String sourceId , String destId ,T body, Object append) {
        this.header = new MqIotHeaderDTO(actionType, sourceId, destId);
        this.body = body;
        this.append = append;
    }

    public MqIotMessageDTO(CmdListEnum actionType, String sourceId , String destId ,T body) {
        this.header = new MqIotHeaderDTO(actionType, sourceId, destId);
        this.body = body;
    }

    public MqIotMessageDTO() {
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Object getAppend() {
        return append;
    }

    public void setAppend(Object append) {
        this.append = append;
    }

    public MqIotHeaderDTO getHeader() {
        return header;
    }

    public void setHeader(MqIotHeaderDTO header) {
        this.header = header;
    }
}
