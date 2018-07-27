package com.sykj.uusmart;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.sykj.uusmart.mqtt.CallBack;
import com.sykj.uusmart.mqtt.MQTTUtils;
import com.sykj.uusmart.utils.ConfigGetUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.UUID;


@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
@EnableHystrix
@EnableFeignClients
@Import(FdfsClientConfig.class)
//@EnableScheduling// 计划支持
public class UusmartAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(UusmartAppApplication.class, args);
		MQTTUtils.init(ConfigGetUtils.serviceConfig.getMQTT_URL(),
			ConfigGetUtils.serviceConfig.getMQTT_CLIENT_NAME(), new CallBack(), ConfigGetUtils.serviceConfig.getSUB_MQTT_TOPIC());
	}
}
