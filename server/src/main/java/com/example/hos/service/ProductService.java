package com.example.hos.service;

import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.ResultResponse;

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
    ResultResponse addProduct(ProductVO productVO);

    /**
     * 删除药品
     * @param pid
     * @return
     * @author changwei.zhong
     * @date 2021/3/2
     **/
    ResultResponse delProduct(String pid);

    /**
     * 修改药品
     * @param productVO
     * @return
     * @author changwei.zhong
     * @date 2021/3/2
     **/
    ResultResponse updateProduct(ProductVO productVO);

    /**
     * 分页查询
     * @author changwei.zhong
     * @date 2021/3/8
     * @param pageNum
     * @param pageSize
     * @return
     **/
    ResultResponse selectByPage(Integer pageNum, Integer pageSize);
}
