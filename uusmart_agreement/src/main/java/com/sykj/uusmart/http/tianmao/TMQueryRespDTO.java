package com.sykj.uusmart.http.tianmao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/24 0024.
 */
public class TMQueryRespDTO extends TMDiscoveryRespDTO {

    private List<Map<String, String>> properties;

    public TMQueryRespDTO() {
    }

    public TMQueryRespDTO(List<Map<String, String>> properties) {
        this.properties = properties;
    }

    public TMQueryRespDTO(TMHeaderDTO header, Object payload, List<Map<String, String>> properties) {
        super(header, payload);
        this.properties = properties;
    }

    public List<Map<String, String>> getProperties() {
        return properties;
    }

    public void setProperties(List<Map<String, String>> properties) {
        this.properties = properties;
    }
}
