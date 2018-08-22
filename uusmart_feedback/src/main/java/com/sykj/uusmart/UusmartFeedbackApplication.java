package com.sykj.uusmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableHystrix
@EnableFeignClients
//@Import(FdfsClientConfig.class)
//@EnableScheduling// 计划支持
public class UusmartFeedbackApplication {


	public static void main(String[] args) {
		SpringApplication.run(UusmartFeedbackApplication.class, args);
//		MQTTUtils.init(ConfigGetUtils.serviceConfig.getMQTT_URL(),
//			ConfigGetUtils.serviceConfig.getMQTT_CLIENT_NAME(), new CallBack(), ConfigGetUtils.serviceConfig.getSUB_MQTT_TOPIC());
	}
}
