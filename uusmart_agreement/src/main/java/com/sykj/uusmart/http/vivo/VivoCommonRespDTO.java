package com.sykj.uusmart.http.vivo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class VivoCommonRespDTO<T> implements Serializable {

	private static final long serialVersionUID = 3997124446365032582L;
	
	/**
	 * 错误码
	 */
	@ApiModelProperty(value = "错误码", required = true)
	private String code = "1000";
	@ApiModelProperty(value = "数据", required = true)
	private T devices;
	@ApiModelProperty(value = "消息提示")
	private String msg;

	public VivoCommonRespDTO() {
		super();
	}

	public VivoCommonRespDTO(String code, String msg ) {
		super();
		this.code = code;
//		this.bizCode = bizCode;
		this.msg = msg;
	}

	public VivoCommonRespDTO(String code, String msg, T devices ) {
		super();
		this.code = code;
		this.msg = msg;
		this.devices = devices;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getDevices() {
		return devices;
	}

	public void setDevices(T devices) {
		this.devices = devices;
	}
}
