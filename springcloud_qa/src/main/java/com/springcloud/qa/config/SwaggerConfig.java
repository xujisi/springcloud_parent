package com.springcloud.qa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.springcloud.qa.controller"})
public class SwaggerConfig {

    private boolean enable = true;

    //配置了Swagger2的Docket的Bean实例
    @Bean
    public Docket docket() {
        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        //添加head参数end

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .enable(enable)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.springcloud.qa.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //配置个人作者信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Base's Swagger")
                .description("问答微服务接口文档说明")
                .termsOfServiceUrl("http://www.baidu.com")
                .contact(new Contact("xjs", "", "562683719@qq.com"))
                .version("1.0")
                .build();
    }
}
