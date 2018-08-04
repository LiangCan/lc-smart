package com.sykj.uusmart.mqtt.cmd;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.mqtt.cmd.input.MqIotConditionDTO;
import com.sykj.uusmart.utils.RegularExpressionUtils;

import javax.validation.constraints.*;
import java.util.List;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotAddObjectGenDTO {

    //角色
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String role;

    // 情景ID
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String eventCode;

    // 指令model or and
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String combModel;

    //修改次数
    private int updateNum;

    //条件
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private List<MqIotConditionDTO> condition;


    public int getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(int updateNum) {
        this.updateNum = updateNum;
    }

    public String getCombModel() {
        return combModel;
    }

    public void setCombModel(String combModel) {
        this.combModel = combModel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public List<MqIotConditionDTO> getCondition() {
        return condition;
    }

    public void setCondition(List<MqIotConditionDTO> condition) {
        this.condition = condition;
    }
}
