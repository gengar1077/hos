package com.example.hos.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: KEANU
 * @date: 2021/1/29
 **/
@Data
public class LoginInfoVO{

    private static final long serialVersionUID = 247616217919830202L;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户token")
    private String token;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "是否登陆过")
    private Boolean isLogged;

    @ApiModelProperty(value = "是否是管理员")
    private Boolean isAdmin;

}