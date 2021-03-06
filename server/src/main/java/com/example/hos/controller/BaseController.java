package com.example.hos.controller;


import com.example.hos.repository.UserRepository;
import com.example.hos.model.entity.User;
import com.example.hos.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author DBC-090
 * @date 2021/1/12
 * @description Base Controller
 */
public abstract class BaseController {

    @Value("${jwt.token.name}")
    private String jwtHeader;

    @Resource
    private JwtService jwtService;

    @Resource
    private UserRepository userRepository;

    /**
     * @description 获取当前用户
     * @date 2021/1/14
     * @param
     * @return java.lang.String
     */
    public User currentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader(jwtHeader);
        int i = jwtService.checkToken(token);
        if(i == 0){
            String uid = jwtService.unSign(token);
            return userRepository.findById(uid).orElse(null);
        }else {
            return null;
        }
    }
}
