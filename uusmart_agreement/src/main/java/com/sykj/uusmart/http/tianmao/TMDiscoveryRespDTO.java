package com.sykj.uusmart.http.tianmao;

/**
 * Created by Administrator on 2017/11/24 0024.
 */
public class TMDiscoveryRespDTO<T> {
    TMHeaderDTO header;

    T payload;

    public TMDiscoveryRespDTO() {
    }

    public TMDiscoveryRespDTO(TMHeaderDTO header, T payload) {
        this.header = header;
        this.payload = payload;
    }

    public TMHeaderDTO getHeader() {
        return header;
    }

    public void setHeader(TMHeaderDTO header) {
        this.header = header;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
