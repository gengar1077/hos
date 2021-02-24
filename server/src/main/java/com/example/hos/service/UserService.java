package com.example.hos.service;

import com.example.hos.model.TUser;
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
}
