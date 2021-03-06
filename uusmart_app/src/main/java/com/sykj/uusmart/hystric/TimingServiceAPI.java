package com.sykj.uusmart.hystric;

import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.hystric.impl.TimingServiceAPIHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "uusmart-timing",fallback = TimingServiceAPIHystric.class)
public interface TimingServiceAPI {

    @RequestMapping(value = "/auth/timing/user/by/device/delete.do",method = RequestMethod.POST)
    String byDeviceDeleteAllTiming(@RequestBody ReqBaseDTO reqBaseDTO);

    @RequestMapping(value = "/auth/timing/test.do",method = RequestMethod.POST)
    String test(@RequestBody ReqBaseDTO reqBaseDTO);
}
