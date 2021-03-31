package com.example.hos.service.impl;

import com.example.hos.handle.HosException;
import com.example.hos.mapper.TPermissionMapper;
import com.example.hos.mapper.TUserMapper;
import com.example.hos.model.TPermission;
import com.example.hos.model.TUser;
import com.example.hos.model.TUserExample;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.LoginInfoVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.JwtService;
import com.example.hos.service.ResponseService;
import com.example.hos.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/2/13
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper userMapper;

    @Resource
    private ResponseService responseService;

    @Resource
    private JwtService jwtService;

    @Resource
    private TPermissionMapper permissionMapper;

    @Override
    public ResultResponse addUser(UserVO userVO) {
        ResultResponse resultResponse = new ResultResponse();
        Optional.ofNullable(userMapper.selectByName(userVO.getName())).orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        TUser user = new TUser();
        user.setUsername(userVO.getName());
        user.setPassword(userVO.getPassword());
        user.setStatus("1");
        userMapper.insertSelective(user);
        String message = responseService.message(ResultResponse.Code.SUCCESS);
        resultResponse.success(message);
        return resultResponse;
    }

    @Override
    public ResultResponse login(String username, String password) {
        ResultResponse resultResponse = new ResultResponse();
        TUser tUser = userMapper.selectByName(username)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        if (!password.equals(tUser.getPassword())){
            throw new HosException(ErrorInfo.PASSWORD_IS_FALSE.getMessage());
        }
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        loginInfoVO.setUsername(tUser.getUsername());
        loginInfoVO.setPhone(tUser.getPhone());
        String message = responseService.message(ResultResponse.Code.SUCCESS);
        String token = jwtService.unSign(tUser.getuId());
        loginInfoVO.setToken(token);
        resultResponse.success(message);
        resultResponse.setReturnData(loginInfoVO);
        System.out.println(loginInfoVO);
        return resultResponse;
    }

    @Override
    public ResultResponse updateUser(String uid, UserVO userVO) {
        ResultResponse resultResponse = new ResultResponse();
        TUser user = Optional.ofNullable(userMapper.selectByIdAndStatus(uid, "1"))
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        Optional.ofNullable(userMapper.selectByName(userVO.getName())).orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        if (StringUtils.isNotBlank(userVO.getName())){
            user.setUsername(userVO.getName());
        }
        if (StringUtils.isNotBlank(userVO.getPassword())){
            user.setPassword(userVO.getPassword());
        }
        if (StringUtils.isNotBlank(userVO.getPhone())){
            user.setPhone(userVO.getPhone());
        }
        TUserExample tUserExample = new TUserExample();
        userMapper.updateByExampleSelective(user, tUserExample);
        String message = responseService.message(ResultResponse.Code.SUCCESS);
        resultResponse.success(message);
        return resultResponse;
    }

    @Override
    public ResultResponse deleteUser(String id) {
        ResultResponse resultResponse = new ResultResponse();
        TUser user = Optional.ofNullable(userMapper.selectByIdAndStatus(id, "1"))
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        user.setStatus("0");
        TUserExample tUserExample = new TUserExample();
        userMapper.updateByExampleSelective(user, tUserExample);
        return resultResponse.success(responseService.message(ResultResponse.Code.SUCCESS));
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        List<UserVO> users = userMapper.selectAllAndStatus().stream().map(user -> {
            UserVO userVO = new UserVO();
            userVO.setName(user.getUsername());
            userVO.setPassword(user.getPassword());
            userVO.setPhone(user.getPhone());
            userVO.setRemark(user.getRemark());
            userVO.setId(user.getuId());
            return userVO;
        }).collect(Collectors.toList());
        PageInfo<UserVO> pageInfo = new PageInfo<>(users);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }

    @Override
    public TUser selectById(String id) {
        return Optional.ofNullable(userMapper.selectByIdAndStatus(id, "1"))
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
    }

    @Override
    public ResultResponse selectByName(String username) {
        TUser tUser = userMapper.selectByName(username)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        ResultResponse response = new ResultResponse();
        response.setReturnData(tUser);
        return response;
    }

    @Override
    public List<String> getRoles(String uid) {
        List<TPermission> permissions = permissionMapper.findAllByUserIdAndStatus(uid).orElse(Lists.newArrayList());
        return permissions.stream().map(TPermission::getBak1).collect(Collectors.toList());
    }
}
