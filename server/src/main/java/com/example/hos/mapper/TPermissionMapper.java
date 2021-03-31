package com.example.hos.mapper;

import com.example.hos.model.TPermission;
import com.example.hos.model.TPermissionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface TPermissionMapper {
    long countByExample(TPermissionExample example);

    int deleteByExample(TPermissionExample example);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    List<TPermission> selectByExample(TPermissionExample example);

    int updateByExampleSelective(@Param("record") TPermission record, @Param("example") TPermissionExample example);

    int updateByExample(@Param("record") TPermission record, @Param("example") TPermissionExample example);

    @Select("select * from t_permission where user_id=#{} and status='1")
    Optional<List<TPermission>> findAllByUserIdAndStatus(String uid);
}