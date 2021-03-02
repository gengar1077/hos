package com.example.hos.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器类
        registry.addInterceptor(new LoginInterceptor())
                //指定拦截的请求
                .addPathPatterns("/**")
                //指定放行的请求
                .excludePathPatterns("/user/toLogin","/user/login","/js/**","/user/sendCode");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //指定请求地址
        registry.addResourceHandler("/pic/**")
                //指定资源路径
                .addResourceLocations("file:E:/pic/");
    }
}
