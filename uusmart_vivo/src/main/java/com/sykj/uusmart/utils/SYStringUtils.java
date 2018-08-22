package com.sykj.uusmart.utils;

import java.util.UUID;

public class SYStringUtils {

    public static String getUUIDExistSymbol(){
        return   UUID.randomUUID().toString();
    }

    public static String getUUIDNotExistSymbol(){
        return  UUID.randomUUID().toString().replace("-", "");
    }
}
