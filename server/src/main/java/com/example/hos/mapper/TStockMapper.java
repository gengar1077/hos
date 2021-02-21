package com.example.hos.mapper;

import com.example.hos.model.TStock;
import com.example.hos.model.TStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TStockMapper {
    long countByExample(TStockExample example);

    int deleteByExample(TStockExample example);

    int deleteByPrimaryKey(Long stockId);

    int insert(TStock record);

    int insertSelective(TStock record);

    List<TStock> selectByExample(TStockExample example);

    TStock selectByPrimaryKey(Long stockId);

    int updateByExampleSelective(@Param("record") TStock record, @Param("example") TStockExample example);

    int updateByExample(@Param("record") TStock record, @Param("example") TStockExample example);

    int updateByPrimaryKeySelective(TStock record);

    int updateByPrimaryKey(TStock record);
}