package com.example.hos.controller;

import com.example.hos.interceptor.Authorization;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.SellVO;
import com.example.hos.service.SellService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author changwei.zhong
 * @date create by 2021/3/17
 */
@Authorization
@RestController
@RequestMapping("/sell")
public class SellController {

    @Resource
    private SellService sellService;


    @ApiOperation(value = "销售列表")
    @GetMapping(value = "/findByPage")
    public ResultResponse pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                   @RequestParam(defaultValue = "6",required = false) Integer pageSize,
                                   String name){
        return sellService.selectByPage(pageNum, pageSize, name);
    }


    @ApiOperation(value = "新增销售单", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/addSell",method = RequestMethod.POST)
    public ResultResponse addSell(@RequestBody SellVO sellVO){
        return sellService.addSell(sellVO);
    }


    @ApiOperation(value = "删除销售单", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public ResultResponse delProduct(@RequestBody SellVO sellVO) {
        return sellService.delSell(sellVO.getSellId());
    }
}
