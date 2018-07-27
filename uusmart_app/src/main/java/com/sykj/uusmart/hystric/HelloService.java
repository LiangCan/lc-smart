package com.sykj.uusmart.hystric;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("uusmart-wisdom")
public interface HelloService {


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String hello();
}