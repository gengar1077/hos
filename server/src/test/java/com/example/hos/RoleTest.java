package com.example.hos;

import com.example.hos.dao.repository.RoleRepository;
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

    @Test
    public void test(){
        Role role = new Role();
        role.setRid(UUID.randomUUID().toString());
        role.setRname("admin");
        role.setStatus(Constant.STATUS);
        role.setRemark("admin");
        roleRepository.saveAndFlush(role);
    }
}
