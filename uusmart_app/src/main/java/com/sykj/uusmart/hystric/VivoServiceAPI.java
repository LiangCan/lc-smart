package com.sykj.uusmart.hystric;

import com.sykj.uusmart.http.vivo.bindDervice.BindDerviceReq;
import com.sykj.uusmart.hystric.impl.VivoServiceAPIHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "uusmart-vivo",fallback = VivoServiceAPIHystric.class)
public interface VivoServiceAPI {

    @RequestMapping(value = "device/bind",method = RequestMethod.POST)
    String bindDeviceForVivo(@RequestBody BindDerviceReq reqBaseDTO);
}
