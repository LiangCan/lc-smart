package com.mqtt.test;

public class HandeMqIotMsgTimeOut implements Runnable {
    private int i;

    public HandeMqIotMsgTimeOut(int i) {
        this.i = i;
    }

    public void run() {
        System.out.println(" ----- 执行 " + i );
    }
}


    