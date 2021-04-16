package com.example.hos.service.impl;

import com.example.hos.repository.ProductRepository;
import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Product;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.service.ProductService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductRepository productRepository;

    @Override
    public void addProduct(ProductVO productVO) {
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
    }

    @Override
    public void delProduct(String pid) {
        Product product = productRepository.findByPidAndStatus(pid, Constant.STATUS)
                .orElseThrow(()->new HosException(ErrorInfo.PRODUCT_NOT_FOUND.getMessage()));
        product.setStatus(Constant.DEL_STATUS);
        productRepository.saveAndFlush(product);
    }

    @Override
    public void updateProduct(ProductVO productVO) {
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
    }

    @Override
    public PageInfo<ProductVO> selectByPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        if (StringUtils.isNoneBlank(name)){
            List<ProductVO> productVOS = productRepository.findLikeProductnameAndStatus(name, Constant.STATUS)
                    .stream().map(this::makeVO).collect(Collectors.toList());
            return new PageInfo<>(productVOS);
        }
        List<ProductVO> products = productRepository.findAllByStatus(Constant.STATUS)
                .stream().map(this::makeVO).collect(Collectors.toList());
        return new PageInfo<>(products);
    }

    private ProductVO makeVO(Product product) {
        ProductVO productVO = new ProductVO();
        productVO.setPid(product.getPid());
        productVO.setPname(product.getPname());
        productVO.setPlace(product.getPlace());
        productVO.setRemark(product.getRemark());
        productVO.setSpec(product.getSpec());
        productVO.setPrice(product.getPrice());
        return productVO;
    }

    @Override
    public ProductVO findProduct(String name) {
        Product product = productRepository.findByPname(name)
                .orElseThrow((()-> new HosException(ErrorInfo.PRODUCT_NOT_FOUND.getMessage())));
        ProductVO productVO = new ProductVO();
        productVO.setPname(product.getPname());
        productVO.setPlace(product.getPlace());
        productVO.setRemark(product.getRemark());
        productVO.setSpec(product.getSpec());
        productVO.setPrice(product.getPrice());
        return productVO;
    }
}
