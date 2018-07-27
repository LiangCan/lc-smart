package com.sykj.uusmart.mqtt.cmd;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.mqtt.cmd.input.MqIotConditionDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotAddObjectSubsDTO {

    //设备类型
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value=0, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @Max(value=2, message= Constants.systemError.PARAM_VALUE_LENGTH)
    private short productClass;

    //角色
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String role;

    // 情景ID
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private List<String>  eventCode;

    // 指令model or and
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String combModel;

    //条件
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private Map<String, String> trigger;

    public String getCombModel() {
        return combModel;
    }

    public void setCombModel(String combModel) {
        this.combModel = combModel;
    }

    public short getProductClass() {
        return productClass;
    }

    public void setProductClass(short productClass) {
        this.productClass = productClass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getEventCode() {
        return eventCode;
    }

    public void setEventCode(List<String> eventCode) {
        this.eventCode = eventCode;
    }

    public Map<String, String> getTrigger() {
        return trigger;
    }

    public void setTrigger(Map<String, String> trigger) {
        this.trigger = trigger;
    }
}
