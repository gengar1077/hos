package com.example.hos.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.hos.service.JwtService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author DBC-090
 */
@Service
public class JwtServiceImpl implements JwtService {

    private static final long EXPIRE_TIME = 360 * 60 * 1000;

    private static final String JWT_KEY = "testSecret";

    /**
     * @title 生成token
     */
    @Override
    public String sign(String userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
            // 设置头部信息
            Map<String, Object> header = Maps.newHashMap();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            return JWT.create()
                    // header
                    .withHeader(header)
                    .withClaim("uid", userId)
                    // 过期时间
                    .withExpiresAt(date)
                    // 签名
                    .sign(algorithm);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @title 校验token
     */
    @Override
    public int checkToken(String token) {
        int flag;
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
            JWT.require(algorithm).build().verify(token);
            // 校验通过
            flag = 0;
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            // token过期
            flag = 1;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            // 校验失败
            flag = 2;
        }
        return flag;
    }

    /**
     * @title 获取token中的用户id
     */
    @Override
    public String unSign(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("uid").asString();
    }
}
