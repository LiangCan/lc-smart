package com.sykj.uusmart.mqtt.cmd.input;

import com.sykj.uusmart.Constants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/6/11 0011.
 */
public class MqIotConditionDTO {

    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String name;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String compModel;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String value;

    public MqIotConditionDTO() {
    }

    public MqIotConditionDTO(String name, String compModel, String value) {
        this.name = name;
        this.compModel = compModel;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompModel() {
        return compModel;
    }

    public void setCompModel(String compModel) {
        this.compModel = compModel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
