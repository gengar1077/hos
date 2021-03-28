package com.example.hos.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JwtServiceTest {

    @Resource
    private JwtService jwtService;

    @Test
    public void sign() {
        String uid = "22";
        String token = jwtService.sign(uid);
        System.out.println(token);
    }

    @Test
    public void unSign() {
    }
}