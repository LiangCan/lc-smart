package com.sykj.uusmart.http.dingdong;

import com.sykj.uusmart.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Map;

@ApiModel
public class DingDongSessionDTO {

    @NotEmpty(message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "true", value = "user_id")
    private boolean is_new;

    @NotEmpty(message =Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "be44d9f4f13a4e789c2d1b5f3d897e84", value = "解析内容")
    private String session_id;

    @NotEmpty(message =Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "{}", value = "解析内容")
    private Map<String, String> attributes;

    public boolean isIs_new() {
        return is_new;
    }

    public void setIs_new(boolean is_new) {
        this.is_new = is_new;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "DingDongSessionDTO{" +
                "is_new=" + is_new +
                ", session_id='" + session_id + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}