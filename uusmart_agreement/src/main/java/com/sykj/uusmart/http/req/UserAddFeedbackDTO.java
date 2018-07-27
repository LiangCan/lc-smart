package com.sykj.uusmart.http.req;

import com.sykj.uusmart.Constants;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAddFeedbackDTO {

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "网关重启后出现App控制不了", required = true, value = "反馈内容 ")
    @Size(max = 255, min = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    private String feedbackContent;

    @NotNull(message =  Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "13800138000", required = true, value = "联系方式，邮箱、QQ，手机皆可")
    @Size(max = 30, min = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    private String contactWay;

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }
}
