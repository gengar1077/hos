package com.example.hos.service.impl;

import com.example.hos.mapper.TProductMapper;
import com.example.hos.model.TProduct;
import com.example.hos.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
