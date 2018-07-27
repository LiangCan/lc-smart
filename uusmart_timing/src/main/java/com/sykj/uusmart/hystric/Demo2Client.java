package com.sykj.uusmart.hystric;

import com.sykj.uusmart.hystric.impl.Demo2ClientHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "demo3",fallback = Demo2ClientHystric.class)
public interface Demo2Client {


    @RequestMapping("/demo/list")
    List<Test> list();


    @RequestMapping("/demo/save")
    int save();
}
