package com.system.auth.dao;

import com.system.auth.model.Organization;
import com.system.auth.model.ext.OrganizationView;
import com.system.auth.model.request.OrganizationBulk;
import com.system.auth.model.request.OrganizationListCondition;
import com.system.auth.sql.OrganizationSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface OrganizationMapper {
    @Delete({
        "delete from t_organization",
        "where organization_id = #{organizationId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String organizationId);

    @DeleteProvider(type = OrganizationSQL.class, method = "deleteByOrganizationIds")
    int deleteByOrganizationIds(OrganizationBulk orgs);

    @Insert({
        "insert into t_organization (organization_id, organization_name, ",
        "organization_f_id, platform_id, ",
        "organization_level, organization_f_tree, ",
        "description, create_user_id, ",
        "update_time, create_time)",
        "values (#{organizationId,jdbcType=VARCHAR}, #{organizationName,jdbcType=VARCHAR}, ",
        "#{organizationFId,jdbcType=VARCHAR}, #{platformId,jdbcType=VARCHAR}, ",
        "#{organizationLevel,jdbcType=INTEGER}, #{organizationFTree,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{createUserId,jdbcType=VARCHAR}, ",
        "#{updateTime,jdbcType=BIGINT}, #{createTime,jdbcType=BIGINT})"
    })
    int insert(Organization record);

    @InsertProvider(type=OrganizationSQL.class, method="insertSelective")
    int insertSelective(Organization record);

    @Select({
        "select",
        "a.organization_id, a.organization_name, a.organization_f_id, a.platform_id, b.platform_name, a.organization_level, ",
        "a.organization_f_tree, a.description, a.create_user_id, a.update_time, a.create_time",
        "from t_organization a, t_platform b",
        "where a.organization_id = #{organizationId,jdbcType=VARCHAR}",
            "and a.platform_id = b.platform_id"
    })
    @Results({
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="organization_f_id", property="organizationFId", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
        @Result(column="organization_level", property="organizationLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="organization_f_tree", property="organizationFTree", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    OrganizationView selectByPrimaryKey(String organizationId);

    @SelectProvider(type = OrganizationSQL.class, method = "selectBySelective")
    @Results({
            @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR),
            @Result(column="organization_f_id", property="organizationFId", jdbcType=JdbcType.VARCHAR),
            @Result(column="organization_f_name", property="organizationFName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="organization_level", property="organizationLevel", jdbcType=JdbcType.INTEGER),
            @Result(column="organization_f_tree", property="organizationFTree", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<OrganizationView> selectBySelective(OrganizationListCondition condition);

    @SelectProvider(type = OrganizationSQL.class, method = "selectByPlatformIdAndOrganizationName")
    @Results({
            @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR),
            @Result(column="organization_f_id", property="organizationFId", jdbcType=JdbcType.VARCHAR),
            @Result(column="organization_f_name", property="organizationFName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="organization_level", property="organizationLevel", jdbcType=JdbcType.INTEGER),
            @Result(column="organization_f_tree", property="organizationFTree", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    OrganizationView selectByPlatformIdAndOrganizationName(OrganizationListCondition condition);

    @SelectProvider(type= OrganizationSQL.class, method = "selectByOrganizationIds")
    List<String> selectByOrganizationIds(OrganizationBulk orgs);

    @UpdateProvider(type=OrganizationSQL.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Organization record);

    @Update({
        "update t_organization",
        "set organization_name = #{organizationName,jdbcType=VARCHAR},",
          "organization_f_id = #{organizationFId,jdbcType=VARCHAR},",
          "platform_id = #{platformId,jdbcType=VARCHAR},",
          "organization_level = #{organizationLevel,jdbcType=INTEGER},",
          "organization_f_tree = #{organizationFTree,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where organization_id = #{organizationId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Organization record);
}