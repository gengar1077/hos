package com.example.hos.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/2/13
 */
@Configuration
@EnableSwagger2 //开启在线文档
public class SwaggerConfig {

    @Value("${jwt.token.name}")
    private String jwtHeader;

    // 声明api 文档的属性 构建器
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("marrycode")
                .description("xxgc_hui")
                .termsOfServiceUrl("http://www.baidu.com")
                .contact("000")
                .version("1.0.0")
                .build();
    }

    // 核心配置信息
    @Bean
    public Docket createRestApi(){

        List<Parameter> pars = Lists.newArrayList();
        ParameterBuilder jwtTokenPar = new ParameterBuilder();
        jwtTokenPar.name(jwtHeader).description("JWT token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false);

        pars.add(jwtTokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hui.springboot.controller"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }
}
