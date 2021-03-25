package com.example.hos.interceptor;

import com.example.hos.mapper.TUserMapper;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.service.JwtService;
import com.example.hos.service.ResponseService;
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
 * @author Wang Qingcheng
 * @date 2021/1/13
 * @description 登录认证拦截器
 */
@Component
public class AuthInterceptor implements AsyncHandlerInterceptor {

    private static final String STATUS = "1";

    @Value("${jwt.token.name}")
    private String jwtHeader;

    @Resource
    private JwtService jwtService;

    @Resource
    private TUserMapper userMapper;

    @Resource
    private ResponseService responseService;

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
                if (StringUtils.isNotBlank(account)) {
                    if (Objects.isNull(userMapper.selectByIdAndStatus(account, STATUS))){
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
     * @author Qingcheng Wang
     * @date 2021/1/13
     * @param handlerMethod
     * @return com.dragonsoft.sony.bookService.commons.annotation.Authorization
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
     * @param
     * @return
     * @author Qingcheng Wang
     * @date 2020/9/24
     */
    private void failedResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(Consts.UTF_8.name());
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        ResultResponse resultResponse = new ResultResponse();
        String message=responseService.message(ResultResponse.Code.REQUIRE_LOGIN);
        resultResponse.requireLogin(message);
        ObjectMapper om = new ObjectMapper();
        response.getWriter().write(om.writeValueAsString(resultResponse));
    }

//    private void failedResponse(HttpServletResponse response) throws IOException {
//        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
//        ObjectMapper om = new ObjectMapper();
//        Opr<Void> opr = new Opr<>();
//        opr.setCode(Message.LOGIN_AUTH_FAIL.getCode()).setResult(false).setHttpStatus(HttpStatus.UNAUTHORIZED.value()).setMsg(Message.LOGIN_AUTH_FAIL.getMsg());
//        response.getWriter().write(om.writeValueAsString(opr));
//    }
}
