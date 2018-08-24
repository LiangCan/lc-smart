package com.sykj.uusmart.http.alexa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PropertiesDTO {
    List<Map<String, String >> supported = new ArrayList<>();
    boolean proactivelyReported = true;
    boolean retrievable = true;

    public List<Map<String, String>> getSupported() {
        return supported;
    }

    public void setSupported(List<Map<String, String>> supported) {
        this.supported = supported;
    }

    public boolean isProactivelyReported() {
        return proactivelyReported;
    }

    public void setProactivelyReported(boolean proactivelyReported) {
        this.proactivelyReported = proactivelyReported;
    }

    public boolean isRetrievable() {
        return retrievable;
    }

    public void setRetrievable(boolean retrievable) {
        this.retrievable = retrievable;
    }
}
