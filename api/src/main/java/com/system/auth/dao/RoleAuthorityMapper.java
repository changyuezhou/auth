package com.system.auth.dao;

import com.system.auth.model.RoleAuthority;
import com.system.auth.model.ext.RoleAuthorityView;
import com.system.auth.model.provider.RoleAuthorityBulkInsert;
import com.system.auth.model.request.RoleAuthorityBulk;
import com.system.auth.model.request.RoleAuthorityKey;
import com.system.auth.model.request.RoleAuthorityListCondition;
import com.system.auth.sql.RoleAuthoritySQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface RoleAuthorityMapper {
    @Delete({
        "delete from t_role_authority",
        "where role_id = #{roleId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(RoleAuthorityKey key);

    @DeleteProvider(type=RoleAuthoritySQL.class, method = "deleteByRoleAuthorityIds")
    int deleteByRoleAuthorityIds(RoleAuthorityBulk role_auths);

    @Insert({
        "insert into t_role_authority (role_id, auth_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{roleId,jdbcType=VARCHAR}, #{authId,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(RoleAuthority record);

    @InsertProvider(type=RoleAuthoritySQL.class, method="insertByRoleAuthorityList")
    int insertByRoleAuthorityList(RoleAuthorityBulkInsert record);

    @Select({
            "select",
            "a.role_id, b.role_name, a.auth_id, c.auth_name, d.platform_id, d.platform_name, c.description, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time",
            "from t_role_authority a, t_role b, t_authority c, t_platform d, t_user e",
            "where a.role_id = #{roleId,jdbcType=VARCHAR}",
            "and a.auth_id = #{authId,jdbcType=VARCHAR}",
            "and a.create_user_id = e.user_id and d.platform_id = b.platform_id and a.auth_id = b.auth_id and a.role_id = b.role_id"
    })
    @Results({
            @Result(column="role_id", property="roleId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    RoleAuthorityView selectByPrimaryKey(RoleAuthorityKey key);

    @SelectProvider(type = RoleAuthoritySQL.class, method = "selectBySelective")
    @Results({
            @Result(column="role_id", property="roleId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<RoleAuthorityView> selectBySelective(RoleAuthorityListCondition condition);

    @Update({
        "update t_role_authority",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where role_id = #{roleId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(RoleAuthority record);
}