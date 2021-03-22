package com.example.hos.mapper;

import com.example.hos.model.TUser;
import com.example.hos.model.TUserExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface TUserMapper {
    long countByExample(TUserExample example);

    int deleteByExample(TUserExample example);

    int deleteByPrimaryKey(String uId);

    int insert(TUser record);

    int insertSelective(TUser record);

    List<TUser> selectByExample(TUserExample example);

    TUser selectByPrimaryKey(String uId);

    int updateByExampleSelective(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByExample(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    @Select("select * from t_user")
    List<TUser> selectAll();

    @Select("select * from t_user where id=#{id}")
    TUser selectById(String id);

    @Select("select * from t_user where username=#{username}")
    Optional<TUser> selectByName(String username);

    @Delete("delete from t_user where id=#{id}")
    int delete(String id);
}