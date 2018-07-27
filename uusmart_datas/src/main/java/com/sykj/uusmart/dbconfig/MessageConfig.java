package com.sykj.uusmart.dbconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class MessageConfig {
    public static Map<String, String> map = new HashMap<String, String>();
    static{
        map.put("zh", "CN");
        map.put("en", "US");
    }

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource(){
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("message");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}
