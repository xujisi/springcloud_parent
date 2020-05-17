package com.springcloud.manager.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfig {

    //配置了Swagger2的Docket的Bean实例
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //enable：是否启动Swagger
                .enable(false)
                .select()
                //RequestHandlerSelectors 配置要扫描接口的方式
                //basePackage:指定要扫描的包
                //any():扫描全部
                //none();不扫描
                //withClassAnnotation:扫描了类上的注解
                //withMethodAnnotation:扫描了方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com....."))
                //paths:过滤什么路径
                .paths(PathSelectors.ant("com...."))
                .build();
    }

    //配置Swagger信息=apiinfi
    private ApiInfo apiInfo() {

        //作者信息
        Contact contact = new Contact("许集思", "www.baidu.com", "562683719@qq.com");

        return new ApiInfo(
                "Xujisi的Swagger",
                "大家好，我是你们的父亲",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

}
