package com.system.auth.dao;

import com.system.auth.model.UserRole;
import com.system.auth.model.UserRoleKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface UserRoleMapper {
    @Delete({
        "delete from t_user_role",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and role_id = #{roleId,jdbcType=VARCHAR}",
          "and platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(UserRoleKey key);

    @Insert({
        "insert into t_user_role (user_id, role_id, ",
        "platform_id, create_user_id, ",
        "update_time, create_time)",
        "values (#{userId,jdbcType=VARCHAR}, #{roleId,jdbcType=VARCHAR}, ",
        "#{platformId,jdbcType=VARCHAR}, #{createUserId,jdbcType=VARCHAR}, ",
        "#{updateTime,jdbcType=BIGINT}, #{createTime,jdbcType=BIGINT})"
    })
    int insert(UserRole record);

    @InsertProvider(type=UserRoleSqlProvider.class, method="insertSelective")
    int insertSelective(UserRole record);

    @Select({
        "select",
        "user_id, role_id, platform_id, create_user_id, update_time, create_time",
        "from t_user_role",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and role_id = #{roleId,jdbcType=VARCHAR}",
          "and platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    UserRole selectByPrimaryKey(UserRoleKey key);

    @UpdateProvider(type=UserRoleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserRole record);

    @Update({
        "update t_user_role",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and role_id = #{roleId,jdbcType=VARCHAR}",
          "and platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(UserRole record);
}