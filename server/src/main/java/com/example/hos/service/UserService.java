package com.example.hos.service;

import com.example.hos.model.vo.LoginInfoVO;
import com.example.hos.model.vo.UserVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/2/13
 */
public interface UserService {
    /**
     * 注册
     * @author 吃面龙
     * @date 2021/2/13
     * @param userVO
     * @return
     */
    void addUser(UserVO userVO);

    /**
     * 登录
     * @author 吃面龙
     * @date 2021/2/13
     * @param username
     * @param password
     * @return
     */
    LoginInfoVO login(String username, String password);

    /**
     * 更新用户
     * @date: 2021/1/7
     * @param userVo
     * @return
     **/
    void updateUser(String uid, UserVO userVo);

    /**
     * 管理员修改用户
     * @date: 2021/1/7
     * @param userVo
     * @return
     **/
    void updateUserByAdmin(UserVO userVo);

    /**
     * 删除用户
     * @author changwei.zhong
     * @date 2021/2/24
     * @param id
     * @return
     **/
    void deleteUser(String id);

    /**
     * 分页查询
     * @author changwei.zhong
     * @date 2021/2/24
     * @param pageNum
     * @param pageSize
     * @return
     **/
    PageInfo<UserVO> selectByPage(Integer pageNum, Integer pageSize, String name);

    /**
     * 根据id查询用户
     * @author changwei.zhong
     * @date 2021/2/24
     * @param id
     * @return
     **/
    UserVO selectById(String id);

    /**
     * 根据用户获取角色
     * @param uid
     * @return a
     * @author changwei.zhong
     * @date 2021/3/31
     **/
    List<String> getRoles(String uid);

    /**
     * 上传头像
     * @param uid
     * @param image
     * @return a
     * @author changwei.zhong
     * @date 2021/3/31
     **/
    void upload(String uid, MultipartFile image) throws IOException;
}
