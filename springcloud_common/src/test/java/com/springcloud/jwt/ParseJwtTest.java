package com.springcloud.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwtTest {

    public static void main(String[] args) {
        Claims claims = Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJ4dWppc2kiLCJpYXQiOjE1ODc4MDQyMjgsImV4cCI6MTU4NzgwNDI4OCwicm9sZSI6ImFkbWluIn0.xhCTpeFATfLpr1CVjc8dFhmr-Z4G3WFD_QPmTR-spxg")
                .getBody();

        System.out.println("用户ID：" + claims.getId());
        System.out.println("用户名：" + claims.getSubject());
        System.out.println("登录时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户角色：" + claims.get("role"));

    }

}
