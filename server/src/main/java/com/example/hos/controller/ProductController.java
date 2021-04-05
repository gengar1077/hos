package com.example.hos.controller;

import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.StockVO;
import com.example.hos.service.ProductService;
import com.example.hos.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
//@Authorization
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private StockService stockService;

    @ApiOperation(value = "新增", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultResponse addProduct(@RequestBody ProductVO productVO) {
        return productService.addProduct(productVO);
    }

    @ApiOperation(value = "删除", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public ResultResponse delProduct(@RequestBody ProductVO productVO) {
        return productService.delProduct(productVO.getPid());
    }

    @ApiOperation(value = "药品信息修改")
    @PostMapping(value = "/update")
    public ResultResponse updateProduct(@RequestBody ProductVO productVO){
        return productService.updateProduct(productVO);
    }

    @ApiOperation(value = "分页查询药品")
    @GetMapping(value = "/findByPage")
    public ResultResponse pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                   @RequestParam(defaultValue = "6",required = false) Integer pageSize,
                                   String name){
        return productService.selectByPage(pageNum, pageSize, name);
    }

    @ApiOperation(value = "查询药品信息")
    @GetMapping(value = "/findByName")
    public ResultResponse findProduct(String name){
        return productService.findProduct(name);
    }

    @ApiOperation(value = "查询药品库存")
    @GetMapping(value = "/findStock")
    public ResultResponse stockList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                    @RequestParam(defaultValue = "6",required = false) Integer pageSize,
                                    String name){
        return stockService.selectByPage(pageNum, pageSize, name);
    }

    @ApiOperation(value = "更改库存", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/editStock",method = RequestMethod.POST)
    public ResultResponse editStock(@RequestBody StockVO stockVO) {
        return stockService.inStock(stockVO.getPname(), stockVO.getPNum());
    }

    @ApiOperation(value = "添加库存单", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/addStock",method = RequestMethod.POST)
    public ResultResponse addStock(@RequestBody StockVO stockVO) {
        return stockService.addStock(stockVO);
    }
}
