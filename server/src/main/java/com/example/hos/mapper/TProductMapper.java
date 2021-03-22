package com.example.hos.mapper;

import com.example.hos.model.TProduct;
import com.example.hos.model.TProductExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface TProductMapper {
    long countByExample(TProductExample example);

    int deleteByExample(TProductExample example);

    int deleteByPrimaryKey(String pId);

    int insert(TProduct record);

    int insertSelective(TProduct record);

    List<TProduct> selectByExample(TProductExample example);

    TProduct selectByPrimaryKey(String pId);

    int updateByExampleSelective(@Param("record") TProduct record, @Param("example") TProductExample example);

    int updateByExample(@Param("record") TProduct record, @Param("example") TProductExample example);

    int updateByPrimaryKeySelective(TProduct record);

    int updateByPrimaryKey(TProduct record);

    @Select("select * from t_product where p_name=#{pName}")
    Optional<TProduct> selectByName(String pName);
}