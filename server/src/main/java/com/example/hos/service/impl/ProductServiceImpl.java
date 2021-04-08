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
        Optional<Product> byPname = productRepository.findByPname(productVO.getPname());
        if (byPname.isPresent()){
            throw new HosException(ErrorInfo.PRODUCT_IS_EXIST.getMessage());
        }
        Product product = new Product();
        product.setPname(productVO.getPname());
        product.setSpec(productVO.getSpec());
        product.setPlace(productVO.getPlace());
        product.setRemark(productVO.getRemark());
        product.setPrice(productVO.getPrice());
        product.setStatus(Constant.STATUS);
        productRepository.saveAndFlush(product);
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse delProduct(String pid) {
        ResultResponse resultResponse = new ResultResponse();
        Product product = productRepository.findByPidAndStatus(pid, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.PRODUCT_NOT_FOUND.getMessage()));
        product.setStatus(Constant.DEL_STATUS);
        productRepository.saveAndFlush(product);
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse updateProduct(ProductVO productVO) {
        ResultResponse resultResponse = new ResultResponse();
        Product product = productRepository.findByPidAndStatus(productVO.getPid(), Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.PRODUCT_NOT_FOUND.getMessage()));
        Optional.ofNullable(productRepository.findByPname(productVO.getPname())).orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        if (StringUtils.isNotBlank(productVO.getPname())){
            product.setPname(productVO.getPname());
        }
        product.setSpec(productVO.getSpec());
        product.setPlace(productVO.getPlace());
        product.setRemark(productVO.getRemark());
        product.setPrice(productVO.getPrice());
        productRepository.saveAndFlush(product);
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        if (StringUtils.isNoneBlank(name)){
            List<ProductVO> productVOS = productRepository.findLikeProductnameAndStatus(name, Constant.STATUS).stream().map(product -> {
                ProductVO productVO = new ProductVO();
                productVO.setPid(product.getPid());
                productVO.setPname(product.getPname());
                productVO.setPlace(product.getPlace());
                productVO.setRemark(product.getRemark());
                productVO.setSpec(product.getSpec());
                productVO.setPrice(product.getPrice());
                return productVO;
            }).collect(Collectors.toList());
            PageInfo<ProductVO> pageInfo = new PageInfo<>(productVOS);
            ResultResponse response = new ResultResponse();
            response.setReturnData(pageInfo);
            return response;
        }
        List<ProductVO> products = productRepository.findAllByStatus(Constant.STATUS).stream().map(product -> {
            ProductVO productVO = new ProductVO();
            productVO.setPid(product.getPid());
            productVO.setPname(product.getPname());
            productVO.setPlace(product.getPlace());
            productVO.setRemark(product.getRemark());
            productVO.setSpec(product.getSpec());
            productVO.setPrice(product.getPrice());
            return productVO;
        }).collect(Collectors.toList());
        PageInfo<ProductVO> pageInfo = new PageInfo<>(products);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }

    @Override
    public ResultResponse findProduct(String name) {
        Product product = productRepository.findByPname(name)
                .orElseThrow((()-> new HosException(ErrorInfo.PRODUCT_NOT_FOUND.getMessage())));
        ProductVO productVO = new ProductVO();
        productVO.setPname(product.getPname());
        productVO.setPlace(product.getPlace());
        productVO.setRemark(product.getRemark());
        productVO.setSpec(product.getSpec());
        productVO.setPrice(product.getPrice());
        ResultResponse response = new ResultResponse();
        response.setReturnData(productVO);
        return response;
    }
}
