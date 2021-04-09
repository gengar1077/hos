package com.example.hos.service.impl;

import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Permission;
import com.example.hos.model.entity.Role;
import com.example.hos.model.entity.User;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.LoginInfoVO;
import com.example.hos.model.vo.ResultResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Base64;
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
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse login(String username, String password) {
        ResultResponse resultResponse = new ResultResponse();
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
        resultResponse.setReturnData(loginInfoVO);
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse updateUser(String uid, UserVO userVO) {
        ResultResponse resultResponse = new ResultResponse();
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
        resultResponse.setSuccess(true);
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
        if (StringUtils.isNotBlank(userVO.getWei())){
            user.setWei(userVO.getWei());
        }
        if (StringUtils.isNotBlank(userVO.getRoleName())){
            roleRepository.findRoleByRname(userVO.getRoleName()).ifPresent(role -> user.setRoleId(role.getRid()));
        }
        userRepository.saveAndFlush(user);
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse deleteUser(String id) {
        ResultResponse resultResponse = new ResultResponse();
        User user = userRepository.findByUidAndStatus(id, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        user.setStatus(Constant.DEL_STATUS);
        userRepository.saveAndFlush(user);
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        if (StringUtils.isNoneBlank(name)){
            List<UserVO> userVOS = userRepository.findLikeUsernameAndStatus(name, Constant.STATUS).stream().map(user -> {
                UserVO userVO = new UserVO();
                userVO.setName(user.getUsername());
                userVO.setPassword(user.getPassword());
                userVO.setPhone(user.getPhone());
                userVO.setRemark(user.getRemark());
                userVO.setId(user.getUid());
                roleRepository.findById(user.getRoleId()).ifPresent(role -> userVO.setRoleName(role.getRname()));
                return userVO;
            }).collect(Collectors.toList());
            PageInfo<UserVO> pageInfo = new PageInfo<>(userVOS);
            ResultResponse response = new ResultResponse();
            response.setReturnData(pageInfo);
            return response;
        }
        List<UserVO> users = userRepository.findByStatus(Constant.STATUS).stream().map(user -> {
            UserVO userVO = new UserVO();
            userVO.setName(user.getUsername());
            userVO.setPassword(user.getPassword());
            userVO.setPhone(user.getPhone());
            userVO.setRemark(user.getRemark());
            userVO.setId(user.getUid());
            roleRepository.findById(user.getRoleId()).ifPresent(role -> userVO.setRoleName(role.getRname()));
            return userVO;
        }).collect(Collectors.toList());
        PageInfo<UserVO> pageInfo = new PageInfo<>(users);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }

    @Override
    public ResultResponse selectById(String id) {
        ResultResponse resultResponse = new ResultResponse();
        User user = userRepository.findByUidAndStatus(id, Constant.STATUS)
                .orElseThrow(() -> new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        UserVO userVO = new UserVO();
        userVO.setRemark(user.getRemark());
        userVO.setPhone(user.getPhone());
        userVO.setName(user.getUsername());
        roleRepository.findById(user.getRoleId()).ifPresent(role -> userVO.setRoleName(role.getRname()));
        userVO.setWei(user.getWei());
        userVO.setPhoto(user.getPhoto());
        resultResponse.setReturnData(userVO);
        return resultResponse;
    }

    @Override
    public List<String> getRoles(String uid) {
        List<Permission> permissions = permissionRepository.findAllByUidAndStatus(uid, Constant.STATUS).orElse(Lists.newArrayList());
        return permissions.stream().map(Permission::getRname).collect(Collectors.toList());
    }

//    @Override
//    public ResultResponse upload(String uid, MultipartFile image) {
//        ResultResponse resultResponse = new ResultResponse();
//        String filesPath = Constant.FILES_PATH;
//        if (!image.isEmpty()) {
//            // 当前用户
//            User user = userRepository.findByUidAndStatus(uid, Constant.STATUS)
//                    .orElseThrow(() -> new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
//            String photo = user.getPhoto();
//            // 默认以原来的头像名称为新头像的名称，这样可以直接替换掉文件夹中对应的旧头像
//            String imageName = photo;
//            // 若头像名称不存在
//            if (photo == null || StringPool.EMPTY.equals(photo)) {
//                imageName = filesPath + System.currentTimeMillis()+ image.getOriginalFilename();
//                // 路径存库
//                user.setPhoto(imageName);
//                userRepository.saveAndFlush(user);
//            }
//            // 磁盘保存
//            BufferedOutputStream out = null;
//            try {
//                File folder = new File(filesPath);
//                if (!folder.exists()) {
//                    boolean mkdirs = folder.mkdirs();
//                    if (!mkdirs){
//                        throw new HosException(ErrorInfo.PHOTO_NOT_FOUND.getMessage());
//                    }
//                }
//                out = new BufferedOutputStream(new FileOutputStream(imageName));
//                // 写入新文件
//                out.write(image.getBytes());
//                out.flush();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return resultResponse;
//            } finally {
//                try {
//                    assert out != null;
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return resultResponse;
//        } else {
//            throw new HosException(ErrorInfo.PHOTO_NOT_FOUND.getMessage());
//        }
//    }

    @Override
    public ResultResponse upload(String uid, MultipartFile image) throws IOException {
        ResultResponse resultResponse = new ResultResponse();
        if (!image.isEmpty()) {
            String imageStr = getImageStr(image);
            User user = userRepository.findByUidAndStatus(uid, Constant.STATUS)
                    .orElseThrow(() -> new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
            user.setPhoto(imageStr);
            userRepository.saveAndFlush(user);
        } else {
            throw new HosException(ErrorInfo.PHOTO_NOT_FOUND.getMessage());
        }
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    public static String getImageStr(MultipartFile file) throws IOException {
        byte[] fileByte = null;
        fileByte = file.getBytes();
        return Base64.getEncoder().encodeToString(fileByte);
    }
}
