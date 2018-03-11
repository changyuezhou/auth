package com.system.auth.dao;

import com.system.auth.model.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface RoleMapper {
    @Delete({
        "delete from t_role",
        "where role_id = #{roleId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String roleId);

    @Insert({
        "insert into t_role (role_id, role_name, ",
        "platform_id, description, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{roleId,jdbcType=VARCHAR}, #{roleName,jdbcType=VARCHAR}, ",
        "#{platformId,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(Role record);

    @InsertProvider(type=RoleSqlProvider.class, method="insertSelective")
    int insertSelective(Role record);

    @Select({
        "select",
        "role_id, role_name, platform_id, description, create_user_id, update_time, create_time",
        "from t_role",
        "where role_id = #{roleId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    Role selectByPrimaryKey(String roleId);

    @UpdateProvider(type=RoleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Role record);

    @Update({
        "update t_role",
        "set role_name = #{roleName,jdbcType=VARCHAR},",
          "platform_id = #{platformId,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where role_id = #{roleId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Role record);
}