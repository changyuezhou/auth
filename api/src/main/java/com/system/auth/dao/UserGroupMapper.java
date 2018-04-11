package com.system.auth.dao;

import com.system.auth.model.UserGroup;
import com.system.auth.model.ext.UserGroupView;
import com.system.auth.model.provider.UserGroupBulkInsert;
import com.system.auth.model.request.UserGroupKey;
import com.system.auth.model.request.UserGroupListCondition;
import com.system.auth.model.request.UserGroupsBulk;
import com.system.auth.sql.UserGroupSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserGroupMapper {
    @Delete({
        "delete from t_user_group",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and group_id = #{groupId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(UserGroupKey key);

    @DeleteProvider(type=UserGroupSQL.class, method = "deleteByUserGroupIds")
    int deleteByUserGroupIds(UserGroupsBulk user_groups);


    @Insert({
        "insert into t_user_group (user_id, group_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{userId,jdbcType=VARCHAR}, #{groupId,jdbcType=ARRAY}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(UserGroup record);

    @InsertProvider(type=UserGroupSQL.class, method="insertByUserGroupList")
    int insertByUserGroupList(UserGroupBulkInsert record);

    @Select({
        "select",
        "a.user_id, b.user_name, a.group_id, c.group_name, c.platform_id, d.platform_name, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time",
        "from t_user_group a, t_user b, t_group c, t_platform d, t_user e",
        "where a.user_id = #{userId,jdbcType=VARCHAR}",
          "and a.group_id = #{groupId,jdbcType=VARCHAR})",
            " and a.user_id=b.user_id and a.group_id=c.group_id and c.platform_id=d.platform_id and a.create_user_id=e.user_id"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="group_id", property="groupId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="group_name", property="groupName", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    UserGroupView selectByPrimaryKey(UserGroupKey key);

    @SelectProvider(type=UserGroupSQL.class, method="selectBySelective")
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="group_id", property="groupId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="group_name", property="groupName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<UserGroupView> selectBySelective(UserGroupListCondition condition);

    @Update({
        "update t_user_group",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and group_id = #{groupId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(UserGroup record);
}