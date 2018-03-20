package com.system.auth.dao;

import com.system.auth.model.RoleAuthority;
import com.system.auth.model.RoleAuthorityKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface RoleAuthorityMapper {
    @Delete({
        "delete from t_role_authority",
        "where role_id = #{roleId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}",
          "and platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(RoleAuthorityKey key);

    @Insert({
        "insert into t_role_authority (role_id, auth_id, ",
        "platform_id, create_user_id, ",
        "update_time, create_time)",
        "values (#{roleId,jdbcType=VARCHAR}, #{authId,jdbcType=VARCHAR}, ",
        "#{platformId,jdbcType=VARCHAR}, #{createUserId,jdbcType=VARCHAR}, ",
        "#{updateTime,jdbcType=BIGINT}, #{createTime,jdbcType=BIGINT})"
    })
    int insert(RoleAuthority record);

    @InsertProvider(type=RoleAuthoritySqlProvider.class, method="insertSelective")
    int insertSelective(RoleAuthority record);

    @Select({
        "select",
        "role_id, auth_id, platform_id, create_user_id, update_time, create_time",
        "from t_role_authority",
        "where role_id = #{roleId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}",
          "and platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    RoleAuthority selectByPrimaryKey(RoleAuthorityKey key);

    @UpdateProvider(type=RoleAuthoritySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RoleAuthority record);

    @Update({
        "update t_role_authority",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where role_id = #{roleId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}",
          "and platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(RoleAuthority record);
}