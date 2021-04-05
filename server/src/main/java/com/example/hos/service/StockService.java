package com.example.hos.service;

import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.StockVO;

/**
 * 库存操作
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/3/8
 */
public interface StockService {
    /**
     * 药品入库
     * @param pid
     * @param num
     * @return
     * @author changwei.zhong
     * @date 2021/3/8
     **/
    ResultResponse inStock(String pid, int num);

    /**
     * 分页查询库存
     * @author changwei.zhong
     * @date 2021/3/8
     * @param pageNum
     * @param pageSize
     * @return
     **/
    ResultResponse selectByPage(Integer pageNum, Integer pageSize, String name);

    /**
     * 新增库存单
     * @param stockVO
     * @return
     * @author changwei.zhong
     * @date 2021/3/8
     **/
    ResultResponse addStock(StockVO stockVO);
}
