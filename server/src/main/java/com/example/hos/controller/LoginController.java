package com.example.hos.controller;

import com.example.hos.model.vo.LoginRequest;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.JwtService;
import com.example.hos.service.UserService;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author changwei.zhong
 * @date create by 2021/3/23
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Value("${jwt.token.name}")
    private String jwtHeader;

    @Resource
    private UserService userService;

    @Resource
    private JwtService jwtService;


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


    @ApiOperation(value = "登出", produces = "application/json;charset=utf-8")
    @PostMapping("/logout")
    public ResultResponse logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        response.addHeader(jwtHeader, jwtService.sign(StringPool.EMPTY));
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(true);
        return resultResponse;
    }
}
