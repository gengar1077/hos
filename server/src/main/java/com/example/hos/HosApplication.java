package com.example.hos;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.hos.mapper")
@SpringBootApplication
public class HosApplication {

    public static void main(String[] args) {
        SpringApplication.run(HosApplication.class, args);
    }

}
