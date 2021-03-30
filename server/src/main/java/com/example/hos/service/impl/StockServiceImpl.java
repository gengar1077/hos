package com.example.hos.service.impl;

import com.example.hos.handle.HosException;
import com.example.hos.mapper.TProductMapper;
import com.example.hos.mapper.TStockMapper;
import com.example.hos.model.TProduct;
import com.example.hos.model.TStock;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.StockVO;
import com.example.hos.service.ResponseService;
import com.example.hos.service.StockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
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

    @Resource
    private ResponseService responseService;

    @Override
    public ResultResponse inStock(String pid, int num) {
        TProduct product = Optional.ofNullable(productMapper.selectByPrimaryKey(pid))
                .orElseThrow(() -> new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        TStock stock = Optional.ofNullable(stockMapper.selectByPrimaryKey(pid))
                .orElseThrow(() -> new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));;
        stock.setpId(pid);
        stock.setpName(product.getpName());
        stock.setpNum(num);
        stock.setCreatetime(new Date());
        stock.setpNum(num);
        ResultResponse resultResponse = new ResultResponse();
        return resultResponse.success(responseService.message(ResultResponse.Code.SUCCESS));
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize) {
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
            BeanUtils.copyProperties(stockVO, stock);
            stockVOList.add(stockVO);
        });
        PageInfo<StockVO> pageInfo = new PageInfo<>(stockVOList);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }
}
