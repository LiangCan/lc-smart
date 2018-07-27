package com.sykj.uusmart.http; /**
 * @Title: ResponseDTO.java
 * @Package com.yijucloud.menu.dto
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Henry
 * @date 2015年7月28日 下午2:40:27
 * @version V1.0
 */


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.utils.ServiceUtils;

/**
 * 统一的返回参数DTO
 * version : 1.0
 */
public class ResponseDTO {
    /** 返回状态码 默认为0  */
    private String hRA = String.valueOf(Constants.mainStatus.UNKNOWN);

    /** 返回时间戳 默认为0  */
    private Long hRB = System.currentTimeMillis();

    /** 返回json数据 */
    private Object hRC;

    /**  错误信息 */
    private String hRD = Constants.mainStatus.SYSTEM_ERROR;

    public ResponseDTO() {
    }

    /** 请求成功赋值 */
    public ResponseDTO(Object data) {
        this.hRA = String.valueOf(Constants.mainStatus.SUCCESS);
        this.hRD = "";
        this.hRC = data;
    }

    /** 请求失败的赋值 */
    public ResponseDTO(String resultCode, String errorMsg){
        this.hRC = null;
        this.hRA = resultCode;
        this.hRD = ServiceUtils.messageUtils.getMessage(errorMsg);
    }

    /** 请求失败的赋值 */
    public ResponseDTO(String resultCode, String errorMsg, Object[] params){
        this.hRC = null;
        this.hRA = resultCode;
        this.hRD = ServiceUtils.messageUtils.getMessage(errorMsg, params);
    }

    /** 请求失败的赋值 */
    public ResponseDTO(String resultCode, String errorMsg, Object[] params, String resultData){
        this.hRC = resultData;
        this.hRA = resultCode;
        this.hRD = ServiceUtils.messageUtils.getMessage(errorMsg, params);
    }


    /** 请求失败的赋值 */
    public ResponseDTO(String resultCode, String errorMsg, String resultData){
        this.hRC = resultData;
        this.hRA = resultCode;
        this.hRD = ServiceUtils.messageUtils.getMessage(errorMsg);
    }

    public String gethRA() {
        return hRA;
    }

    public void sethRA(String hRA) {
        this.hRA = hRA;
    }

    public Long gethRB() {
        return hRB;
    }

    public void sethRB(Long hRB) {
        this.hRB = hRB;
    }

    public Object gethRC() {
        return hRC;
    }

    public void sethRC(Object hRC) {
        this.hRC = hRC;
    }

    public String gethRD() {
        return hRD;
    }

    public void sethRD(String hRD) {
        this.hRD = hRD;
    }
}
