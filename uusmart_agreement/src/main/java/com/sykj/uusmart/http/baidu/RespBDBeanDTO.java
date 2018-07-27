/**
  * Copyright 2018 bejson.com 
  */
package com.sykj.uusmart.http.baidu;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2018-07-06 16:51:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RespBDBeanDTO<T> {

    private BDHeaderDTO header;
    private T payload;

    public RespBDBeanDTO() {
    }

    public RespBDBeanDTO(BDHeaderDTO header, T payload) {
        this.header = header;
        this.payload = payload;
    }
    public BDHeaderDTO getHeader() {
        return header;
    }

    public void setHeader(BDHeaderDTO header) {
        this.header = header;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}