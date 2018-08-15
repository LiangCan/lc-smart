package com.sykj.uusmart.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


//}
//    @Bean
//    public Docket appApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("app")
//                .genericModelSubstitutes(DeferredResult.class)
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(false)
//                .pathMapping("/")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.sykj.uusmart"))
//                .paths(or(regex("/*.*/auth/*.*")))
//                .build()
//                .apiInfo(appApiInfo());
//    }
//
//    private ApiInfo appApiInfo() {
//        return new ApiInfoBuilder()
//                .title("UUSMART 2代 后台API")//大标题
//                .description("UUSMART 2代 后台API，所有应用程序都可以通过JSON访问Object模型数据。")//详细描述
//                .version("2.0")//版本
//                .termsOfServiceUrl("UUSmart服务条款")
//                .contact(new Contact("梁灿", "https://www.google.com.hk", "386740421@qq.com"))//作者
//                .license("The Apache License, Version 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//                .build();
//
////        return apiInfo;
//    }


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
        ApiInfo apiInfo = new ApiInfo("UUSMART 2代 APP服务API",
                "UUSMART 2代 后台API，所有应用程序都可以通过JSON访问Object模型数据。  \n" +
                        " 设备的接口API地址:   \n" +
                        " 'http://172.19.2.122:9003/u-device/swagger-ui.html'  \n" +
                        " 智能的接口API地址:   \n" +
                        " 'http://172.19.2.122:9003/u-wisdom/swagger-ui.html'  \n" +
                        " 请求详细说明请查看 ‘智能家居_协议说明文档_V2.4+’",
                "2.0.0",
                "UUSmart服务条款",
                "386740421@qq.com",
                "The Apache License, Version 2.0",
                "https://github.com/LiangCan/UUSmart");
        return apiInfo;
    }
}