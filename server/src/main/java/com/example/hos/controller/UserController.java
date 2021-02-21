package com.example.hos.controller;

import com.example.hos.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/2/13
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

}
