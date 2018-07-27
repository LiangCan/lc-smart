package com.sykj.uusmart.http;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.utils.RegularExpressionUtils;
import com.sykj.uusmart.validator.CheckLong;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;


/**
 * Created by Administrator on 2018/5/15 0015.
 */
public class ReqBaseDTO<T>{
    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Pattern(regexp= RegularExpressionUtils.REGEX_NUMBER , message= Constants.systemError.PARAM_VALUE_INVALID )
    @Size(max = 5, min = 5, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "10000", required =true, value="请求Key,  L(5)")
    private String hA ;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @CheckLong(min=13, max =13)
    @ApiModelProperty(example = "1560927290321", required =true, value="请求时间戳, L(13)")
    private Long hB;

    private T hG;//请求体

    @Size(max = 36, min = 36, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(value="用户token,  L(36)", example ="54d5f2b6-889c-40dd-bd62-2b16031ba474")
    private String hC;

    @Size(max = 36, min = 36, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(value="签名,密钥Key,  L(36)", example ="bc5b3d5eaec85e4ec52a15d556142e498c63")
    private String hD;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Max(value = 4, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @Min(value = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty( required =true, value="请求类型,  S(1~4)" , example ="1")
    private Short hE;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(max = 16, min = 1, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty( required =true, value="加密方式[0/AES/MD5/..],  L(1~16)" , example ="0")
    private String hF;

    @Pattern(regexp= RegularExpressionUtils.NUMBER_DIAN , message= Constants.systemError.PARAM_VALUE_INVALID )
    @Size(max = 16, min = 4, message =  Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty( required =true, value="协议版本,  L(4~16)" , example ="0.0.1")
    private String hH;

    public String gethA() {
        return hA;
    }

    public void sethA(String hA) {
        this.hA = hA;
    }

    public Long gethB() {
        return hB;
    }

    public void sethB(Long hB) {
        this.hB = hB;
    }

    public T gethG() {
        return hG;
    }

    public void sethG(T hG) {
        this.hG = hG;
    }

    public String gethC() {
        return hC;
    }

    public void sethC(String hC) {
        this.hC = hC;
    }

    public String gethD() {
        return hD;
    }

    public void sethD(String hD) {
        this.hD = hD;
    }

    public Short gethE() {
        return hE;
    }

    public void sethE(Short hE) {
        this.hE = hE;
    }

    public String gethF() {
        return hF;
    }

    public void sethF(String hF) {
        this.hF = hF;
    }

    public String gethH() {
        return hH;
    }

    public void sethH(String hH) {
        this.hH = hH;
    }

    @Override
    public String toString() {
        return "ReqBaseDTO{" + "hA=" + hA + ", hB=" + hB + ", hG=" +  GsonUtils.toJSON(hG)  + ", hC='" + hC + '\'' + ", hD='" + hD + '\'' + ", hE=" + hE + ", hF='" + hF + '\'' + ", hH='" + hH + '\'' + '}';
    }
}
