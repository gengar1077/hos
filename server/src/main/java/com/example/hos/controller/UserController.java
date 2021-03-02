package com.example.hos.controller;

import com.example.hos.mapper.TUserMapper;
import com.example.hos.model.TUser;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/toLogin")
    public String toLogin(){
        return "user/login";
    }

    @GetMapping("/toRegister")
    public String toRegister(){
        return "user/register";
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "index";
    }

    @ApiOperation(value = "登录", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username, String password, HttpSession session) {
        TUser tUser=userMapper.selectByName(username).orElse(null);
        if (tUser==null||!password.equals(tUser.getPassword())){
            LOG.warn("用户登陆失败！用户名：{}，密码：{}",username,password);
            return "user/login";
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
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(TUser user) {
        TUser tUser = userMapper.selectByName(user.getUsername()).orElse(null);
        if (tUser!=null){
            LOG.warn("用户已存在");
            return "user/register";
        }else {
            userMapper.insertSelective(user);
        }
        return "user/login";
    }

    @ApiOperation(value = "用户信息修改")
    @PostMapping(value = "/update")
    public String updateUser(UserVO userVO){
        userService.updateUser(userVO);
        return "user/userList";
    }

    @ApiOperation(value = "用户删除")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "query",dataType = "long")
    @PostMapping(value = "/delete")
    public String deleteUser(Long id) {
        userService.deleteUser(id);
        return "user/userList";
    }

    @ApiOperation(value = "分页查询用户")
    @PostMapping(value = "/findByPage")
    public String pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                           @RequestParam(defaultValue = "6",required = false) Integer pageSize, ModelMap modelMap){
        PageInfo<TUser> userPage =userService.selectByPage(pageNum,pageSize);
        modelMap.put("userPage",userPage);

        return "user/userList";
    }
}
