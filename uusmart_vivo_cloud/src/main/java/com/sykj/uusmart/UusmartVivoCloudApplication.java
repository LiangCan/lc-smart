package com.sykj.uusmart;

import com.sykj.uusmart.mqtt.MQTTUtils;
import com.sykj.uusmart.utils.ConfigGetUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
//@EnableAutoConfiguration
@EnableTransactionManagement
@EnableHystrix
@EnableFeignClients
public class UusmartVivoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(UusmartVivoCloudApplication.class, args);
		MQTTUtils.init(ConfigGetUtils.serviceConfig.getMQTT_URL(),
				ConfigGetUtils.serviceConfig.getMQTT_CLIENT_NAME(),
				ConfigGetUtils.callBack, ConfigGetUtils.serviceConfig.getSUB_MQTT_TOPIC());
	}
}
