package com.sykj.uusmart.http.alexa;

import com.sykj.uusmart.Constants;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class SaveAlexaOauthInfoDTO {

    @NotEmpty(message = Constants.systemError.PARAM_MISS)
    private String accessToken;

    @NotEmpty(message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "token", value = "token")
    private String oauthInfo;

    public String getOauthInfo() {
        return oauthInfo;
    }

    public void setOauthInfo(String oauthInfo) {
        this.oauthInfo = oauthInfo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
