package com.example.hos.service.impl;

import com.example.hos.mapper.TProductMapper;
import com.example.hos.mapper.TStockMapper;
import com.example.hos.model.TProduct;
import com.example.hos.model.TStock;
import com.example.hos.service.StockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author changwei.zhong
 * @date create by 2021/3/8
 */
@Service
public class StockServiceImpl implements StockService {

    @Resource
    private TStockMapper stockMapper;

    @Resource
    private TProductMapper productMapper;

    @Override
    public String inStock(Long pid,int num) {
        TProduct product = productMapper.selectByPrimaryKey(pid);
        if (Objects.isNull(product)){
            TStock stock = new TStock();
            stock.setpId(pid);
            stock.setpName(product.getpName());
            stock.setpNum(num);
            stock.setCreateTime(new Date());
        }
        TStock stock = stockMapper.selectByPrimaryKey(pid);
        stock.setpNum(num);
        return null;
    }

    @Override
    public PageInfo<TStock> selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        final List<TStock> stocks = stockMapper.selectByExample(null);
        final PageInfo<TStock> stockPage = new PageInfo<>(stocks);
        return stockPage;
    }
}
