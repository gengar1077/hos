package com.example.hos.controller;

import com.example.hos.interceptor.Authorization;
import com.example.hos.interceptor.RoleAccess;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.UserService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/2/13
 */
//@Authorization
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;


    @ApiOperation(value = "用户信息修改")
    @PostMapping(value = "/update")
    public ResponseEntity<Void> updateUser(@RequestBody UserVO userVO){
        userService.updateUser(currentUser().getUid(), userVO);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "管理员修改个人信息")
    @PostMapping(value = "/updateByAdmin")
    @RoleAccess(roles = {Constant.ROLE_ADMIN})
    public ResponseEntity<Void> updateUserByAdmin(@RequestBody UserVO userVO){
        userService.updateUserByAdmin(userVO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "获取用户")
    @GetMapping(value = "/getUser")
    public ResponseEntity<UserVO> getUser(){
        return ResponseEntity.ok(userService.selectById(currentUser().getUid()));
    }

    @ApiOperation(value = "用户删除")
    @PostMapping(value = "/delete")
    @RoleAccess(roles = {Constant.ROLE_ADMIN})
    public ResponseEntity<Void> deleteUser(@RequestBody UserVO userVO) {
        userService.deleteUser(userVO.getId());
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "分页查询用户")
    @GetMapping(value = "/findByPage")
    @Authorization
    @RoleAccess(roles = {Constant.ROLE_ADMIN})
    public ResponseEntity<PageInfo<UserVO>> pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                                     @RequestParam(defaultValue = "6",required = false) Integer pageSize,
                                                     String name){
        return ResponseEntity.ok(userService.selectByPage(pageNum, pageSize, name));
    }

    @ApiOperation(value = "上传头像")
    @RequestMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> upload(MultipartFile image) throws IOException {
        userService.upload(currentUser().getUid(), image);
        return ResponseEntity.ok().build();
    }
}
