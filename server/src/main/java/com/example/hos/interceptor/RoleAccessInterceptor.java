package com.example.hos.interceptor;


import com.example.hos.model.type.ErrorInfo;
import com.example.hos.service.JwtService;
import com.example.hos.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 *@author DBC-090
 *@description 角色访问拦截器
 *@date 2020/9/29
 */
@Component
@Slf4j
public class RoleAccessInterceptor implements AsyncHandlerInterceptor {

    @Value("${jwt.token.name}")
    private String jwtHeader;

    @Resource
    private JwtService jwtService;

    @Resource
    private UserService userService;

    /**
     * @Description：在controller方法处理之前，进行角色信息验证
     * @param request
     * @param response
     * @param handler
     * @return：boolean
     * @Date：2020/11/19
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            RoleAccess roleAccess = getRoleAccessAnnotation(method);
            if (Objects.nonNull(roleAccess)) {
                String token = request.getHeader(jwtHeader);
                if (StringUtils.isEmpty(token)) {
                    token = request.getParameter(jwtHeader);
                }
                String account = jwtService.unSign(token);
                List<String> haveRoles = userService.getRoles(account);
                if (CollectionUtils.isEmpty(haveRoles) || !CollectionUtils.containsAny(haveRoles, roleAccess.roles())) {
                    log.info("账号{}不匹配角色{},访问{}失败", account, roleAccess.roles(), request.getRequestURI());
                    failedResponse(response);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @Description：获取方法/类上的RoleAccess注解
     * @param handlerMethod
     * @Date：2020/11/19
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private RoleAccess getRoleAccessAnnotation(HandlerMethod handlerMethod) {
        RoleAccess roleAccess = null;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(RoleAccess.class)) {
            roleAccess = method.getAnnotation(RoleAccess.class);
        } else {
            Class beanType = handlerMethod.getBean().getClass();
            if (beanType.isAnnotationPresent(RoleAccess.class)) {
                roleAccess = (RoleAccess) beanType.getAnnotation(RoleAccess.class);
            }
        }
        return roleAccess;
    }

    /**
     * 角色认证失败信息
     * @date 2020/9/24
     * @param
     * @return
     */
    private void failedResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        ObjectMapper om = new ObjectMapper();
        response.getWriter().write(om.writeValueAsString(ErrorInfo.ROLE_MISMATCHING.getMessage()));
    }
}

