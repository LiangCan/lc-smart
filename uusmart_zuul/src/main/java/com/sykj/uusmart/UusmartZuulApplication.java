package com.sykj.uusmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@EnableAutoConfiguration
@EnableHystrix
@EnableHystrixDashboard
public class UusmartZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(UusmartZuulApplication.class, args);
	}
}
