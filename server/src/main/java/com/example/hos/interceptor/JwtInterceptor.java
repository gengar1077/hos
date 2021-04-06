package com.example.hos.interceptor;

import com.example.hos.dao.repository.UserRepository;
import com.example.hos.service.JwtService;
import com.example.hos.until.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author changwei.zhong
 * @date create by 2021/4/6
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.token.name}")
    private String jwtHeader;

    @Resource
    private JwtService jwtService;

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader(jwtHeader);
        // 如果不是映射到方法直接通过
        if(object instanceof HandlerMethod){
            HandlerMethod handlerMethod=(HandlerMethod)object;
            Method method = handlerMethod.getMethod();
            //检查有没有需要用户权限的注解
            if (method.isAnnotationPresent(Authorization.class)) {
                Authorization authorization = method.getAnnotation(Authorization.class);
                if (Objects.nonNull(authorization)) {
                    // 执行认证
                    if (token == null) {
                        throw new RuntimeException("无token，请登录");
                    }
                    // 获取 token 中的 userId
                    String account = jwtService.sign(token);
                    if (StringUtils.isNoneBlank(account)) {
                        if (Objects.isNull(userRepository.findByUidAndStatus(account, Constant.STATUS))){
                            httpServletResponse.addHeader(jwtHeader, jwtService.sign(account));
                        } else {
                            throw new RuntimeException("token错误");
                        }
                    }
                }
            }
            throw new RuntimeException("无token，请登录");
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
