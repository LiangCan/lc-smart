package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.validator.CheckLong;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class UserUpdateDeviceTimingDTO extends IdDTO{

    @NotNull(message = Constants.systemError.PARAM_MISS)
    @CheckLong(max = 16, min = 1)
    @ApiModelProperty(example = "1", required=true, value = "定时任务的ID ,L(1~16)")
    private Long timingId;

    @NotNull(message = Constants.systemError.PARAM_MISS)
    @Size(max = 32, min = 2, message = Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "定时打开", required=true, value = "定时的名字 ,L(2~32)")
    private String dtName;

    @NotNull(message = Constants.systemError.PARAM_MISS)
    @Size(max = 7, min = 7, message = Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "0001000", required=true, value = "执行日期 ,L(7)")
    private String dtDays;

    @NotNull(message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "{\"time\":{\"year\":2018,\"month\":7,\"date\":8,\"hour\":12,\"min\":47},\"trigger\":{\"onoff\":\"1\"}}", required=true, value = "启动的信息 ,L(*)")
    private LinkedHashMap startInfo;

    @NotNull(message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "{\"time\":{\"year\":2018,\"month\":7,\"date\":8,\"hour\":12,\"min\":48},\"trigger\":{\"onoff\":\"0\"}}", required=true, value = "启动的信息 ,L(*)")
    private LinkedHashMap endInfo;

    @NotNull(message = Constants.systemError.PARAM_MISS)
    @Size(max = 32, min = 2, message = Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "self", required=true, value = "模型(once,every,week,rest,self) ,L(2~32)")
    private String dtMode;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value = 1)
    @Max(value = 2)
    @ApiModelProperty(example = "1", required =true, value="状态,L(1~2)")
    private Short dtStatus;

    public Short getDtStatus() {
        return dtStatus;
    }

    public void setDtStatus(Short dtStatus) {
        this.dtStatus = dtStatus;
    }

    public Long getTimingId() {
        return timingId;
    }

    public void setTimingId(Long timingId) {
        this.timingId = timingId;
    }

    public String getDtDays() {
        return dtDays;
    }

    public void setDtDays(String dtDays) {
        this.dtDays = dtDays;
    }

    public String getDtName() {
        return dtName;
    }

    public void setDtName(String dtName) {
        this.dtName = dtName;
    }

    public LinkedHashMap getStartInfo() {
        return startInfo;
    }

    public void setStartInfo(LinkedHashMap startInfo) {
        this.startInfo = startInfo;
    }

    public LinkedHashMap getEndInfo() {
        return endInfo;
    }

    public void setEndInfo(LinkedHashMap endInfo) {
        this.endInfo = endInfo;
    }

    public String getDtMode() {
        return dtMode;
    }

    public void setDtMode(String dtMode) {
        this.dtMode = dtMode;
    }

}
