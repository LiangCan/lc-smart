package com.sykj.uusmart.http.req;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2018/7/3 0003.
 */
public class UserUploadHeadIconDTO {

    @ApiModelProperty( required = true, value = "头像")
    private MultipartFile userIcon;

    public MultipartFile getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(MultipartFile userIcon) {
        this.userIcon = userIcon;
    }

}
