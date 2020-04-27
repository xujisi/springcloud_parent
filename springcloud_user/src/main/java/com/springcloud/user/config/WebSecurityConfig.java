package com.springcloud.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @version V1.0
 * @Title:安全配置类（现在暂时全部放行）之后用令牌来设置才可以访问
 * @ClassName: com.springcloud.user.config.WebSecurityConfig.java
 * @author: 许集思
 * @date: 2020/4/25 14:09
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //authorizeRequests 所有security全注解配置实现的开端，表示开始说明必要的权限
    //需要的权限分两部分，第一部分是拦截的路径，第二部分是访问该路径需要的权限
    //antMatchers 表示拦截。。 路径，permitAll标识任何路径通行
    //anyRequest 标识任何请求 authenticated标识认证后才能访问
    //.and().csrf().disable() 标识 csrf拦截失效。
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
