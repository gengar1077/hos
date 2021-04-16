package com.example.hos.controller;

import com.example.hos.interceptor.Authorization;
import com.example.hos.interceptor.RoleAccess;
import com.example.hos.model.entity.Supplier;
import com.example.hos.model.vo.SupplierVO;
import com.example.hos.service.SupplierService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author changwei.zhong
 * @date create by 2021/3/22
 */
@Authorization
@RestController
@RequestMapping("/supplier")
@RoleAccess(roles = {Constant.ROLE_ADMIN})
public class SupplierController {

    @Resource
    private SupplierService supplierService;


    @ApiOperation(value = "新增供应商", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/addSupplier",method = RequestMethod.POST)
    public ResponseEntity<Void> addStock(@RequestBody SupplierVO supplierVO) {
        supplierService.addSupplier(supplierVO);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "删除", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public ResponseEntity<Void> delProduct(@RequestBody SupplierVO supplierVO) {
        supplierService.delSupplier(supplierVO.getSid());
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "查询供应商列表")
    @GetMapping(value = "/findStock")
    public ResponseEntity<PageInfo<SupplierVO>> supplierList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                                           @RequestParam(defaultValue = "6",required = false) Integer pageSize,
                                                           String name){
        return ResponseEntity.ok(supplierService.selectByPage(pageNum, pageSize, name));
    }
}
