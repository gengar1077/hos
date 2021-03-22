package com.example.hos.service.impl;

import com.example.hos.mapper.TUserMapper;
import com.example.hos.model.TUser;
import com.example.hos.model.TUserExample;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        userMapper.insertSelective(user);
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
        TUserExample tUserExample = new TUserExample();
        userMapper.updateByExample(user,tUserExample);
        return "修改成功！";
    }

    @Override
    public String deleteUser(String id) {
        TUser tUser = userMapper.selectById(id);
        tUser.setStatus("0");
        return "删除成功";
    }

    @Override
    public PageInfo<UserVO> selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        TUserExample tUserExample = new TUserExample();
        tUserExample.createCriteria().andStatusEqualTo("1");
        List<UserVO> users = userMapper.selectByExample(null).stream().map(user -> {
            UserVO userVO = new UserVO();
            userVO.setName(user.getUsername());
            userVO.setPhoto(user.getPhoto());
            return userVO;
        }).collect(Collectors.toList());
        return new PageInfo<>(users);
    }

    @Override
    public TUser selectById(String id) {
        return userMapper.selectById(id);
    }
}
