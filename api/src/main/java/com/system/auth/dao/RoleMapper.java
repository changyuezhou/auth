package com.system.auth.dao;

import com.system.auth.model.Role;
import com.system.auth.model.ext.RoleView;
import com.system.auth.model.request.RoleBulk;
import com.system.auth.model.request.RoleListCondition;
import com.system.auth.sql.RoleSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface RoleMapper {
    @Delete({
        "delete from t_role",
        "where role_id = #{roleId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String roleId);

    @DeleteProvider(type = RoleSQL.class, method = "deleteByRoleIds")
    int deleteByRoleIds(RoleBulk roles);


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

    @InsertProvider(type=RoleSQL.class, method="insertSelective")
    int insertSelective(Role record);

    @Select({
        "select",
        "a.role_id, a.role_name, a.platform_id, b.platform_name, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time",
        "from t_role a, t_platform b, t_user c",
        "where role_id = #{roleId,jdbcType=VARCHAR}",
            "and a.platform_id = b.platform_id and a.create_user_id = c.user_id"
    })
    @Results({
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
    RoleView selectByPrimaryKey(String roleId);

    @SelectProvider(type = RoleSQL.class, method = "selectBySelective")
    @Results({
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
    List<RoleView> selectBySelective(RoleListCondition condition);


    @SelectProvider(type=RoleSQL.class, method = "selectByRoleIds")
    List<String> selectByRoleIds(RoleBulk roles);

    @SelectProvider(type = RoleSQL.class, method = "selectByPlatformIdAndRoleName")
    @Results({
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
    RoleView selectByPlatformIdAndRoleName(RoleListCondition condition);

    @UpdateProvider(type=RoleSQL.class, method="updateByPrimaryKeySelective")
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