package com.example.hos;

import com.example.hos.dao.repository.PermissionRepository;
import com.example.hos.dao.repository.RoleRepository;
import com.example.hos.model.entity.Permission;
import com.example.hos.model.entity.Role;
import com.example.hos.until.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.UUID;

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
        role.setRname("sell");
        role.setStatus(Constant.STATUS);
        role.setRemark("sell");
        roleRepository.saveAndFlush(role);
    }

    @Test
    public void permissionTest(){
        Permission permission = new Permission();
        permission.setUid("32008fcf-823d-4a5c-bbfc-54eadc2ce936");
        permission.setUsername("jk");
        permission.setRid("c8c6e53c-15b5-492b-ad51-41a84b5f4e6a");
        permission.setRname("admin");
        permission.setStatus(Constant.STATUS);
        permissionRepository.saveAndFlush(permission);
    }
}
