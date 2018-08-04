package com.sykj.uusmart.mqtt;

import com.google.gson.Gson;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.mqtt.push.impl.MqIotMsgCallBackImpl;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.UserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
public class Test {
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(128L);
        Long uid = 128L;
//        DeviceInfo deviceInfo = new DeviceInfo();
//        deviceInfo.setDeviceId(1L);
        if(userInfo.getUserId() == uid){
            System.out.println(" true ");
        }else{
            System.out.println(" false ");
        }
//        Long i = 12L;
//        Long j = 12L;
//        Long m = 195L;
//        Long n = 195L;
//        System.out.println(i==j);//true
//        System.out.println(m==n);//false
//        System.out.println(i.equals(j));//true
//        System.out.println(m.equals(n));//true

    }
}
