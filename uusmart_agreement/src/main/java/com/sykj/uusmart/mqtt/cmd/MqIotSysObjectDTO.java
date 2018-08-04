package com.sykj.uusmart.mqtt.cmd;

import com.sykj.uusmart.Constants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class MqIotSysObjectDTO {
    //角色
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private String role;

    // 情景ID
    @NotNull( message = Constants.systemError.PARAM_MISS)
    private Map<Long, Integer>  datas;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Map<Long, Integer> getDatas() {
        return datas;
    }

    public void setDatas(Map<Long, Integer> datas) {
        this.datas = datas;
    }
}
