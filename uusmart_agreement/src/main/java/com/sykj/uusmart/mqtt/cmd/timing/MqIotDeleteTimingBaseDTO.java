package com.sykj.uusmart.mqtt.cmd.timing;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.mqtt.cmd.timing.input.MqIotTimerTaskDTO;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class MqIotDeleteTimingBaseDTO {
    //角色
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String role;

    // 情景ID
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private MqIotTimerTaskDTO timertask;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public MqIotTimerTaskDTO getTimertask() {
        return timertask;
    }

    public void setTimertask(MqIotTimerTaskDTO timertask) {
        this.timertask = timertask;
    }
}
