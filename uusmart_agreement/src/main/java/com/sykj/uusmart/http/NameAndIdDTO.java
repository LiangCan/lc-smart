package com.sykj.uusmart.http;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.utils.RegularExpressionUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2018/5/15 0015.
 */
public class NameAndIdDTO extends IdDTO{

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=2, max =36, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "name", required =true, value="名字,L(2~36)")
    @Pattern(regexp= RegularExpressionUtils.PEGEX_EX_STR , message= Constants.systemError.PARAM_VALUE_INVALID )
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
