package com.system.auth.dao;

import com.system.auth.model.AdminUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AdminUserMapper {
    @Delete({
        "delete from t_admin_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userId);

    @Insert({
        "insert into t_admin_user (user_id, system_id, ",
        "flag, description, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{userId,jdbcType=INTEGER}, #{systemId,jdbcType=INTEGER}, ",
        "#{flag,jdbcType=TINYINT}, #{description,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=INTEGER}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(AdminUser record);

    @InsertProvider(type=AdminUserSqlProvider.class, method="insertSelective")
    int insertSelective(AdminUser record);

    @Select({
        "select",
        "user_id, system_id, flag, description, create_user_id, update_time, create_time",
        "from t_admin_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="system_id", property="systemId", jdbcType=JdbcType.INTEGER),
        @Result(column="flag", property="flag", jdbcType=JdbcType.TINYINT),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    AdminUser selectByPrimaryKey(Integer userId);

    @UpdateProvider(type=AdminUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AdminUser record);

    @Update({
        "update t_admin_user",
        "set system_id = #{systemId,jdbcType=INTEGER},",
          "flag = #{flag,jdbcType=TINYINT},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AdminUser record);
}