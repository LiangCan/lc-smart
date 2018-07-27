package com.sykj.uusmart.hystric.impl;


import com.sykj.uusmart.hystric.Demo2Client;
import com.sykj.uusmart.hystric.Test;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Demo2ClientHystric implements Demo2Client {


    @Override
    public List<Test> list() {
        System.out.println("进入断路器-list。。。");
        throw new RuntimeException("list 保存失败.");
    }

    @Override
    public int save() {
        System.out.println("进入断路器-save。。。");
        throw new RuntimeException("save 保存失败.");
    }
}