package com.springcloud.manager.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//开启Swagger2
public class SwaggerConfig {

    //是否启动swagger2
    private boolean enable = true;

    //配置了Swagger2的Docket的Bean实例
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enable)
                .select()
                .paths(PathSelectors.ant("com...."))
                .build().apiInfo(apiInfo());
    }

    //配置个人作者信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Manager's Swagger")
                .description("API接口文档说明")
                .termsOfServiceUrl("http://www.baidu.com")
                .contact(new Contact("xjs", "", "562683719@qq.com"))
                .version("1.0")
                .build();
    }

}
