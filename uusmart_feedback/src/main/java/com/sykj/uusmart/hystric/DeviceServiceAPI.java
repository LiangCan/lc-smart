package com.sykj.uusmart.hystric;

import com.sykj.uusmart.hystric.impl.DeviceServiceAPIHiHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "u-app",fallback = DeviceServiceAPIHiHystric.class)
public interface DeviceServiceAPI {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}