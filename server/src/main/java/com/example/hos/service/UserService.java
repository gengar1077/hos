package com.example.hos.service;

import com.example.hos.model.TUser;
import com.example.hos.model.vo.UserVO;
import com.github.pagehelper.PageInfo;

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
     * @param user
     * @return
     */
    String addUser(TUser user);

    /**
     * 更新用户
     * @date: 2021/1/7
     * @param userVo
     * @return
     **/
    String updateUser(UserVO userVo);

    /**
     * 删除用户
     * @author changwei.zhong
     * @date 2021/2/24
     * @param id
     * @return
     **/
    String deleteUser(Long id);

    /**
     * 分页查询
     * @author changwei.zhong
     * @date 2021/2/24
     * @param pageNum
     * @param pageSize
     * @return
     **/
    PageInfo<UserVO> selectByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据id查询用户
     * @author changwei.zhong
     * @date 2021/2/24
     * @param id
     * @return
     **/
    TUser selectById(Long id);
}
