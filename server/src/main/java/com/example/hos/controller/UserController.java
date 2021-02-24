package com.example.hos.controller;

import com.example.hos.mapper.TUserMapper;
import com.example.hos.model.TUser;
import com.example.hos.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/2/13
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG= LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private TUserMapper userMapper;

    @ApiOperation(value = "登录", produces = "application/json;charset=utf-8")
    @PostMapping("/login")
    public String login(String username, String password, HttpSession session) {
        TUser tUser=userMapper.selectByName(username);
        if (tUser==null||!password.equals(tUser.getPassword())){
            LOG.warn("用户登陆失败！用户名：{}，密码：{}",username,password);
            return "login";
        }
        session.setAttribute("user",tUser);
        LOG.debug("用户登陆成功！");
        return "index";
    }

    @ApiOperation(value = "登出", produces = "application/json;charset=utf-8")
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "index";
    }

    @ApiOperation(value = "注册", produces = "application/json;charset=utf-8")
    @PostMapping("/register")
    public String register(TUser user) {
        TUser tUser = userMapper.selectByName(user.getUsername());
        if (tUser!=null){
            LOG.warn("用户已存在");
            return "register";
        }else {
            userMapper.insertSelective(user);
        }
        return "index";
    }

}
