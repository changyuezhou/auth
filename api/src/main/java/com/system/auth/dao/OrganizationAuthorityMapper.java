package com.system.auth.dao;

import com.system.auth.model.OrganizationAuthority;
import com.system.auth.model.OrganizationAuthorityKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface OrganizationAuthorityMapper {
    @Delete({
        "delete from t_organization_authority",
        "where organization_id = #{organizationId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(OrganizationAuthorityKey key);

    @Insert({
        "insert into t_organization_authority (organization_id, auth_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{organizationId,jdbcType=VARCHAR}, #{authId,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(OrganizationAuthority record);

    @InsertProvider(type=OrganizationAuthoritySqlProvider.class, method="insertSelective")
    int insertSelective(OrganizationAuthority record);

    @Select({
        "select",
        "organization_id, auth_id, create_user_id, update_time, create_time",
        "from t_organization_authority",
        "where organization_id = #{organizationId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    OrganizationAuthority selectByPrimaryKey(OrganizationAuthorityKey key);

    @UpdateProvider(type=OrganizationAuthoritySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(OrganizationAuthority record);

    @Update({
        "update t_organization_authority",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where organization_id = #{organizationId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(OrganizationAuthority record);
}