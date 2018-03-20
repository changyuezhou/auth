package com.system.auth.dao;

import com.system.auth.model.GroupAuthority;
import com.system.auth.model.GroupAuthorityKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface GroupAuthorityMapper {
    @Delete({
        "delete from t_group_authority",
        "where group_id = #{groupId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}",
          "and platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(GroupAuthorityKey key);

    @Insert({
        "insert into t_group_authority (group_id, auth_id, ",
        "platform_id, create_user_id, ",
        "update_time, create_time)",
        "values (#{groupId,jdbcType=VARCHAR}, #{authId,jdbcType=VARCHAR}, ",
        "#{platformId,jdbcType=VARCHAR}, #{createUserId,jdbcType=VARCHAR}, ",
        "#{updateTime,jdbcType=BIGINT}, #{createTime,jdbcType=BIGINT})"
    })
    int insert(GroupAuthority record);

    @InsertProvider(type=GroupAuthoritySqlProvider.class, method="insertSelective")
    int insertSelective(GroupAuthority record);

    @Select({
        "select",
        "group_id, auth_id, platform_id, create_user_id, update_time, create_time",
        "from t_group_authority",
        "where group_id = #{groupId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}",
          "and platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="group_id", property="groupId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    GroupAuthority selectByPrimaryKey(GroupAuthorityKey key);

    @UpdateProvider(type=GroupAuthoritySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(GroupAuthority record);

    @Update({
        "update t_group_authority",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where group_id = #{groupId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}",
          "and platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(GroupAuthority record);
}