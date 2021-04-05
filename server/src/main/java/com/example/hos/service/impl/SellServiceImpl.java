package com.example.hos.service.impl;

import com.example.hos.dao.repository.ProductRepository;
import com.example.hos.dao.repository.SellRespository;
import com.example.hos.dao.repository.StockRepository;
import com.example.hos.dao.repository.UserRepository;
import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Product;
import com.example.hos.model.entity.Sell;
import com.example.hos.model.entity.Stock;
import com.example.hos.model.entity.User;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.SellVO;
import com.example.hos.service.SellService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
    public ResultResponse addSell(SellVO sellVO) {
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
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse delSell(String sellId) {
        ResultResponse resultResponse = new ResultResponse();
        Sell sell = sellRespository.findBySellIdAndStatus(sellId, Constant.STATUS).
                orElseThrow(()-> new HosException(ErrorInfo.DATA_ERROR.getMessage()));
        sell.setStatus(Constant.DEL_STATUS);
        sellRespository.saveAndFlush(sell);
        resultResponse.setSuccess(true);
        return null;
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        if (StringUtils.isNoneBlank(name)){
            Map<String, Sell> productMap = sellRespository.findLikeProductnameAndStatus(name, Constant.STATUS).stream().collect(Collectors.toMap(Sell::getPname, Function.identity()));
            List<Product> tProducts = productRepository.findAllByStatus(Constant.STATUS);
            List<SellVO> sellVOList = Lists.newArrayList();
            tProducts.forEach(product -> {
                Sell sell = productMap.get(product.getPname());
                if (ObjectUtils.allNotNull(sell)){
                    SellVO sellVO = new SellVO();
                    ProductVO productVO = new ProductVO();
                    productVO.setPid(product.getPid());
                    productVO.setPname(product.getPname());
                    productVO.setPrice(product.getPrice());
                    productVO.setSpec(product.getSpec());
                    productVO.setRemark(product.getRemark());
                    productVO.setPlace(product.getPlace());
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
            PageInfo<SellVO> pageInfo = new PageInfo<>(sellVOList);
            ResultResponse response = new ResultResponse();
            response.setReturnData(pageInfo);
            return response;
        }
        Map<String, Sell> productMap = sellRespository.findAllByStatus(Constant.STATUS).stream().collect(Collectors.toMap(Sell::getPname, Function.identity()));
        List<Product> tProducts = productRepository.findAllByStatus(Constant.STATUS);
        List<SellVO> sellVOList = Lists.newArrayList();
        tProducts.forEach(product -> {
            Sell sell = productMap.get(product.getPname());
            if (ObjectUtils.allNotNull(sell)){
                SellVO sellVO = new SellVO();
                ProductVO productVO = new ProductVO();
                productVO.setPid(product.getPid());
                productVO.setPname(product.getPname());
                productVO.setPrice(product.getPrice());
                productVO.setSpec(product.getSpec());
                productVO.setRemark(product.getRemark());
                productVO.setPlace(product.getPlace());
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
        PageInfo<SellVO> pageInfo = new PageInfo<>(sellVOList);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }
}
