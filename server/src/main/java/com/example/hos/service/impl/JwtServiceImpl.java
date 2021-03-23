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
 * @author heweiwei
 * @date 2018/2/5
 */
@Service
public class JwtServiceImpl implements JwtService {

    //    @Value(("${jwt.secret.key}"))
    //    private String jwtKey;

    private static final long EXPIRE_TIME = 30 * 60 * 1000;

    private static final String JWT_KEY = "testsecret";

    /**
     * @title 生成token
     * @discribtion
     * @author zch
     * @Date 2020年1月7日 下午4:10:32
     * @vision V1.0
     */
    @Override
    public String unSign(String userId)
    {
        try
        {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
            // 设置头部信息
            Map<String, Object> header = Maps.newHashMap();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            String token = JWT.create()
                    // header
                    .withHeader(header)
                    .withClaim("id", userId)
                    // 过期时间
                    .withExpiresAt(date)
                    // 签名
                    .sign(algorithm);
            return token;
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @title 校验token
     * @discribtion
     * @author zch
     * @Date 2020年1月7日 下午4:10:51
     * @return 0 校验通过, 1 token已过期, 2 校验不通过
     * @vision V1.0
     */
    public static int verify(String token)
    {
        int flag;
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
            JWT.require(algorithm).build().verify(token);
            // 校验通过
            flag = 0;
        }
        catch (TokenExpiredException e)
        {
            e.printStackTrace();
            // token过期
            flag = 1;
        }
        catch (JWTVerificationException e)
        {
            e.printStackTrace();
            // 校验失败
            flag = 2;
        }
        return flag;
    }

    /**
     * @title 获取token中的用户id
     * @discribtion
     * @author zch
     * @Date 2020年1月7日 下午4:11:52
     * @vision V1.0
     */
    @Override
    public String sign(String token)
    {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userId").asString();
    }
}
