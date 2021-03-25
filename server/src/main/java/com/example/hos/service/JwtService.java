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

    /**
     * @title 校验token
     * @author zch
     * @Date 2020年1月7日 下午4:10:51
     * @return 0 校验通过, 1 token已过期, 2 校验不通过
     * @vision V1.0
     */
    int checkToken(String token);
}
