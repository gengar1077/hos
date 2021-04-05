package com.example.hos.service.impl;

import com.example.hos.dao.repository.ProductRepository;
import com.example.hos.dao.repository.StockRepository;
import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Product;
import com.example.hos.model.entity.Stock;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.StockVO;
import com.example.hos.service.StockService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author changwei.zhong
 * @date create by 2021/3/8
 */
@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockRepository stockRepository;

    @Resource
    private ProductRepository productRepository;

    @Override
    public ResultResponse addStock(StockVO stockVO) {
        Optional<Stock> byPnameAndStatus = stockRepository.findByPnameAndStatus(stockVO.getPname(), Constant.STATUS);
        if (byPnameAndStatus.isPresent()){
            throw new HosException(ErrorInfo.STOCK_NOT_FOUND.getMessage());
        }
        Product product = productRepository.findByPname(stockVO.getPname()).orElseThrow(() -> new HosException(ErrorInfo.PRODUCT_IS_EXIST.getMessage()));
        Stock stock = new Stock();
        stock.setPname(stockVO.getPname());
        stock.setPid(product.getPid());
        stock.setCreatetime(new Date());
        stock.setStatus(Constant.STATUS);
        stockRepository.saveAndFlush(stock);
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse inStock(String pname, int num) {
        Product product = productRepository.findByPname(pname)
                .orElseThrow(() -> new HosException(ErrorInfo.PRODUCT_NOT_FOUND.getMessage()));
        Stock stock = stockRepository.findByPnameAndStatus(product.getPname(), Constant.STATUS)
                .orElseThrow(() -> new HosException(ErrorInfo.STOCK_NOT_FOUND.getMessage()));
        stock.setPNum(num);
        stock.setCreatetime(new Date());
        stockRepository.saveAndFlush(stock);
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        Map<String, Stock> productMap = stockRepository.findAllByStatus(Constant.STATUS).stream().collect(Collectors.toMap(Stock::getPname, Function.identity()));
        List<Product> tProducts = productRepository.findAllByStatus(Constant.STATUS);
        List<StockVO> stockVOList = Lists.newArrayList();
        tProducts.forEach(product -> {
            Stock stock = productMap.get(product.getPname());
            if (ObjectUtils.allNotNull(stock)){
                StockVO stockVO = new StockVO();
                ProductVO productVO = new ProductVO();
                productVO.setPname(product.getPname());
                productVO.setRemark(product.getRemark());
                stockVO.setProductVO(productVO);
                stockVO.setCreatetime(new Date());
                stockVO.setPNum(stock.getPNum());
                stockVOList.add(stockVO);
            }
        });
        PageInfo<StockVO> pageInfo = new PageInfo<>(stockVOList);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }
}
