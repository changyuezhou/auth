package com.system.auth.dao;

import com.system.auth.model.Organization;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface OrganizationMapper {
    @Delete({
        "delete from t_organization",
        "where organization_id = #{organizationId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String organizationId);

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

    @InsertProvider(type=OrganizationSqlProvider.class, method="insertSelective")
    int insertSelective(Organization record);

    @Select({
        "select",
        "organization_id, organization_name, organization_f_id, platform_id, organization_level, ",
        "organization_f_tree, description, create_user_id, update_time, create_time",
        "from t_organization",
        "where organization_id = #{organizationId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="organization_f_id", property="organizationFId", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
        @Result(column="organization_level", property="organizationLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="organization_f_tree", property="organizationFTree", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    Organization selectByPrimaryKey(String organizationId);

    @UpdateProvider(type=OrganizationSqlProvider.class, method="updateByPrimaryKeySelective")
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