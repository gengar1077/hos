package com.example.hos.service;

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
     * @param productVO
     * @return
     * @author changwei.zhong
     * @date 2021/3/2
     **/
    void addProduct(ProductVO productVO);

    /**
     * 删除药品
     * @param pid
     * @return
     * @author changwei.zhong
     * @date 2021/3/2
     **/
    void delProduct(String pid);

    /**
     * 修改药品
     * @param productVO
     * @return
     * @author changwei.zhong
     * @date 2021/3/2
     **/
    void updateProduct(ProductVO productVO);

    /**
     * 分页查询
     * @author changwei.zhong
     * @date 2021/3/8
     * @param pageNum
     * @param pageSize
     * @return
     **/
    PageInfo<ProductVO> selectByPage(Integer pageNum, Integer pageSize, String name);

    /**
     * 查询药品
     * @param name
     * @author changwei.zhong
     * @date
     **/
    ProductVO findProduct(String name);
}
