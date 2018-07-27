package com.sykj.uusmart.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test  {

    public static void main(String[] args) {
        Map<Long, List<String>> map =  new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("123");
        map.put(1L,list);

        map.get(1L).add("456");

        System.out.println(map.get(1L).get(1));
    }



}