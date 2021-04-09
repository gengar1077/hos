package com.example.hos.controller;

import com.example.hos.interceptor.Authorization;
import com.example.hos.interceptor.RoleAccess;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.UserService;
import com.example.hos.until.Constant;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.*;
import java.io.IOException;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/2/13
 */
@Authorization
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;


    @ApiOperation(value = "用户信息修改")
    @PostMapping(value = "/update")
    public ResultResponse updateUser(@RequestBody UserVO userVO){
        return userService.updateUser(currentUser().getUid(), userVO);
    }


    @ApiOperation(value = "管理员修改个人信息")
    @PostMapping(value = "/updateByAdmin")
    @RoleAccess(roles = {Constant.ROLE_ADMIN})
    public ResultResponse updateUserByAdmin(@RequestBody UserVO userVO){
        return userService.updateUserByAdmin(userVO);
    }


    @ApiOperation(value = "用户删除")
    @PostMapping(value = "/delete")
    @RoleAccess(roles = {Constant.ROLE_ADMIN})
    public ResultResponse deleteUser(@RequestBody UserVO userVO) {
        return userService.deleteUser(userVO.getId());
    }


    @ApiOperation(value = "分页查询用户")
    @GetMapping(value = "/findByPage")
    @Authorization
    @RoleAccess(roles = {Constant.ROLE_ADMIN})
    public ResultResponse pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                   @RequestParam(defaultValue = "6",required = false) Integer pageSize,
                                   String name){
        return userService.selectByPage(pageNum, pageSize, name);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="photo",value = "头像", required = true, dataTypeClass = MultipartFile.class, paramType = "form")
    })
    @RequestMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultResponse upload(MultipartFile image) {
        return userService.upload(currentUser().getUid(), image);
    }
}
