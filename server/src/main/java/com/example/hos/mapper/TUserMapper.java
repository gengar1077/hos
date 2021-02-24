package com.example.hos.mapper;

import com.example.hos.model.TUser;
import com.example.hos.model.TUserExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TUserMapper {
    long countByExample(TUserExample example);

    int deleteByExample(TUserExample example);

    int insert(TUser record);

    int insertSelective(TUser record);

    List<TUser> selectByExample(TUserExample example);

    int updateByExampleSelective(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByExample(@Param("record") TUser record, @Param("example") TUserExample example);

    @Select("select * from t_user")
    List<TUser> selectAll();

    TUser selectById(Long id);

    @Select("select * from t_user where username=#{username}")
    TUser selectByName(String username);

    @Delete("delete from t_user where id=#{id}")
    int delete(Long id);
}