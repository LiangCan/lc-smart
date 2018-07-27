package com.sykj.uusmart.http.dingdong;

import com.sykj.uusmart.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Map;

@ApiModel
public class DingDongUserDTO {

    @NotEmpty(message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "9181c619bbe34e9e935248a70a199e37", value = "user_id")
    private String user_id;

    @NotEmpty(message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "{}", value = "解析内容")
    private Map<String, String> attributes;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "DingDongUserDTO{" +
                "user_id='" + user_id + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}