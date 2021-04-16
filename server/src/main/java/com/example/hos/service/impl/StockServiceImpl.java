package com.example.hos.service.impl;

import com.example.hos.repository.ProductRepository;
import com.example.hos.repository.StockRepository;
import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Product;
import com.example.hos.model.entity.Stock;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.StockVO;
import com.example.hos.service.StockService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = Exception.class)
public class StockServiceImpl implements StockService {

    @Resource
    private StockRepository stockRepository;

    @Resource
    private ProductRepository productRepository;

    @Override
    public void addStock(StockVO stockVO) {
        Optional<Stock> byPnameAndStatus = stockRepository.findByPnameAndStatus(stockVO.getPname(), Constant.STATUS);
        if (byPnameAndStatus.isPresent()){
            throw new HosException(ErrorInfo.DATA_ERROR.getMessage());
        }
        Product product = productRepository.findByPname(stockVO.getPname()).orElseThrow(() -> new HosException(ErrorInfo.PRODUCT_NOT_FOUND.getMessage()));
        Stock stock = new Stock();
        Optional<Stock> byPname = stockRepository.findByPname(stockVO.getPname());
        if (byPname.isPresent()){
            throw new HosException(ErrorInfo.PRODUCT_IS_EXIST.getMessage());
        }
        stock.setPname(stockVO.getPname());
        stock.setPid(product.getPid());
        stock.setCreatetime(new Date());
        stock.setStatus(Constant.STATUS);
        stockRepository.saveAndFlush(stock);
    }

    @Override
    public void delStock(String stockId) {
        Stock stock = stockRepository.findByStockIdAndStatus(stockId, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.STOCK_NOT_FOUND.getMessage()));
        stock.setStatus(Constant.DEL_STATUS);
        stockRepository.saveAndFlush(stock);
    }

    @Override
    public void inStock(String pname, int num) {
        Product product = productRepository.findByPname(pname)
                .orElseThrow(() -> new HosException(ErrorInfo.PRODUCT_NOT_FOUND.getMessage()));
        Stock stock = stockRepository.findByPnameAndStatus(product.getPname(), Constant.STATUS)
                .orElseThrow(() -> new HosException(ErrorInfo.STOCK_NOT_FOUND.getMessage()));
        stock.setPNum(num);
        stock.setCreatetime(new Date());
        stockRepository.saveAndFlush(stock);
    }

    @Override
    public PageInfo<StockVO> selectByPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        if (StringUtils.isNoneBlank(name)){
            Map<String, Stock> productMap = stockRepository.findLikeProductnameAndStatus(name, Constant.STATUS).stream().collect(Collectors.toMap(Stock::getPname, Function.identity()));
            List<Product> tProducts = productRepository.findAllByStatus(Constant.STATUS);
            List<StockVO> stockVOList = Lists.newArrayList();
            tProducts.forEach(product -> {
                Stock stock = productMap.get(product.getPname());
                if (ObjectUtils.allNotNull(stock)){
                    stockVOList.add(makeVO(product, stock));
                }
            });
            return new PageInfo<>(stockVOList);
        }
        Map<String, Stock> productMap = stockRepository.findAllByStatus(Constant.STATUS).stream().collect(Collectors.toMap(Stock::getPname, Function.identity()));
        List<Product> tProducts = productRepository.findAllByStatus(Constant.STATUS);
        List<StockVO> stockVOList = Lists.newArrayList();
        tProducts.forEach(product -> {
            Stock stock = productMap.get(product.getPname());
            if (ObjectUtils.allNotNull(stock)){
                stockVOList.add(makeVO(product, stock));
            }
        });
        return new PageInfo<>(stockVOList);
    }

    private StockVO makeVO(Product product, Stock stock) {
        StockVO stockVO = new StockVO();
        ProductVO productVO = ProductVO.makeVO(product);
        stockVO.setStockId(stock.getStockId());
        stockVO.setPname(stock.getPname());
        stockVO.setPNum(stock.getPNum());
        stockVO.setProductVO(productVO);
        stockVO.setCreatetime(new Date());
        stockVO.setPNum(stock.getPNum());
        return stockVO;
    }
}
