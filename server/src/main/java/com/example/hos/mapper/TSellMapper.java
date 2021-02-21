package com.example.hos.mapper;

import com.example.hos.model.TSell;
import com.example.hos.model.TSellExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSellMapper {
    long countByExample(TSellExample example);

    int deleteByExample(TSellExample example);

    int deleteByPrimaryKey(Long sellId);

    int insert(TSell record);

    int insertSelective(TSell record);

    List<TSell> selectByExample(TSellExample example);

    TSell selectByPrimaryKey(Long sellId);

    int updateByExampleSelective(@Param("record") TSell record, @Param("example") TSellExample example);

    int updateByExample(@Param("record") TSell record, @Param("example") TSellExample example);

    int updateByPrimaryKeySelective(TSell record);

    int updateByPrimaryKey(TSell record);
}