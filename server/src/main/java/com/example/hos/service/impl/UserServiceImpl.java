package com.example.hos.service.impl;

import com.example.hos.dao.repository.PermissionRepository;
import com.example.hos.dao.repository.RoleRepository;
import com.example.hos.dao.repository.UserRepository;
import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Permission;
import com.example.hos.model.entity.User;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.LoginInfoVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.UserVO;
import com.example.hos.service.JwtService;
import com.example.hos.service.UserService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private JwtService jwtService;

    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private RoleRepository roleRepository;

    @Override
    public ResultResponse addUser(UserVO userVO) {
        ResultResponse resultResponse = new ResultResponse();
        Optional<User> username = userRepository.findByUsername(userVO.getName());
        if (username.isPresent()){
            throw new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage());
        }
        User user = new User();
        if (StringUtils.isNotBlank(userVO.getName())){
            user.setUsername(userVO.getName());
        }
        if (StringUtils.isNotBlank(userVO.getPassword())){
            user.setPassword(userVO.getPassword());
        }
        user.setStatus(Constant.STATUS);
        userRepository.saveAndFlush(user);
        return resultResponse;
    }

    @Override
    public ResultResponse login(String username, String password) {
        ResultResponse resultResponse = new ResultResponse();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        if (!password.equals(user.getPassword())){
            throw new HosException(ErrorInfo.PASSWORD_IS_FALSE.getMessage());
        }
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        loginInfoVO.setUsername(user.getUsername());
        loginInfoVO.setPassword(user.getPassword());
        loginInfoVO.setPhone(user.getPhone());
        if (user.getRoleId() == ""){
            loginInfoVO.setIsAdmin(true);
        }
        String token = jwtService.sign(user.getUid());
        loginInfoVO.setToken(token);
        resultResponse.setReturnData(loginInfoVO);
        return resultResponse;
    }

    @Override
    public ResultResponse updateUser(String uid, UserVO userVO) {
        ResultResponse resultResponse = new ResultResponse();
        User user = userRepository.findByUidAndStatus(uid, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        if (StringUtils.isNotBlank(userVO.getName())){
            user.setUsername(userVO.getName());
        }
        if (StringUtils.isNotBlank(userVO.getPassword())){
            user.setPassword(userVO.getPassword());
        }
        if (StringUtils.isNotBlank(userVO.getPhone())){
            user.setPhone(userVO.getPhone());
        }
        return resultResponse;
    }

    @Override
    public ResultResponse updateUserByAdmin(UserVO userVO) {
        ResultResponse resultResponse = new ResultResponse();
        User user = userRepository.findByUsername(userVO.getName())
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        if (StringUtils.isNotBlank(userVO.getRemark())){
            user.setRemark(userVO.getRemark());
        }
        if (StringUtils.isNotBlank(userVO.getPassword())){
            user.setPassword(userVO.getPassword());
        }
        if (StringUtils.isNotBlank(userVO.getPhone())){
            user.setPhone(userVO.getPhone());
        }
        if (StringUtils.isNotBlank(userVO.getRoleName())){
            roleRepository.findRoleByRname(userVO.getRoleName()).ifPresent(role -> {
                user.setRoleId(role.getRid());
            });
        }
        userRepository.saveAndFlush(user);
        return resultResponse;
    }

    @Override
    public ResultResponse deleteUser(String id) {
        ResultResponse resultResponse = new ResultResponse();
        User user = userRepository.findByUidAndStatus(id, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        user.setStatus("0");
        userRepository.saveAndFlush(user);
        return resultResponse;
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        List<UserVO> users = userRepository.findByStatus(Constant.STATUS).stream().map(user -> {
            UserVO userVO = new UserVO();
            userVO.setName(user.getUsername());
            userVO.setPassword(user.getPassword());
            userVO.setPhone(user.getPhone());
            userVO.setRemark(user.getRemark());
            userVO.setId(user.getUid());
            return userVO;
        }).collect(Collectors.toList());
        PageInfo<UserVO> pageInfo = new PageInfo<>(users);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }

    @Override
    public User selectById(String id) {
        return userRepository.findByUidAndStatus(id, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
    }

    @Override
    public ResultResponse selectByName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        ResultResponse response = new ResultResponse();
        response.setReturnData(user);
        return response;
    }

    @Override
    public List<String> getRoles(String uid) {
        List<Permission> permissions = permissionRepository.findAllByUidAndStatus(uid, Constant.STATUS).orElse(Lists.newArrayList());
        return permissions.stream().map(Permission::getRname).collect(Collectors.toList());
    }
}
