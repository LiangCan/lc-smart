package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.req.input.AddWisdomConditionDTO;
import com.sykj.uusmart.http.req.input.AddWisdomImplementDTO;
import com.sykj.uusmart.validator.CheckLong;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
@ApiModel
public class UserUpdateWisdomDTO extends NameAndIdDTO {

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Min(value = 1)
    @Max(value = 4)
    @ApiModelProperty(example = "1", required = true, value = "智能类型 默认：1点击执行，2设备联动执行 3定时执行")
    private Short type;

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @Min(value = 1)
    @Max(value = 2)
    @ApiModelProperty(example = "1", required = true, value = "1 条件都满足的情况下执行，2 其中一个满足就执行")
    private Short andOrRun;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=2, max =36, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "name", required =true, value="名字,L(2~36)")
    private String wisdomIcon;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Min(value = 1)
    @Max(value = 2)
    @ApiModelProperty(example = "1", required =true, value="状态,L(1~2)")
    private Short wisdomStatus;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @CheckLong(min=1, max =16)
    @ApiModelProperty(example = "1", required =true, value= "智能的ID , L(1-16)")
    private Long wisdomId;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    List<AddWisdomConditionDTO> wisdomConditionDTOList;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    List<AddWisdomImplementDTO> wisdomImplementDTOList;

    public List<AddWisdomConditionDTO> getWisdomConditionDTOList() {
        return wisdomConditionDTOList;
    }

    public void setWisdomConditionDTOList(List<AddWisdomConditionDTO> wisdomConditionDTOList) {
        this.wisdomConditionDTOList = wisdomConditionDTOList;
    }

    public List<AddWisdomImplementDTO> getWisdomImplementDTOList() {
        return wisdomImplementDTOList;
    }

    public void setWisdomImplementDTOList(List<AddWisdomImplementDTO> wisdomImplementDTOList) {
        this.wisdomImplementDTOList = wisdomImplementDTOList;
    }

    public Short getWisdomStatus() {
        return wisdomStatus;
    }

    public void setWisdomStatus(Short wisdomStatus) {
        this.wisdomStatus = wisdomStatus;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getAndOrRun() {
        return andOrRun;
    }

    public void setAndOrRun(Short andOrRun) {
        this.andOrRun = andOrRun;
    }

    public String getWisdomIcon() {
        return wisdomIcon;
    }

    public void setWisdomIcon(String wisdomIcon) {
        this.wisdomIcon = wisdomIcon;
    }

    public Long getWisdomId() {
        return wisdomId;
    }

    public void setWisdomId(Long wisdomId) {
        this.wisdomId = wisdomId;
    }
}
