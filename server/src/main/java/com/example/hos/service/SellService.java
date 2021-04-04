package com.example.hos.service;

import com.example.hos.model.Sell;
import com.github.pagehelper.PageInfo;

/**
 * 销售操作
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/3/17
 */
public interface SellService {


    PageInfo<Sell> selectByPage(Integer pageNum, Integer pageSize);

    String addSell();
}
