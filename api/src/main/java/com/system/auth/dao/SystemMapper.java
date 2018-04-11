package com.system.auth.dao;

import com.system.auth.model.System;
import com.system.auth.model.ext.SystemView;
import com.system.auth.model.request.SystemBulk;
import com.system.auth.model.request.SystemListCondition;
import com.system.auth.sql.SystemSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface SystemMapper {
    @Delete({
        "delete from t_system",
        "where system_id = #{systemId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String systemId);

    @DeleteProvider(type = SystemSQL.class, method = "deleteBySystemIds")
    int deleteBySystemIds(SystemBulk systems);

    @Insert({
        "insert into t_system (system_id, system_name, ",
        "description, create_user_id, ",
        "update_time, create_time)",
        "values (#{systemId,jdbcType=VARCHAR}, #{systemName,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{createUserId,jdbcType=VARCHAR}, ",
        "#{updateTime,jdbcType=BIGINT}, #{createTime,jdbcType=BIGINT})"
    })
    int insert(System record);

    @InsertProvider(type=SystemSQL.class, method="insertSelective")
    int insertSelective(System record);

    @Select({
        "select",
        "a.system_id, a.system_name, a.description, a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time",
        "from t_system a, t_user b",
        "where a.system_id = #{systemId,jdbcType=VARCHAR}",
            " and a.create_user_id= b.user_id"
    })
    @Results({
        @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="system_name", property="systemName", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    SystemView selectByPrimaryKey(String systemId);

    @Select({
            "select",
            "a.system_id, a.system_name, a.description, a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time",
            "from t_system a, t_user b",
            "where a.system_name = #{systemName,jdbcType=VARCHAR}",
            " and a.create_user_id= b.user_id"
    })
    @Results({
            @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="system_name", property="systemName", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    SystemView selectBySystemName(String systemName);

    @SelectProvider(type=SystemSQL.class, method="selectBySelective")
    @Results({
            @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="system_name", property="systemName", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<SystemView> selectBySelective(SystemListCondition condition);

    @UpdateProvider(type=SystemSQL.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(System record);

    @Update({
        "update t_system",
        "set system_name = #{systemName,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where system_id = #{systemId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(System record);
}