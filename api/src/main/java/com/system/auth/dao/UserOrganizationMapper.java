package com.system.auth.dao;

import com.system.auth.model.UserOrganization;
import com.system.auth.model.ext.UserOrganizationView;
import com.system.auth.model.provider.UserOrganizationBulkInsert;
import com.system.auth.model.request.UserOrganizationBulk;
import com.system.auth.model.request.UserOrganizationKey;
import com.system.auth.model.request.UserOrganizationListCondition;
import com.system.auth.sql.UserOrganizationSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserOrganizationMapper {
    @Delete({
        "delete from t_user_organization",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and organization_id = #{organizationId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(UserOrganizationKey key);

    @DeleteProvider(type=UserOrganizationSQL.class, method = "deleteByUserOrganizationIds")
    int deleteByUserOrganizationIds(UserOrganizationBulk user_organizations);

    @Insert({
        "insert into t_user_organization (user_id, organization_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{userId,jdbcType=VARCHAR}, #{organizationId,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(UserOrganization record);

    @InsertProvider(type=UserOrganizationSQL.class, method="insertByUserOrganizationList")
    int insertByUserOrganizationList(UserOrganizationBulkInsert record);

    @Select({
            "select",
            "a.user_id, b.user_name, a.organization_id, c.organization_name, c.platform_id, d.platform_name, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time",
            "from t_user_organization a, t_user b, t_organization c, t_platform d, t_user e",
            "where a.user_id = #{userId,jdbcType=VARCHAR}",
            "and a.organization_id = #{organizationId,jdbcType=VARCHAR})",
            " and a.user_id=b.user_id and a.organization_id=c.organization_id and c.platform_id=d.platform_id and a.create_user_id=e.user_id"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    UserOrganizationView selectByPrimaryKey(UserOrganizationKey key);

    @SelectProvider(type=UserOrganizationSQL.class, method="selectBySelective")
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<UserOrganizationView> selectBySelective(UserOrganizationListCondition condition);

    @Update({
        "update t_user_organization",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and organization_id = #{organizationId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(UserOrganization record);
}