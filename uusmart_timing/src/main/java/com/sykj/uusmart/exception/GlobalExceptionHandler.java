package com.sykj.uusmart.exception;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.tianmao.ReqTianMaoBaseDTO;
import com.sykj.uusmart.pojo.ServiceLog;
import com.sykj.uusmart.service.ServiceLogService;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice(basePackages={"com.sykj.uusmart.controller"})
public class GlobalExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseDTO httpMessageNotReadableException(HttpMessageNotReadableException e,  HttpServletResponse response){
        ResponseDTO responseDTO = new ResponseDTO(Constants.resultCode.PARAM_FORMAT_ERROR,Constants.systemError.PARAM_FORMAT_ERROR, new Object[] {"json"});
        saveSerLog(responseDTO);
        return responseDTO;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseDTO methodArgumentNotValidException(MethodArgumentNotValidException e,  HttpServletResponse response){
        ResponseDTO responseDTO = new ResponseDTO(Constants.resultCode.PARAM_VALUE_INVALID,Constants.systemError.PARAM_VALUE_INVALID, new Object[] {"main"});
        saveSerLog(responseDTO);
        return responseDTO;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody //在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
    public ResponseDTO exceptionHandler(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(Constants.resultCode.SYSTEM_ERROR, Constants.systemError.SYSTEM_ERROR);
        saveSerLog(responseDTO);
        return responseDTO;
    }

    @ExceptionHandler(CustomRunTimeException.class)
    @ResponseBody //在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
    public ResponseDTO exceptionHandler(CustomRunTimeException e, HttpServletResponse response) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.sethRA(e.getErrorCode());
        responseDTO.sethRD(e.getErrorMsg());
        saveSerLog(responseDTO);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if("/auth/device/direct/tianmao/cmd".equals(request.getRequestURI())){
            ReqTianMaoBaseDTO reqTianMaoBaseDTO = (ReqTianMaoBaseDTO) request.getAttribute("reqTianMaoBaseDTO");
            reqTianMaoBaseDTO.getHeader().setName("ErrorResponse");
            Map<String, String> pay = new HashMap<>();
            pay.put("deviceId","1");
            pay.put("errorCode",e.getErrorCode());
            pay.put("message",e.getErrorMsg());
        }
        return responseDTO;
    }

    @Autowired
    ServiceLogService serviceLogService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    UserInfoService userInfoService;

    private void saveSerLog(ResponseDTO responseDTO){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        ServiceLog serviceLog = (ServiceLog) request.getAttribute(serviceConfig.getSERVICE_LOG());
        if(serviceLog == null){
            serviceLog = new ServiceLog();
            serviceLog.setCreateTime(System.currentTimeMillis());
            String [] urls = request.getRequestURI().split("/");
            serviceLog.setItfName(request.getRequestURI());
            serviceLog.setReqIp(request.getRemoteAddr());
            // 记录下请求内容
            log.error("URL : " + request.getRequestURL().toString() + "\n PARM: \n  异常请求 " );

        }
        serviceLog.setHandelStatus((short) Constants.mainStatus.FAIL);
        serviceLog.setReturnData(GsonUtils.toJSON(responseDTO));
        serviceLog.setReturnTime(System.currentTimeMillis());
        log.info(serviceLog.getItfName() + "\n RESPONSE :\n " + serviceLog.getReturnData());
        serviceLogService.addServiceLog(serviceLog);
    }
}
