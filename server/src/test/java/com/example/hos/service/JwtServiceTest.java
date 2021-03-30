package com.example.hos.service;

import com.example.hos.model.TUser;
import com.example.hos.model.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
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

    @Test
    public void test() {
        TUser user = new TUser();
        UserVO userVO = new UserVO();
        userVO.setName("嘤嘤嘤");
        userVO.setPhone("157");
        userVO.setPassword("nmsl");
        userVO.setRemark("jwt");
        BeanUtils.copyProperties(user, userVO);
    }
}