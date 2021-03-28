package com.example.hos.service;

import com.example.hos.model.TUser;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.UserVO;

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
    ResultResponse addUser(UserVO userVO);

    /**
     * 登录
     * @author 吃面龙
     * @date 2021/2/13
     * @param username
     * @param password
     * @return
     */
    ResultResponse login(String username, String password);

    /**
     * 更新用户
     * @date: 2021/1/7
     * @param userVo
     * @return
     **/
    ResultResponse updateUser(String uid, UserVO userVo);

    /**
     * 删除用户
     * @author changwei.zhong
     * @date 2021/2/24
     * @param id
     * @return
     **/
    ResultResponse deleteUser(String id);

    /**
     * 分页查询
     * @author changwei.zhong
     * @date 2021/2/24
     * @param pageNum
     * @param pageSize
     * @return
     **/
    ResultResponse selectByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据id查询用户
     * @author changwei.zhong
     * @date 2021/2/24
     * @param id
     * @return
     **/
    TUser selectById(String id);
}
