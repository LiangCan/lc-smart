package com.sykj.uusmart.hystric;

import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.hystric.impl.HelloAPIHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "uusmart-wisdom-test", fallback = HelloAPIHystric.class)
public interface HelloService {

    @RequestMapping(value = "/auth/wisdom/test.do", method = RequestMethod.POST)
    ResponseDTO hello(@RequestBody ReqBaseDTO reqBaseDTO);
}