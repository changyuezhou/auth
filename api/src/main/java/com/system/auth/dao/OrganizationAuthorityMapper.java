package com.system.auth.dao;

import com.system.auth.model.OrganizationAuthority;
import com.system.auth.model.ext.OrganizationAuthorityView;
import com.system.auth.model.provider.OrganizationAuthorityBulkInsert;
import com.system.auth.model.request.OrganizationAuthorityBulk;
import com.system.auth.model.request.OrganizationAuthorityKey;
import com.system.auth.model.request.OrganizationAuthorityListCondition;
import com.system.auth.sql.OrganizationAuthoritySQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface OrganizationAuthorityMapper {
    @Delete({
        "delete from t_organization_authority",
        "where organization_id = #{organizationId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(OrganizationAuthorityKey key);

    @DeleteProvider(type=OrganizationAuthoritySQL.class, method = "deleteByOrganizationAuthorityIds")
    int deleteByOrganizationAuthorityIds(OrganizationAuthorityBulk organization_auths);

    @Insert({
        "insert into t_organization_authority (organization_id, auth_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{organizationId,jdbcType=VARCHAR}, #{authId,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(OrganizationAuthority record);

    @InsertProvider(type=OrganizationAuthoritySQL.class, method="insertByOrganizationAuthorityList")
    int insertByOrganizationAuthorityList(OrganizationAuthorityBulkInsert record);

    @Select({
            "select",
            "a.organization_id, b.organization_name, a.auth_id, c.auth_name, d.platform_id, d.platform_name, c.description, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time",
            "from t_organization_authority a, t_organization b, t_authority c, t_platform d, t_user e",
            "where a.organization_id = #{organizationId,jdbcType=VARCHAR}",
            "and a.auth_id = #{authId,jdbcType=VARCHAR}",
            "and a.create_user_id = e.user_id and d.platform_id = b.platform_id and a.auth_id = b.auth_id and a.organization_id = b.organization_id"
    })
    @Results({
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    OrganizationAuthorityView selectByPrimaryKey(OrganizationAuthorityKey key);

    @SelectProvider(type = OrganizationAuthoritySQL.class, method = "selectBySelective")
    @Results({
            @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<OrganizationAuthorityView> selectBySelective(OrganizationAuthorityListCondition condition);

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