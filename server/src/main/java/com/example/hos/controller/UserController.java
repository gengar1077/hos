package com.example.hos.controller;

import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public ResultResponse updateUser(UserVO userVO){
        return userService.updateUser(currentUser().getUid(), userVO);
    }

    @ApiOperation(value = "管理员修改个人信息")
    @PostMapping(value = "/updateByAdmin")
    public ResultResponse updateUserByAdmin(@RequestBody UserVO userVO){
        return userService.updateUserByAdmin(userVO);
    }

    @ApiOperation(value = "用户删除")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "string")
    @PostMapping(value = "/delete")
//    @RoleAccess(roles = {Constant.ROLE_ADMIN})
    public ResultResponse deleteUser(@RequestBody UserVO userVO) {
        return userService.deleteUser(userVO.getId());
    }

    @ApiOperation(value = "分页查询用户")
    @GetMapping(value = "/findByPage")
    public ResultResponse pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                   @RequestParam(defaultValue = "6",required = false) Integer pageSize){
        return userService.selectByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "根据名字查询用户")
    @GetMapping("/findByUsername")
    public ResultResponse photo(String username) {
        return userService.selectByName(username);
    }

//    @ApiOperation(value = "修改头像")
//    @GetMapping("/photo")
//    public String photo(String id, ModelMap modelMap){
//        final User tuser = userService.selectById(id);
//        modelMap.put("user", tuser);
//        return "/user/userInfo";
//    }
//
//    @PostMapping("/upload")
//    public String upload(MultipartFile photo, String id, HttpSession session) throws IOException {
//        if (photo==null||photo.isEmpty()){
//            return "/user/userInfo";
//        }
//        //获取到文件名
//        final String filename = photo.getOriginalFilename();
//
//        final String uuid = UUID.randomUUID().toString().replace("-", "");
//        final String sub = filename.substring(filename.lastIndexOf("."));
//
//        //创建一个File对象
//        final File file = new File("D://pic//user/photo/" + id + "//" + uuid + sub);
//        if (!file.getParentFile().exists()){
//            file.getParentFile().mkdirs();//创建父目录
//        }
//        photo.transferTo(file);
//        //保存文件数据，会将上传的文件数据写入指定的File对象
//        //关联到用户，将地址保存到数据库
//        UserVO user = new UserVO();
//        user.setId(id);
//        //设置用户信息中的头像信息
//        user.setPhoto("/pic/user/photo/"+id+"/"+uuid+sub);
//        userService.updateUser(id, user);
//        session.setAttribute("user",userService.selectById(user.getId()));
//        return "user/userInfo";
//    }
}
