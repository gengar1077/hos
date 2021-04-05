package com.example.hos.controller;

import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.SupplierVO;
import com.example.hos.service.SupplierService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author changwei.zhong
 * @date create by 2021/3/22
 */
//@Authorization
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Resource
    private SupplierService supplierService;

    @ApiOperation(value = "新增供应商", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/addSupplier",method = RequestMethod.POST)
    public ResultResponse addStock(@RequestBody SupplierVO supplierVO) {
        return supplierService.addSupplier(supplierVO);
    }

    @ApiOperation(value = "删除", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public ResultResponse delProduct(@RequestBody SupplierVO supplierVO) {
        return supplierService.delSupplier(supplierVO.getSid());
    }

    @ApiOperation(value = "查询供应商列表")
    @GetMapping(value = "/findStock")
    public ResultResponse supplierList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                    @RequestParam(defaultValue = "6",required = false) Integer pageSize){
        return supplierService.selectByPage(pageNum, pageSize);
    }
}
