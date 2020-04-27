package com.springcloud.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwt {
    //jwt生成令牌
    public static void main(String[] args) {

        //一分钟过期时间
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666")
                .setSubject("xujisi")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "itcast")
                .setExpiration(new Date(new Date().getTime() + 60000))
                .claim("role", "admin");
        System.out.println("令牌生成，结果为:" + jwtBuilder.compact());
    }
}
