package com.system.auth.dao;

import com.system.auth.model.System;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface SystemMapper {
    @Delete({
        "delete from t_system",
        "where system_id = #{systemId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer systemId);

    @Insert({
        "insert into t_system (system_id, system_name, ",
        "description, create_user_id, ",
        "update_time, create_time)",
        "values (#{systemId,jdbcType=INTEGER}, #{systemName,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{createUserId,jdbcType=INTEGER}, ",
        "#{updateTime,jdbcType=BIGINT}, #{createTime,jdbcType=BIGINT})"
    })
    int insert(System record);

    @InsertProvider(type=SystemSqlProvider.class, method="insertSelective")
    int insertSelective(System record);

    @Select({
        "select",
        "system_id, system_name, description, create_user_id, update_time, create_time",
        "from t_system",
        "where system_id = #{systemId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="system_id", property="systemId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="system_name", property="systemName", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    System selectByPrimaryKey(Integer systemId);

    @UpdateProvider(type=SystemSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(System record);

    @Update({
        "update t_system",
        "set system_name = #{systemName,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where system_id = #{systemId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(System record);
}