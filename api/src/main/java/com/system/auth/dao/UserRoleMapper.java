package com.system.auth.dao;

import com.system.auth.model.UserRole;
import com.system.auth.model.ext.UserRoleView;
import com.system.auth.model.provider.UserRoleBulkInsert;
import com.system.auth.model.request.UserRoleBulk;
import com.system.auth.model.request.UserRoleKey;
import com.system.auth.model.request.UserRoleListCondition;
import com.system.auth.sql.UserRoleSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserRoleMapper {
    @Delete({
        "delete from t_user_role",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and role_id = #{roleId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(UserRoleKey key);

    @DeleteProvider(type=UserRoleSQL.class, method = "deleteByUserRoleIds")
    int deleteByUserRoleIds(UserRoleBulk user_roles);

    @Insert({
        "insert into t_user_role (user_id, role_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{userId,jdbcType=VARCHAR}, #{roleId,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(UserRole record);

    @InsertProvider(type=UserRoleSQL.class, method="insertByUserRoleList")
    int insertByUserRoleList(UserRoleBulkInsert record);

    @Select({
        "select",
        "a.user_id, b.user_name, a.role_id, c.role_name, c.platform_id, d.platform_name, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time",
        "from t_user_role a, t_user b, t_group c, t_platform d, t_user e",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and role_id = #{roleId,jdbcType=VARCHAR}",
            " and a.user_id=b.user_id and a.role_id=c.role_id and c.platform_id=d.platform_id and a.create_user_id=e.user_id"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    UserRoleView selectByPrimaryKey(UserRoleKey key);

    @SelectProvider(type = UserRoleSQL.class, method = "selectBySelective")
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="role_id", property="roleId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<UserRoleView> selectBySelective(UserRoleListCondition condition);

    @Update({
        "update t_user_role",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and role_id = #{roleId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(UserRole record);
}