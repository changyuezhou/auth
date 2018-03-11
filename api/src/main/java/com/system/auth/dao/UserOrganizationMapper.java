package com.system.auth.dao;

import com.system.auth.model.UserOrganization;
import com.system.auth.model.UserOrganizationKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface UserOrganizationMapper {
    @Delete({
        "delete from t_user_organization",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and organization_id = #{organizationId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(UserOrganizationKey key);

    @Insert({
        "insert into t_user_organization (user_id, organization_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{userId,jdbcType=VARCHAR}, #{organizationId,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(UserOrganization record);

    @InsertProvider(type=UserOrganizationSqlProvider.class, method="insertSelective")
    int insertSelective(UserOrganization record);

    @Select({
        "select",
        "user_id, organization_id, create_user_id, update_time, create_time",
        "from t_user_organization",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and organization_id = #{organizationId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    UserOrganization selectByPrimaryKey(UserOrganizationKey key);

    @UpdateProvider(type=UserOrganizationSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserOrganization record);

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