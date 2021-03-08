package com.example.hos.controller;

import com.example.hos.model.TProduct;
import com.example.hos.model.TStock;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.service.ProductService;
import com.example.hos.service.StockService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private StockService stockService;

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

    @ApiOperation(value = "药品信息修改")
    @PostMapping(value = "/update")
    public String updateProduct(ProductVO productVO){
        productService.updateProduct(productVO);
        return "product/productList";
    }

    @ApiOperation(value = "分页查询药品")
    @GetMapping(value = "/findByPage")
    public String pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                           @RequestParam(defaultValue = "6",required = false) Integer pageSize, ModelMap modelMap){
        PageInfo<TProduct> productPage = productService.selectByPage(pageNum,pageSize);
        modelMap.put("productPage",productPage);
        return "product/productList";
    }

    @ApiOperation(value = "查询药品库存")
    @GetMapping(value = "/findStock")
    public String stockList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                           @RequestParam(defaultValue = "6",required = false) Integer pageSize, ModelMap modelMap){
        PageInfo<TStock> stockPage = stockService.selectByPage(pageNum, pageSize);
        modelMap.put("stockPage",stockPage);
        return "product/stockList";
    }

    @ApiOperation(value = "更改库存", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/editStock",method = RequestMethod.POST)
    public String editStock(Long pid,int num) {
        stockService.inStock(pid,num);
        return "product/stockList";
    }
}
