package com.example.hos.service;

import com.example.hos.model.vo.StockVO;
import com.github.pagehelper.PageInfo;

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
    String inStock(String pid, int num);

    /**
     * 分页查询库存
     * @author changwei.zhong
     * @date 2021/3/8
     * @param pageNum
     * @param pageSize
     * @return
     **/
    PageInfo<StockVO> selectByPage(Integer pageNum, Integer pageSize);
}
