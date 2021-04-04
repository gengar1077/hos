package com.example.hos.service.impl;

import com.example.hos.model.Sell;
import com.example.hos.service.SellService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * @author changwei.zhong
 * @date create by 2021/3/17
 */
@Service
public class SellServiceImpl implements SellService {

    @Override
    public PageInfo<Sell> selectByPage(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public String addSell() {
        return null;
    }
}
