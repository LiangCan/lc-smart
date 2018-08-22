package com.sykj.uusmart.log;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.dbconfig.MessageConfig;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.pojo.ServiceLog;
import com.sykj.uusmart.pojo.redis.UserLogin;
import com.sykj.uusmart.service.ServiceLogService;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.utils.MessageUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.util.TextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Aspect
@Component
public class WebLogAspect {
    Log log= LogFactory.getLog(WebLogAspect.class);

    @Autowired
    ServiceLogService serviceLogService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ServiceConfig serviceConfig;


    @Pointcut("execution(public * com.sykj.uusmart.controller..*.*(..))")
    public void reqLog() {
    }

    @Before("reqLog()")
    /**请求拦截处理 用于拦截service层记录异常日志 */
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //设置语言
        String lang = request.getHeader("Accept-Languag");
        String Languag = MessageConfig.map.get("zh");
        if(!TextUtils.isEmpty(lang)){
            Languag = MessageConfig.map.get(lang);
        }else{
            lang = "zh";
        }
        MessageUtils.setLanguage(new Locale(lang, Languag));

        //创建请求记录，日志
        ServiceLog serviceLog = new ServiceLog();
        serviceLog.setCreateTime(System.currentTimeMillis());
        serviceLog.setReqIp(request.getRemoteAddr());
        serviceLog.setItfName(request.getRequestURI());

//        ReqBaseDTO reqBaseDTO = (ReqBaseDTO) joinPoint.getArgs()[0];
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString() + "\n     PARM : \n" + GsonUtils.toJSON(joinPoint.getArgs()[0]));

        //记录日志信息
        serviceLog.setReqTime(System.currentTimeMillis());
//        serviceLog.setOs(reqBaseDTO.gethE());
        serviceLog.setReqData(joinPoint.getArgs()[0].toString());
//        serviceLog.setKey(String.valueOf(reqBaseDTO.gethA()));

        //获取用户token
//        if(!TextUtils.isEmpty(reqBaseDTO.gethC())){
//            UserLogin userLogin = userInfoService.getTokenInfo(reqBaseDTO.gethC(), false);
//            if(userLogin != null){
//                serviceLog.setUserId(userLogin.getUserId());
//            }
//        }

        request.setAttribute(serviceConfig.getSERVICE_LOG(), serviceLog);
    }

    /**
     * 返回正常 用于拦截service层记录异常日志
     */
    @AfterReturning(returning = "ret", pointcut = "reqLog()")
    public void doAfterReturning (Object ret){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        ServiceLog serviceLog = (ServiceLog) request.getAttribute(serviceConfig.getSERVICE_LOG());
        serviceLog.setHandelStatus((short) Constants.mainStatus.SUCCESS);
        serviceLog.setReturnData(ret.toString());
        serviceLog.setReturnTime(System.currentTimeMillis());
        log.info(serviceLog.getItfName() + "\n RESPONSE :\n " + ret);
        serviceLogService.addServiceLog(serviceLog);
    }
}