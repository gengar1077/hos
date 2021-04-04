package com.example.hos.service.impl;

import com.example.hos.dao.repository.ProductRepository;
import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Product;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.service.ProductService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductRepository productRepository;

    @Override
    public ResultResponse addProduct(ProductVO productVO) {
        ResultResponse resultResponse = new ResultResponse();
        Optional.ofNullable(productRepository.findByPname(productVO.getPName())).orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        Product product = new Product();
        product.setPname(productVO.getPName());
        product.setStatus(Constant.STATUS);
        productRepository.saveAndFlush(product);
        return resultResponse;
    }

    @Override
    public ResultResponse delProduct(String pid) {
        ResultResponse resultResponse = new ResultResponse();
        Product product = productRepository.findByPidAndStatus(pid, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        product.setStatus("0");
        productRepository.saveAndFlush(product);
        return resultResponse;
    }

    @Override
    public ResultResponse updateProduct(ProductVO productVO) {
        ResultResponse resultResponse = new ResultResponse();
        Product product = productRepository.findByPidAndStatus(productVO.getPid(), Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        Optional.ofNullable(productRepository.findByPname(productVO.getPName())).orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        if (StringUtils.isNotBlank(productVO.getPName())){
            product.setPname(productVO.getPName());
        }
        productRepository.saveAndFlush(product);
        return resultResponse;
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        List<ProductVO> products = productRepository.findAllByStatus(Constant.STATUS).stream().map(product -> {
            ProductVO productVO = new ProductVO();
            productVO.setPName(product.getPname());
            return productVO;
        }).collect(Collectors.toList());
        PageInfo<ProductVO> pageInfo = new PageInfo<>(products);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }
}
