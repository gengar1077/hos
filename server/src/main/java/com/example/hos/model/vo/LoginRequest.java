package com.example.hos.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/4/4
 */
@Data
@ApiModel(description = "登录请求")
public class LoginRequest {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;
}
