/**
  * Copyright 2018 bejson.com 
  */
package com.sykj.uusmart.http.baidu;

import java.util.Map;

/**
 * Auto-generated: 2018-07-06 15:46:9
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BDMsgBeanDTO{

    private BDHeaderDTO header;
    private Map<String, Object> payload;


    public BDHeaderDTO getHeader() {
        return header;
    }

    public void setHeader(BDHeaderDTO header) {
        this.header = header;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
}