package com.sykj.uusmart.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;  
public class Task3 {  
    public static void main(String[] args) {  
        Runnable runnable = new Runnable() {  
            public void run() {  
                // task to run goes here  
                System.out.println("Hello !!");  
            }  
        };  
        ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
        service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS);  
//        创建并执行在给定延迟后启用的 ScheduledFuture。

//        参数：

//        callable - 要执行的功能

//        delay - 从现在开始延迟执行的时间

//        unit - 延迟参数的时间单位

//        返回：      可用于提取结果或取消的 ScheduledFuture
service.schedule(runnable, 3000, TimeUnit.MILLISECONDS);
    }  
}  