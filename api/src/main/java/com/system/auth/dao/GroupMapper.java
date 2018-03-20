package com.system.auth.dao;

import com.system.auth.model.Group;
import com.system.auth.model.request.GroupBulk;
import com.system.auth.model.request.GroupListCondition;
import com.system.auth.model.ext.GroupView;
import com.system.auth.sql.GroupSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface GroupMapper {
    @Delete({
        "delete from t_group",
        "where group_id = #{groupId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String groupId);

    @DeleteProvider(type = GroupSQL.class, method = "deleteByGroupIds")
    int deleteByGroupIds(GroupBulk groups);

    @Insert({
        "insert into t_group (group_id, group_name, ",
        "platform_id, description, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{groupId,jdbcType=VARCHAR}, #{groupName,jdbcType=VARCHAR}, ",
        "#{platformId,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(Group record);

    @InsertProvider(type=GroupSQL.class, method="insertSelective")
    int insertSelective(Group record);

    @Select({
        "select",
        "a.group_id, a.group_name, a.platform_id, b.platform_name, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, ",
        "a.create_time",
        "from t_group a, t_platform b, t_user c",
        "where a.group_id = #{groupId,jdbcType=VARCHAR}",
            "and a.platform_id = b.platform_id and a.create_user_id = c.user_id"
    })
    @Results({
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
    GroupView selectByPrimaryKey(String groupId);

    @Select({
            "select",
            "a.group_id, a.group_name, a.platform_id, b.platform_name, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, ",
            "a.create_time",
            "from t_group a, t_platform b, t_user c",
            "where a.group_name = #{groupName,jdbcType=VARCHAR}",
            "and a.platform_id = b.platform_id and a.create_user_id = c.user_id"
    })
    @Results({
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
    List<GroupView> selectByGroupName(String groupName);

    @SelectProvider(type=GroupSQL.class, method = "selectByGroupIds")
    List<String> selectByGroupIds(GroupBulk groups);

    @SelectProvider(type=GroupSQL.class, method="selectByPlatformIdAndGroupName")
    @Results({
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
    GroupView selectByPlatformIdAndGroupName(GroupListCondition condition);

    @SelectProvider(type=GroupSQL.class, method="selectBySelective")
    @Results({
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
    List<GroupView> selectBySelective(GroupListCondition condition);

    @UpdateProvider(type=GroupSQL.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Group record);

    @Update({
        "update t_group",
        "set group_name = #{groupName,jdbcType=VARCHAR},",
          "platform_id = #{platformId,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where group_id = #{groupId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Group record);
}