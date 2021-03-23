package com.example.hos.service;

/**
 *@description JWT 加密解密
 *@author
 *@date
 */
public interface JwtService {

    /**
     * 加密
     * @date 2021/3/23
     * @param userId
     * @return
     */
    String sign(String userId);

    /**
     * 解密
     * @date 2021/3/23
     * @param token
     * @return
     */
    String unSign(String token);
}
