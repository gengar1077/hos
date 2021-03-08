package com.example.hos.service;

import com.example.hos.model.TProduct;
import com.example.hos.model.vo.ProductVO;
import com.github.pagehelper.PageInfo;

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

    /**
     * 修改药品
     * @param productVO
     * @return
     * @author changwei.zhong
     * @date 2021/3/2
     **/
    String updateProduct(ProductVO productVO);

    /**
     * 分页查询
     * @author changwei.zhong
     * @date 2021/3/8
     * @param pageNum
     * @param pageSize
     * @return
     **/
    PageInfo<TProduct> selectByPage(Integer pageNum, Integer pageSize);
}
