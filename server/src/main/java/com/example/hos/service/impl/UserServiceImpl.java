package com.example.hos.service.impl;

import com.example.hos.mapper.TUserMapper;
import com.example.hos.model.TUser;
import com.example.hos.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/2/13
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper userMapper;

    @Override
    public boolean addUser(TUser user) {
        int i = userMapper.insertSelective(user);
        return true;
    }
}
