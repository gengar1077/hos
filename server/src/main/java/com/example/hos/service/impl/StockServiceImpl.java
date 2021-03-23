package com.example.hos.service.impl;

import com.example.hos.mapper.TProductMapper;
import com.example.hos.mapper.TStockMapper;
import com.example.hos.model.TProduct;
import com.example.hos.model.TStock;
import com.example.hos.model.vo.StockVO;
import com.example.hos.service.StockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public String inStock(String pid, int num) {
        TProduct product = productMapper.selectByPrimaryKey(pid);
        if (Objects.isNull(product)){
            TStock stock = new TStock();
            stock.setpId(pid);
            stock.setpName(product.getpName());
            stock.setpNum(num);
            stock.setCreatetime(new Date());
        }
        TStock stock = stockMapper.selectByPrimaryKey(pid);
        stock.setpNum(num);
        return null;
    }

    @Override
    public PageInfo<StockVO> selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        List<TStock> tStocks = stockMapper.selectByExample(null);
        Map<String, TStock> productMap = tStocks.stream().collect(Collectors.toMap(TStock::getpName, Function.identity()));
        List<TProduct> tProducts = productMapper.selectByExample(null);
        List<StockVO> stockVOList = Lists.newArrayList();
        tProducts.forEach(product -> {
            TStock stock = productMap.get(product.getpName());
            StockVO stockVO = new StockVO();
            BeanUtils.copyProperties(stockVO,stock);
            stockVOList.add(stockVO);
        });
        return new PageInfo<>(stockVOList);
    }
}
