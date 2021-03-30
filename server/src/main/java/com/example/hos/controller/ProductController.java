package com.example.hos.controller;

import com.example.hos.interceptor.Authorization;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.service.ProductService;
import com.example.hos.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Authorization
@Controller
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private StockService stockService;

    @ApiOperation(value = "新增", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ResultResponse addProduct(ProductVO productVO) {
        return productService.addProduct(productVO);
    }

    @ApiOperation(value = "删除", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ResponseBody
    public ResultResponse delProduct(String pid) {
        return productService.delProduct(pid);
    }

    @ApiOperation(value = "药品信息修改")
    @PostMapping(value = "/update")
    @ResponseBody
    public ResultResponse updateProduct(ProductVO productVO){
        return productService.updateProduct(productVO);
    }

    @ApiOperation(value = "分页查询药品")
    @GetMapping(value = "/findByPage")
    @ResponseBody
    public ResultResponse pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                           @RequestParam(defaultValue = "6",required = false) Integer pageSize){
        return productService.selectByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "查询药品库存")
    @GetMapping(value = "/findStock")
    @ResponseBody
    public ResultResponse stockList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                           @RequestParam(defaultValue = "6",required = false) Integer pageSize){
        return stockService.selectByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "更改库存", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/editStock",method = RequestMethod.POST)
    @ResponseBody
    public ResultResponse editStock(String pid, int num) {
        return stockService.inStock(pid, num);
    }
}
