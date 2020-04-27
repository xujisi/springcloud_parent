package com.springcloud.qa.config;

import com.springcloud.qa.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * @version V1.0
 * @Title:登录拦截器
 * @ClassName: com.springcloud.user.config.interceptorConfig.java
 * @author: 许集思
 * @date: 2020/4/25 17:46
 */

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * @Title:非login相关的不能登录
     * @MethodName: addInterceptors
     * @Param: * @param registry
     * @Return void
     * @Exception
     * @author: 许集思
     * @date: 2020/4/25 17:56
     */
    protected void addInterceptors(InterceptorRegistry registry) {

        //注册拦截器要声明拦截器对象和要拦截的请求
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login/**");
    }
}
