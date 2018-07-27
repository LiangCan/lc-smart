package com.sykj.uusmart.http.dingdong;

import com.sykj.uusmart.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ID DTO
 */
@ApiModel
public class DingDongPushCmdDTO {
    @NotEmpty(message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "1", value = "版本")
    private String versionid;

    @NotEmpty(message =Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "1", value = "状态")
    private String status;

    @ApiModelProperty(example = "{ \"deviceName\": \"插座\", \"cmd\": \"on\"  }", value = "解析内容")
    private DingDongCmdDTO slots = new DingDongCmdDTO();

    @NotEmpty(message =Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "f10ee90bcff644cdab1ed2a18c4ddd63", value = "流水号")
    private String sequence;

    @ApiModelProperty(example = "1873609207048", value = "时间戳")
    private Long timestamp;

    @ApiModelProperty(value = "叮咚用户信息")
    private DingDongUserDTO user = new DingDongUserDTO();

    @ApiModelProperty(value = "叮咚会话信息")
    private DingDongSessionDTO session = new DingDongSessionDTO();

    @NotEmpty(message =Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "让小U打开插座。", value = "slots")
    private String input_text;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DingDongCmdDTO getSlots() {
        return slots;
    }

    public void setSlots(DingDongCmdDTO slots) {
        this.slots = slots;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public DingDongUserDTO getUser() {
        return user;
    }

    public void setUser(DingDongUserDTO user) {
        this.user = user;
    }

    public DingDongSessionDTO getSession() {
        return session;
    }

    public void setSession(DingDongSessionDTO session) {
        this.session = session;
    }

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid;
    }

    public String getInput_text() {
        return input_text;
    }

    public void setInput_text(String input_text) {
        this.input_text = input_text;
    }

    @Override
    public String toString() {
        return "DingDongPushCmdDTO{" +
                "versionid='" + versionid + '\'' +
                ", status='" + status + '\'' +
                ", slots=" + slots.toString() +
                ", sequence='" + sequence + '\'' +
                ", timestamp=" + timestamp +
                ", user=" + user.toString() +
                ", session=" + session.toString() +
                ", input_text='" + input_text + '\'' +
                '}';
    }
}