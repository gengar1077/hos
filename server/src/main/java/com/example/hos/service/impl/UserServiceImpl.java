package com.example.hos.service.impl;

import com.example.hos.mapper.TUserMapper;
import com.example.hos.model.TUser;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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
    public String addUser(TUser user) {
        int i = userMapper.insertSelective(user);
        return null;
    }

    @Override
    public String updateUser(UserVO userVo) {
        TUser user = null;
        if (StringUtils.isNotBlank(userVo.getName())) {
            user = userMapper.selectByName(userVo.getName()).orElse(null);
        }
        if (Objects.isNull(user)){
            return "该用户不存在";
        }
        if (!user.getUsername().equals(userVo.getName())){
            if (userMapper.selectByName(userVo.getName()).isPresent()){
                return "该用户名已经存在，修改失败！";
            }
        }
        user.setUsername(userVo.getName());
        user.setPassword(userVo.getPassword());
        user.setPhone(userVo.getPhoto());
        return "修改成功！";
    }
}
