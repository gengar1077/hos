package com.example.hos.controller;

import com.example.hos.interceptor.Authorization;
import com.example.hos.interceptor.RoleAccess;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.StockVO;
import com.example.hos.service.ProductService;
import com.example.hos.service.StockService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Authorization
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private StockService stockService;


    @ApiOperation(value = "新增", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<Void> addProduct(@RequestBody ProductVO productVO) {
        productService.addProduct(productVO);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "删除", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public ResponseEntity<Void> delProduct(@RequestBody ProductVO productVO) {
        productService.delProduct(productVO.getPid());
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "药品信息修改")
    @PostMapping(value = "/update")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductVO productVO){
        productService.updateProduct(productVO);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "分页查询药品")
    @GetMapping(value = "/findByPage")
    public ResponseEntity<PageInfo<ProductVO>> pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                                        @RequestParam(defaultValue = "6",required = false) Integer pageSize,
                                                        String name){
        return ResponseEntity.ok().body(productService.selectByPage(pageNum, pageSize, name));
    }


    @ApiOperation(value = "查询药品信息")
    @GetMapping(value = "/findByName")
    public ResponseEntity<Void> findProduct(String name){
        productService.findProduct(name);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "查询药品库存")
    @GetMapping(value = "/findStock")
    @RoleAccess(roles = {Constant.ROLE_STOCK, Constant.ROLE_ADMIN})
    public ResponseEntity<PageInfo<StockVO>> stockList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                    @RequestParam(defaultValue = "6",required = false) Integer pageSize,
                                    String name){
        return ResponseEntity.ok().body(stockService.selectByPage(pageNum, pageSize, name));
    }


    @ApiOperation(value = "更改库存", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/editStock",method = RequestMethod.POST)
    @RoleAccess(roles = {Constant.ROLE_STOCK, Constant.ROLE_ADMIN})
    public ResponseEntity<Void> editStock(@RequestBody StockVO stockVO) {
        stockService.inStock(stockVO.getPname(), stockVO.getPNum());
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "添加库存单", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/addStock",method = RequestMethod.POST)
    @RoleAccess(roles = {Constant.ROLE_STOCK, Constant.ROLE_ADMIN})
    public ResponseEntity<Void> addStock(@RequestBody StockVO stockVO) {
        stockService.addStock(stockVO);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "删除库存单", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/delStock",method = RequestMethod.POST)
    @RoleAccess(roles = {Constant.ROLE_STOCK, Constant.ROLE_ADMIN})
    public ResponseEntity<Void> delStock(@RequestBody StockVO stockVO) {
        stockService.delStock(stockVO.getStockId());
        return ResponseEntity.ok().build();
    }
}
