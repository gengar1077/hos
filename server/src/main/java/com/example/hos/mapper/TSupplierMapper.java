package com.example.hos.mapper;

import com.example.hos.model.TSupplier;
import com.example.hos.model.TSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSupplierMapper {
    long countByExample(TSupplierExample example);

    int deleteByExample(TSupplierExample example);

    int deleteByPrimaryKey(Long sId);

    int insert(TSupplier record);

    int insertSelective(TSupplier record);

    List<TSupplier> selectByExample(TSupplierExample example);

    TSupplier selectByPrimaryKey(Long sId);

    int updateByExampleSelective(@Param("record") TSupplier record, @Param("example") TSupplierExample example);

    int updateByExample(@Param("record") TSupplier record, @Param("example") TSupplierExample example);

    int updateByPrimaryKeySelective(TSupplier record);

    int updateByPrimaryKey(TSupplier record);
}