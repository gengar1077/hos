package com.example.hos.service.impl;

import com.example.hos.handle.HosException;
import com.example.hos.mapper.TUserMapper;
import com.example.hos.model.TUser;
import com.example.hos.model.TUserExample;
import com.example.hos.model.vo.LoginInfoVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.JwtService;
import com.example.hos.service.ResponseService;
import com.example.hos.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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

    @Resource
    private ResponseService responseService;

    @Resource
    private JwtService jwtService;

    @Override
    public ResultResponse addUser(UserVO userVO) {
        ResultResponse resultResponse = new ResultResponse();
        TUser tUser = userMapper.selectByName(userVO.getName()).orElse(null);
        if (tUser!=null){
            throw new HosException("ACCOUNT_IS_BLOCK");
        }
        TUser tUser1 = new TUser();
        BeanUtils.copyProperties(tUser1, userVO);
        userMapper.insertSelective(tUser1);
        String message = responseService.message(ResultResponse.Code.SUCCESS);
        resultResponse.success(message);
        return resultResponse;
    }

    @Override
    public ResultResponse login(String username, String password) {
        ResultResponse resultResponse = new ResultResponse();
        TUser tUser=userMapper.selectByName(username).orElse(null);
        if (tUser==null){
            throw new HosException("ACCOUNT_NOT_FOUND");
        }
        if (!password.equals(tUser.getPassword())){
            throw new HosException("ACCOUNT_PASS_ERROR");
        }
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        BeanUtils.copyProperties(loginInfoVO, tUser);
        String message = responseService.message(ResultResponse.Code.SUCCESS);
        resultResponse.success(message);
        resultResponse.setReturnData(loginInfoVO);
        String token = jwtService.unSign(tUser.getuId());
        loginInfoVO.setToken(token);
        return resultResponse;
    }

    @Override
    public ResultResponse updateUser(UserVO userVo) {
        ResultResponse resultResponse = new ResultResponse();
        TUser user = null;
        if (StringUtils.isNotBlank(userVo.getName())) {
            user = userMapper.selectByName(userVo.getName()).orElse(null);
        }
        if (Objects.isNull(user)){
            throw new HosException("ACCOUNT_NOT_FOUND");
        }
        if (!user.getUsername().equals(userVo.getName())){
            if (userMapper.selectByName(userVo.getName()).isPresent()){
                throw new HosException("ACCOUNT_IS_BLOCK");
            }
        }
        user.setUsername(userVo.getName());
        user.setPassword(userVo.getPassword());
        user.setPhone(userVo.getPhoto());
        TUserExample tUserExample = new TUserExample();
        userMapper.updateByExample(user,tUserExample);
        String message = responseService.message(ResultResponse.Code.SUCCESS);
        resultResponse.success(message);
        return resultResponse;
    }

    @Override
    public ResultResponse deleteUser(String id) {
        ResultResponse resultResponse = new ResultResponse();
        TUser tUser = userMapper.selectById(id, "1");
        tUser.setStatus("0");
        return resultResponse.success(responseService.message(ResultResponse.Code.SUCCESS));
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
        return userMapper.selectById(id, "1");
    }
}
