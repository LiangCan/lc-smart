package com.sykj.uusmart.http;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.validator.CheckLong;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/5/15 0015.
 */
public class IdDTO {

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @CheckLong(min=1, max =16)
    @ApiModelProperty(example = "1", required =true, value= "id , L(1-16)")
    private long id;

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
