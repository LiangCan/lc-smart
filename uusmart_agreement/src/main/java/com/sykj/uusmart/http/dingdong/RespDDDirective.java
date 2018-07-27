package com.sykj.uusmart.http.dingdong;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class RespDDDirective {

    private List<RespDingDongDirective> directive_items = new ArrayList<>();

    public List<RespDingDongDirective> getDirective_items() {
        return directive_items;
    }

    public void setDirective_items(List<RespDingDongDirective> directive_items) {
        this.directive_items = directive_items;
    }
}
