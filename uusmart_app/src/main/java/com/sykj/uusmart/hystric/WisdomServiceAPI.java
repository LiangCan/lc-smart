package com.sykj.uusmart.hystric;

import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.hystric.impl.WisdomServiceAPIHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "uusmart-wisdom",fallback = WisdomServiceAPIHystric.class)
public interface WisdomServiceAPI {

///auth/wisdom/user/delete/device.do

    @RequestMapping(value = "/auth/wisdom/user/delete/device.do",method = RequestMethod.POST)
    String byDeviceDeleteAllWisdom(@RequestBody ReqBaseDTO reqBaseDTO);
}
