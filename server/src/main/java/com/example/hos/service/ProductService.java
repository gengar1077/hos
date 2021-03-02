package com.example.hos.service;

import com.example.hos.model.TProduct;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/3/2
 */
public interface ProductService {

    /**
     * 新增药品
     * @param product
     * @return
     * @author changwei.zhong
     * @date 2021/3/2
     **/
    String addProduct(TProduct product);

    /**
     * 删除药品
     * @param pid
     * @return
     * @author changwei.zhong
     * @date 2021/3/2
     **/
    String delProduct(Long pid);
}
