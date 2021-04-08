package com.example.hos.interceptor;

import com.example.hos.repository.UserRepository;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.service.JwtService;
import com.example.hos.until.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author DBC-090
 * @date 2021/1/13
 * @description 登录认证拦截器
 */
@Component
public class AuthInterceptor implements AsyncHandlerInterceptor {

    @Value("${jwt.token.name}")
    private String jwtHeader;

    @Resource
    private JwtService jwtService;

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Authorization authorization = getAuthorizationAnnotation(method);
            if (Objects.nonNull(authorization)) {
                String token = request.getHeader(jwtHeader);
                if (StringUtils.isBlank(token)) {
                    token = request.getParameter(jwtHeader);
                }
                String account = jwtService.unSign(token);
                if (StringUtils.isNoneBlank(account)) {
                    if (Objects.nonNull(userRepository.findByUidAndStatus(account, Constant.STATUS))){
                        response.addHeader(jwtHeader, jwtService.sign(account));
                    } else {
                        failedResponse(response);
                        return false;
                    }
                } else {
                    failedResponse(response);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @description 获取注解
     * @param handlerMethod
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Authorization getAuthorizationAnnotation(HandlerMethod handlerMethod) {
        Authorization authorization = null;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(Authorization.class)) {
            authorization = method.getAnnotation(Authorization.class);
        } else {
            Class beanType = handlerMethod.getBean().getClass();
            if (beanType.isAnnotationPresent(Authorization.class)) {
                authorization = (Authorization) beanType.getAnnotation(Authorization.class);
            }
        }
        return authorization;
    }


    /**
     * 登录失败信息
     * @param response
     * @return
     */
    private void failedResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(Consts.UTF_8.name());
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        ObjectMapper om = new ObjectMapper();
        response.getWriter().write(om.writeValueAsString(ErrorInfo.REQUIRE_LOGIN.getMessage()));
    }
}
