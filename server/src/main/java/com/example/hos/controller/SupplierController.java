package com.example.hos.controller;

import com.example.hos.interceptor.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author changwei.zhong
 * @date create by 2021/3/22
 */
@Authorization
@Controller
@RequestMapping("/supplier")
public class SupplierController {
}
