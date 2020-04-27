package com.springcloud.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version V1.0
 * @Title:用户权限拦截器
 * @ClassName: com.springcloud.user.interceptor.JwtInterceptor.java
 * @author: 许集思
 * @date: 2020/4/25 17:43
 */


@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("经过了拦截器");
        //无论如何先放行，然后再操作中判断
        //拦截器只是负责把有请求头中包含token的令牌进行一个解析验证。
        String header = request.getHeader("Authorization");

        if (header != null && !"".equals(header)) {
            //如果包含有头信息。就对其进行解析
            if (header.startsWith("Bearer ")) {
                //得到令牌
                String token = header.substring(7);
                //对令牌进行验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    //如果是admin用户
                    if (roles != null && roles.equals("admin")) {
                        request.setAttribute("claims_admin", token);
                    }
                    //如果是user用户
                    if (roles != null && roles.equals("user")) {
                        request.setAttribute("claims_user", token);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确!");
                }
            }

        }
        return true;
    }


}
