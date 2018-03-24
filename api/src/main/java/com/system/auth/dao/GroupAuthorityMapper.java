package com.system.auth.dao;

import com.system.auth.model.GroupAuthority;
import com.system.auth.model.ext.GroupAuthorityView;
import com.system.auth.model.provider.GroupAuthorityBulkInsert;
import com.system.auth.model.request.GroupAuthorityBulk;
import com.system.auth.model.request.GroupAuthorityKey;
import com.system.auth.model.request.GroupAuthorityListCondition;
import com.system.auth.sql.GroupAuthoritySQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface GroupAuthorityMapper {
    @Delete({
        "delete from t_group_authority",
        "where group_id = #{groupId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(GroupAuthorityKey key);

    @DeleteProvider(type=GroupAuthoritySQL.class, method = "deleteByGroupAuthorityIds")
    int deleteByGroupAuthorityIds(GroupAuthorityBulk group_auths);

    @Insert({
        "insert into t_group_authority (group_id, auth_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{groupId,jdbcType=VARCHAR}, #{authId,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(GroupAuthority record);

    @InsertProvider(type=GroupAuthoritySQL.class, method="insertByGroupAuthorityList")
    int insertByGroupAuthorityList(GroupAuthorityBulkInsert record);

    @Select({
        "select",
        "a.group_id, b.group_name, a.auth_id, c.auth_name, d.platform_id, d.platform_name, c.description, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time",
        "from t_group_authority a, t_group b, t_authority c, t_platform d, t_user e",
        "where a.group_id = #{groupId,jdbcType=VARCHAR}",
          "and a.auth_id = #{authId,jdbcType=VARCHAR}",
            "and a.create_user_id = e.user_id and d.platform_id = b.platform_id and a.auth_id = b.auth_id and a.group_id = b.group_id"
    })
    @Results({
        @Result(column="group_id", property="groupId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="group_name", property="groupName", jdbcType=JdbcType.VARCHAR),
        @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    GroupAuthorityView selectByPrimaryKey(GroupAuthorityKey key);

    @SelectProvider(type = GroupAuthoritySQL.class, method = "selectBySelective")
    @Results({
            @Result(column="group_id", property="groupId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="group_name", property="groupName", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<GroupAuthorityView> selectBySelective(GroupAuthorityListCondition condition);

    @Update({
        "update t_group_authority",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where group_id = #{groupId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(GroupAuthority record);
}