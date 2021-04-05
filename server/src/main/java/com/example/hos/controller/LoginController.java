package com.example.hos.controller;

import com.example.hos.model.vo.LoginRequest;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author changwei.zhong
 * @date create by 2021/3/23
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "登录", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @ApiOperation(value = "注册", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResultResponse register(@RequestBody UserVO userVO) {
        return userService.addUser(userVO);
    }

//    @ApiOperation(value = "登出", produces = "application/json;charset=utf-8")
//    @PostMapping("/logout")
//    public String logout(HttpSession session) {
//        session.removeAttribute("user");
//    }
}
