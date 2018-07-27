package com.sykj.uusmart.service;

import com.sykj.uusmart.pojo.ServiceLog;

/**
 * Created by Liang on 2016/12/23.
 */
public interface ServiceLogService {
    /** API 添加 or 修改 日志服务*/
    void addServiceLog(ServiceLog serviceLog);

}
