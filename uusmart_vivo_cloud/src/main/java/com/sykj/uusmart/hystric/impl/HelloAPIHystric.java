package com.sykj.uusmart.hystric.impl;

import com.mysql.jdbc.log.LogUtils;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.hystric.HelloService;
import org.springframework.stereotype.Component;

@Component
public class HelloAPIHystric implements HelloService {


    @Override
    public ResponseDTO hello(ReqBaseDTO reqBaseDTO) {
        System.out.println(" -----  >  进 熔断器了啦！！" );
        return new ResponseDTO();
    }
}
