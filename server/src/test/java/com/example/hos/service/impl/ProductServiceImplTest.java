package com.example.hos.service.impl;

import com.example.hos.model.entity.Role;
import com.example.hos.until.Constant;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class ProductServiceImplTest {
    
    @Test
    public void test(){
        Role role = new Role();
        role.setRid(UUID.randomUUID().toString());
        role.setRname("admin");
        role.setStatus(Constant.STATUS);
        role.setRemark("管理员");
    }
}