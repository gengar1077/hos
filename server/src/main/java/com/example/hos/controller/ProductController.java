package com.example.hos.controller;

import com.example.hos.model.TProduct;
import com.example.hos.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @ApiOperation(value = "新增", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addProduct(TProduct product) {
        productService.addProduct(product);
        return "product/productList";
    }

    @ApiOperation(value = "删除", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public String delProduct(Long pid) {
        productService.delProduct(pid);
        return "product/productList";
    }
}
