package com.example.hos.service.impl;

import com.example.hos.repository.ProductRepository;
import com.example.hos.repository.SellRespository;
import com.example.hos.repository.StockRepository;
import com.example.hos.repository.UserRepository;
import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Product;
import com.example.hos.model.entity.Sell;
import com.example.hos.model.entity.Stock;
import com.example.hos.model.entity.User;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.SellVO;
import com.example.hos.service.SellService;
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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author changwei.zhong
 * @date create by 2021/3/17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SellServiceImpl implements SellService {

    @Resource
    private SellRespository sellRespository;

    @Resource
    private StockRepository stockRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProductRepository productRepository;

    @Override
    public void addSell(SellVO sellVO) {
        Sell sell = new Sell();
        sell.setCreatetime(new Date());
        sell.setMoney(sellVO.getMoney());
        sell.setStatus(Constant.STATUS);
        sell.setRemark(sellVO.getRemark());
        sell.setPname(sellVO.getPname());
        Stock stock = stockRepository.findByPnameAndStatus(sellVO.getPname(), Constant.STATUS)
                .orElseThrow(() -> new HosException(ErrorInfo.STOCK_NOT_FOUND.getMessage()));
        sell.setPNum(stock.getPNum());
        User user = userRepository.findByUsername(sellVO.getOperator())
                .orElseThrow(() -> new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        sell.setOperator(user.getUsername());
        sellRespository.saveAndFlush(sell);
    }

    @Override
    public void delSell(String sellId) {
        Sell sell = sellRespository.findBySellIdAndStatus(sellId, Constant.STATUS).
                orElseThrow(()-> new HosException(ErrorInfo.DATA_ERROR.getMessage()));
        sell.setStatus(Constant.DEL_STATUS);
        sellRespository.saveAndFlush(sell);
    }

    @Override
    public PageInfo<SellVO> selectByPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        if (StringUtils.isNoneBlank(name)){
            Map<String, Sell> productMap = sellRespository.findLikeProductnameAndStatus(name, Constant.STATUS).stream().collect(Collectors.toMap(Sell::getPname, Function.identity()));
            return new PageInfo<>(makeListVO(productMap));
        }
        Map<String, Sell> productMap = sellRespository.findAllByStatus(Constant.STATUS).stream().collect(Collectors.toMap(Sell::getPname, Function.identity()));
        return new PageInfo<>(makeListVO(productMap));
    }

    private List<SellVO> makeListVO(Map<String, Sell> productMap) {
        List<Product> tProducts = productRepository.findAllByStatus(Constant.STATUS);
        List<SellVO> sellVOList = Lists.newArrayList();
        tProducts.forEach(product -> {
            Sell sell = productMap.get(product.getPname());
            if (ObjectUtils.allNotNull(sell)){
                SellVO sellVO = new SellVO();
                ProductVO productVO = ProductVO.makeVO(product);
                sellVO.setProductVO(productVO);
                sellVO.setCreatetime(new Date());
                sellVO.setMoney(sell.getMoney());
                sellVO.setSellId(sell.getSellId());
                sellVO.setPname(sell.getPname());
                sellVO.setRemark(sell.getRemark());
                sellVO.setOperator(sell.getOperator());
                sellVOList.add(sellVO);
            }
        });
        return sellVOList;
    }
}
