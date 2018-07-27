package com.sykj.uusmart.http.resp;

import com.sykj.uusmart.pojo.Wisdom;
import com.sykj.uusmart.pojo.WisdomCondition;
import com.sykj.uusmart.pojo.WisdomImplement;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12 0012.
 */
public class RespQueryWisdomListDTO {

    private Wisdom wisdom;

    private List<WisdomCondition> wisdomConditions;

    private List<WisdomImplement> wisdomImplements;


    public RespQueryWisdomListDTO() {
    }

    public RespQueryWisdomListDTO(Wisdom wisdom, List<WisdomCondition> wisdomConditions, List<WisdomImplement> wisdomImplements) {
        this.wisdom = wisdom;
        this.wisdomConditions = wisdomConditions;
        this.wisdomImplements = wisdomImplements;
    }

    public Wisdom getWisdom() {
        return wisdom;
    }

    public void setWisdom(Wisdom wisdom) {
        this.wisdom = wisdom;
    }

    public List<WisdomCondition> getWisdomConditions() {
        return wisdomConditions;
    }

    public void setWisdomConditions(List<WisdomCondition> wisdomConditions) {
        this.wisdomConditions = wisdomConditions;
    }

    public List<WisdomImplement> getWisdomImplements() {
        return wisdomImplements;
    }

    public void setWisdomImplements(List<WisdomImplement> wisdomImplements) {
        this.wisdomImplements = wisdomImplements;
    }
}
