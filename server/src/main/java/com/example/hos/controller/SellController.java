package com.example.hos.controller;

import com.example.hos.interceptor.Authorization;
import com.example.hos.model.Sell;
import com.example.hos.service.SellService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author changwei.zhong
 * @date create by 2021/3/17
 */
@Authorization
@Controller
@RequestMapping("/sell")
public class SellController {

    @Resource
    private SellService sellService;

    @ApiOperation(value = "获取药品出售清单")
    @GetMapping(value = "/findByPage")
    public String pageList(@RequestParam(defaultValue = "1",required = false) Integer pageNum,
                           @RequestParam(defaultValue = "6",required = false) Integer pageSize, ModelMap modelMap){
        PageInfo<Sell> sellPageInfo = sellService.selectByPage(pageNum,pageSize);
        modelMap.put("sellPageInfo", sellPageInfo);
        return "product/productList";
    }

    public String addSell(){
        String s = sellService.addSell();
        return s;
    }
}
