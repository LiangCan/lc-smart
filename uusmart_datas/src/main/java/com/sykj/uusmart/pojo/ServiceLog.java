package com.sykj.uusmart.pojo;

/**
 * Created by Liang on 2016/12/23.
 */

import javax.persistence.*;

@Entity
@Table(name = "t_service_log")
public class ServiceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id", length = 16)
    private Long logId;//日志ID

    @Column(name = "req_key", columnDefinition = " varchar(16) DEFAULT NULL   COMMENT '请求key' ")
    private String key;//请求KEY

    @Column(name = "user_id", columnDefinition = " bigint(16) COMMENT '用户ID' ")
    private Long userId;//用户ID

    @Column(name = "itf_name", columnDefinition = " varchar(255) DEFAULT NULL COMMENT '处理接口' ")
    private String itfName;//处理接口

    @Column(name = "os", columnDefinition = " smallint(2)  COMMENT '请求设备类型(1.PC,2.WEB,3.安卓,4.IOS)' ")
    private Short os; //1.PC,2.WEB,3.安卓,4.IOS

    @Column(name = "ssid", columnDefinition = " varchar(36) DEFAULT NULL COMMENT '请求IP' ")
    private String ssid;//SSID

    @Column(name = "req_ip", columnDefinition = " varchar(32) DEFAULT NULL COMMENT '请求IP' ")
    private String reqIp;//请求IP

    @Column(name = "req_data", columnDefinition = " longtext DEFAULT NULL COMMENT '请求数据' ")
    private String reqData;//请求数据

    @Column(name = "req_time", columnDefinition = " bigint(13)  DEFAULT NULL COMMENT '请求时间戳' ")
    private Long reqTime;//请求时间戳

    @Column(name = "create_time", columnDefinition = " bigint(13)  DEFAULT NULL COMMENT '创建时间戳' ")
    private Long createTime;//创建时间戳

    @Column(name = "return_time", columnDefinition = " bigint(13)   DEFAULT NULL COMMENT '返回时间戳' ")
    private Long returnTime;//返回时间戳

    @Column(name = "return_data", columnDefinition = " longtext  DEFAULT NULL COMMENT '返回数据' ")
    private String returnData;//返回数据

    @Column(name = "handel_status", columnDefinition = " smallint(2)  DEFAULT NULL COMMENT '最终结果 0 未知 1 成功 9 失败' ")
    private Short handelStatus;//最终结果 0 未知 1 成功 9 失败

    public ServiceLog() {
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Short getOs() {
        return os;
    }

    public void setOs(Short os) {
        this.os = os;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getItfName() {
        return itfName;
    }

    public void setItfName(String itfName) {
        this.itfName = itfName;
    }

    public String getReqIp() {
        return reqIp;
    }

    public void setReqIp(String reqIp) {
        this.reqIp = reqIp;
    }

    public String getReqData() {
        return reqData;
    }

    public void setReqData(String reqData) {
        this.reqData = reqData;
    }

    public Long getReqTime() {
        return reqTime;
    }

    public void setReqTime(Long reqTime) {
        this.reqTime = reqTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public Short getHandelStatus() {
        return handelStatus;
    }

    public void setHandelStatus(Short handelStatus) {
        this.handelStatus = handelStatus;
    }


}
