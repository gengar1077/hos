package com.example.hos.controller;


import com.example.hos.log.LogFactory;
import com.example.hos.mapper.TUserMapper;
import com.example.hos.model.TUser;
import com.example.hos.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author Wang Qingcheng
 * @date 2021/1/12
 * @description Base Controller
 */
public abstract class BaseController {

    @Value("${jwt.token.name}")
    private String jwtHeader;

    @Resource
    private JwtService jwtService;

    @Resource
    private TUserMapper userMapper;

    /**
     * @description 获取当前用户
     * @author Qingcheng Wang
     * @date 2021/1/14
     * @param
     * @return java.lang.String
     */
    public TUser currentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader(jwtHeader);
        LogFactory.getDebugLog().debug("token:{}", token);
        int i = jwtService.checkToken(token);
        if(i == 0){
            String uid = jwtService.sign(token);
            return userMapper.selectByPrimaryKey(uid);
        }else {
            return null;
        }
    }
}
