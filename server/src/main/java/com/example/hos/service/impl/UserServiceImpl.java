package com.example.hos.service.impl;

import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Permission;
import com.example.hos.model.entity.Role;
import com.example.hos.model.entity.User;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.LoginInfoVO;
import com.example.hos.model.vo.UserVO;
import com.example.hos.repository.PermissionRepository;
import com.example.hos.repository.RoleRepository;
import com.example.hos.repository.UserRepository;
import com.example.hos.service.JwtService;
import com.example.hos.service.UserService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import jodd.util.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    public void addUser(UserVO userVO) {
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
        user.setRoleId(Constant.USER);
        user.setStatus(Constant.STATUS);
        userRepository.saveAndFlush(user);
        Permission permission = new Permission();
        permission.setUid(user.getUid());
        permission.setUsername(user.getUsername());
        permission.setRid(user.getRoleId());
        permission.setRname(Constant.ROLE_USER);
        permission.setStatus(Constant.STATUS);
        permissionRepository.saveAndFlush(permission);
    }

    @Override
    public LoginInfoVO login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        if (!password.equals(user.getPassword())){
            throw new HosException(ErrorInfo.PASSWORD_IS_FALSE.getMessage());
        }
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        loginInfoVO.setUsername(user.getUsername());
        loginInfoVO.setPassword(user.getPassword());
        loginInfoVO.setPhone(user.getPhone());
        roleRepository.findById(user.getRoleId()).ifPresent(role -> loginInfoVO.setRoleName(role.getRname()));
        String token = jwtService.sign(user.getUid());
        loginInfoVO.setToken(token);
        return loginInfoVO;
    }

    @Override
    public void updateUser(String uid, UserVO userVO) {
        User user = userRepository.findByUidAndStatus(uid, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        if (StringUtils.isNotBlank(userVO.getName())){
            Optional<User> username = userRepository.findByUsername(userVO.getName());
            if (username.isPresent()){
                throw new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage());
            }
            user.setUsername(userVO.getName());
        }
        if (StringUtils.isNotBlank(userVO.getPhoto())){
            user.setPhoto(userVO.getPhoto());
        }
        if (StringUtils.isNotBlank(userVO.getWei())){
            user.setWei(userVO.getWei());
        }
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
            roleRepository.findRoleByRname(userVO.getRoleName()).ifPresent(role -> user.setRoleId(role.getRid()));
            Role role = roleRepository.findRoleByRname(userVO.getRoleName()).orElse(null);
            Permission permission = permissionRepository.findByUidAndStatus(uid, Constant.STATUS);
            assert role != null;
            permission.setRid(role.getRid());
            permission.setRname(role.getRname());
        }
        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateUserByAdmin(UserVO userVO) {
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
        if (StringUtils.isNotBlank(userVO.getWei())){
            user.setWei(userVO.getWei());
        }
        if (StringUtils.isNotBlank(userVO.getRoleName())){
            roleRepository.findRoleByRname(userVO.getRoleName()).ifPresent(role -> user.setRoleId(role.getRid()));
        }
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findByUidAndStatus(id, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        user.setStatus(Constant.DEL_STATUS);
        userRepository.saveAndFlush(user);
    }

    @Override
    public PageInfo<UserVO> selectByPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);

        if (StringUtils.isNoneBlank(name)){
            List<UserVO> userVOS = userRepository.findLikeUsernameAndStatus(name, Constant.STATUS)
                    .stream().map(this::makeVO).collect(Collectors.toList());
            return new PageInfo<>(userVOS);
        }
        List<UserVO> users = userRepository.findByStatus(Constant.STATUS)
                .stream().map(this::makeVO).collect(Collectors.toList());
        return new PageInfo<>(users);
    }

    private UserVO makeVO(User user) {
        UserVO userVO = new UserVO();
        userVO.setName(user.getUsername());
        userVO.setPassword(user.getPassword());
        userVO.setPhone(user.getPhone());
        userVO.setRemark(user.getRemark());
        userVO.setId(user.getUid());
        roleRepository.findById(user.getRoleId()).ifPresent(role -> userVO.setRoleName(role.getRname()));
        return userVO;
    }

    @Override
    public UserVO selectById(String id) {
        User user = userRepository.findByUidAndStatus(id, Constant.STATUS)
                .orElseThrow(() -> new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        UserVO userVO = new UserVO();
        userVO.setRemark(user.getRemark());
        userVO.setPhone(user.getPhone());
        userVO.setName(user.getUsername());
        roleRepository.findById(user.getRoleId()).ifPresent(role -> userVO.setRoleName(role.getRname()));
        userVO.setWei(user.getWei());
        userVO.setPhoto(user.getPhoto());
        return userVO;
    }

    @Override
    public List<String> getRoles(String uid) {
        List<Permission> permissions = permissionRepository.findAllByUidAndStatus(uid, Constant.STATUS).orElse(Lists.newArrayList());
        return permissions.stream().map(Permission::getRname).collect(Collectors.toList());
    }

//    @Override
//    public void upload(String uid, MultipartFile image) throws IOException {
//        if (!image.isEmpty()) {
//            String imageStr = getImageStr(image);
//            User user = userRepository.findByUidAndStatus(uid, Constant.STATUS)
//                    .orElseThrow(() -> new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
//            user.setPhoto(imageStr);
//            userRepository.saveAndFlush(user);
//        } else {
//            throw new HosException(ErrorInfo.PHOTO_NOT_FOUND.getMessage());
//        }
//    }
//
//    public static String getImageStr(MultipartFile file) throws IOException {
//        return Base64.getEncoder().encodeToString(file.getBytes());
//    }

    @Override
    public void upload(String accountId, MultipartFile image) throws IOException {
        String base64 = Base64Utils.encodeToString(image.getBytes());
        String assetCode = UUID.randomUUID().toString();
        File dir = new File(dirPath());
        dir.mkdirs();
        File file = new File(dir, assetCode);
        byte[] fileBytes = Base64Utils.decodeFromString(base64);
        FileUtils.writeByteArrayToFile(file, fileBytes);
    }

    private String dirPath() {
        return StringUtils.join(new String[] {"/home/econtract/appdata", new SimpleDateFormat("yyyyMMdd").format(new Date()), "containerName", StringUtils.EMPTY}, StringPool.SLASH);
    }
}
