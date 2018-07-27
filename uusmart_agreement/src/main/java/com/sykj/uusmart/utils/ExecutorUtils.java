package com.sykj.uusmart.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Liang on 2017/1/3.
 */
public class ExecutorUtils {
    /**
     * 可缓存,无限大线程池
     */
    public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * 定长线程池
     */
    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    /**
     * 定时执行线程池
     */
    public static ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

//    public static void main(String[] args) throws InterruptedException, TimeoutException, ExecutionException {
//        scheduledExecutor.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("--------------");
//            }
//        },3000,TimeUnit.MILLISECONDS);
//
//        Thread.sleep(4000);
//        scheduledExecutor.shutdownNow();
//    }


}
