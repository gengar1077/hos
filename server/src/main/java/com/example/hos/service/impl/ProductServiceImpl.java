package com.example.hos.service.impl;

import com.example.hos.mapper.TProductMapper;
import com.example.hos.model.TProduct;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private TProductMapper productMapper;

    @Override
    public String addProduct(TProduct product) {
        if (Objects.isNull(product)){
            System.out.println("请填写正确的信息!");
            return "index";
        }
        if (productMapper.selectByPrimaryKey(product.getpId())!=null){
            System.out.println("药品已存在!");
            return "index";
        }
        productMapper.insertSelective(product);
        return "index";
    }

    @Override
    public String delProduct(Long pid) {
        if (productMapper.selectByPrimaryKey(pid)!=null){
            System.out.println("请填写正确的药品序号!");
            return "index";
        }
        productMapper.deleteByPrimaryKey(pid);
        return null;
    }

    @Override
    public String updateProduct(ProductVO productVO) {
        TProduct product = null;
        if (StringUtils.isNotBlank(productVO.getPName())) {
            product = productMapper.selectByName(productVO.getPName()).orElse(null);
        }
        if (Objects.isNull(product)){
            return "药品不存在";
        }
        if (!product.getpName().equals(product.getpName())){
            if (productMapper.selectByName(productVO.getPName()).isPresent()){
                return "该药品名已经存在，修改失败！";
            }
        }
        product.setpId(productVO.getPid());
        product.setpName(productVO.getPName());
        productMapper.updateByPrimaryKey(product);
        return null;
    }

    @Override
    public PageInfo<TProduct> selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        final List<TProduct> products = productMapper.selectByExample(null);
        final PageInfo<TProduct> productPage = new PageInfo<>(products);
        return productPage;
    }
}
