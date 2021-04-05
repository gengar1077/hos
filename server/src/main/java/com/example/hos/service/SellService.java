package com.example.hos.service;

import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.SellVO;

/**
 * 销售操作
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/3/17
 */
public interface SellService {

    ResultResponse selectByPage(Integer pageNum, Integer pageSize, String name);

    ResultResponse addSell(SellVO sellVO);

    ResultResponse delSell(String sellId);
}
