package com.sykj.uusmart.http.tianmao;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/24 0024.
 */
public class ReqTianMaoBaseDTO {
    TMHeaderDTO header;

    Map<String, Object> payload;

    public TMHeaderDTO getHeader() {
        return header;
    }

    public void setHeader(TMHeaderDTO header) {
        this.header = header;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ReqTianMaoBaseDTO{" +
                "header=" + header +
                ", payload=" + payload.get("accessToken") +
                '}';
    }
}
