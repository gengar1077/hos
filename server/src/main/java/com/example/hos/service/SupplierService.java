package com.example.hos.service;

import com.example.hos.model.entity.Supplier;
import com.example.hos.model.vo.SupplierVO;
import com.github.pagehelper.PageInfo;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/4/5
 */
public interface SupplierService {

    /**
     * 新增供应商
     * @param supplierVO
     * @return
     * @author changwei.zhong
     * @date 2021/3/8
     **/
    void addSupplier(SupplierVO supplierVO);

    /**
     * 分页查询供应商
     * @author changwei.zhong
     * @date 2021/3/8
     * @param pageNum
     * @param pageSize
     * @return
     **/
    PageInfo<SupplierVO> selectByPage(Integer pageNum, Integer pageSize, String name);

    /**
     * @Desc 删除供应商
     * @Author 吃面龙
     * @Date 2021/4/5
     * @Params sid
     */
    void delSupplier(String sid);
}
