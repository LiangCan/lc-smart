package com.sykj.uusmart.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {



    @Bean
    public Docket ProductApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sykj.uusmart"))
                .build()
                .apiInfo(productApiInfo());
    }

    private ApiInfo productApiInfo() {
        ApiInfo apiInfo = new ApiInfo("UUSMART 2代 智能服务API",
                "UUSMART 2代 后台API，所有应用程序都可以通过JSON访问Object模型数据。  \n" +
                        " 首页的接口API地址:    \n" +
                        " 'http://172.19.2.122:9003/u-app/swagger-ui.html'  \n" +
                        " 请求详细说明请查看 ‘智能家居_协议说明文档_V2.4+’",
                "2.0.0",
                "UUSmart服务条款",
                "386740421@qq.com",
                "The Apache License, Version 2.0",
                "https://github.com/LiangCan/UUSmart");
        return apiInfo;
    }
}