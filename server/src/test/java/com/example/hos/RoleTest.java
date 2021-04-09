package com.example.hos;

import com.example.hos.repository.PermissionRepository;
import com.example.hos.repository.RoleRepository;
import com.example.hos.model.entity.Permission;
import com.example.hos.model.entity.Role;
import com.example.hos.until.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/4/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RoleTest {

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private PermissionRepository permissionRepository;

    @Test
    public void roleTest(){
        Role role = new Role();
        role.setRname("user");
        role.setStatus(Constant.STATUS);
        role.setRemark("user");
        roleRepository.saveAndFlush(role);
    }

    @Test
    public void permissionTest(){
        Permission permission = new Permission();
        permission.setUid("b667c410-4ce3-4e52-97ca-28f9b9001138");
        permission.setUsername("jk");
        permission.setRid("7f3a3216-b161-4bd2-b552-c832271374ab");
        permission.setRname("admin");
        permission.setStatus(Constant.STATUS);
        permissionRepository.saveAndFlush(permission);
    }
}
