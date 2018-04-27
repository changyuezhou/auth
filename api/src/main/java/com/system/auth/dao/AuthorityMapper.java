package com.system.auth.dao;

import com.system.auth.model.Authority;
import com.system.auth.model.ext.AuthorityView;
import com.system.auth.model.request.AuthorityBulk;
import com.system.auth.model.request.AuthorityListCondition;
import com.system.auth.sql.AuthoritySQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface AuthorityMapper {
    /*
    @Delete({
        "delete from t_authority",
        "where auth_id = #{authId,jdbcType=VARCHAR} or auth_f_tree like #{authId,jdbcType=VARCHAR}"
    })
    */
    @DeleteProvider(type = AuthoritySQL.class, method = "deleteByAuthorityId")
    int deleteByPrimaryKey(String authId);

    @DeleteProvider(type = AuthoritySQL.class, method = "deleteByAuthorityIds")
    int deleteByAuthorityIds(AuthorityBulk auths);

    @Insert({
        "insert into t_authority (auth_id, auth_name, url, ",
        "auth_f_id, system_id, ",
        "auth_level, auth_f_tree, ",
        "description, create_user_id, ",
        "update_time, create_time)",
        "values (#{authId,jdbcType=VARCHAR}, #{authName,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, ",
        "#{authFId,jdbcType=VARCHAR}, #{systemId,jdbcType=VARCHAR}, ",
        "#{authLevel,jdbcType=INTEGER}, #{authFTree,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{createUserId,jdbcType=VARCHAR}, ",
        "#{updateTime,jdbcType=BIGINT}, #{createTime,jdbcType=BIGINT})"
    })
    int insert(Authority record);

    @InsertProvider(type=AuthoritySQL.class, method="insertSelective")
    int insertSelective(Authority record);

    @Select({
        "select",
        "a.auth_id, a.auth_name, a.url, a.auth_f_id, a.system_id, c.system_name, a.auth_level, a.auth_f_tree, b.auth_name as auth_f_name, a.description, ",
        "a.create_user_id, d.user_name as create_user_name, a.update_time, a.create_time",
        "from t_authority a, t_authority b, t_system c, t_user d",
        "where a.auth_id = #{authId,jdbcType=VARCHAR}",
            "and (a.auth_f_id = b.auth_id)",
            "and a.system_id = c.system_id",
            "and a.create_user_id = d.user_id",
            " group by a.auth_id"
    })
    @Results({
        @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="auth_f_id", property="authFId", jdbcType=JdbcType.VARCHAR),
        @Result(column="auth_f_name", property="authFName", jdbcType=JdbcType.VARCHAR),
        @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR),
        @Result(column="system_name", property="systemName", jdbcType=JdbcType.VARCHAR),
        @Result(column="auth_level", property="authLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="auth_f_tree", property="authFTree", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    AuthorityView selectByPrimaryKey(String authId);

    @SelectProvider(type = AuthoritySQL.class, method = "selectBySelective")
    @Results({
            @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_f_id", property="authFId", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_f_name", property="authFName", jdbcType=JdbcType.VARCHAR),
            @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR),
            @Result(column="system_name", property="systemName", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_level", property="authLevel", jdbcType=JdbcType.INTEGER),
            @Result(column="auth_f_tree", property="authFTree", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<AuthorityView> selectBySelective(AuthorityListCondition condition);

    @SelectProvider(type = AuthoritySQL.class, method = "selectBySystemIdAuthName")
    @Results({
            @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_f_id", property="authFId", jdbcType=JdbcType.VARCHAR),
            @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_level", property="authLevel", jdbcType=JdbcType.INTEGER),
            @Result(column="auth_f_tree", property="authFTree", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    AuthorityView selectBySystemIdAuthName(AuthorityListCondition condition);

    @SelectProvider(type= AuthoritySQL.class, method = "selectByAuthIds")
    List<String> selectByAuthIds(AuthorityBulk auths);

    @UpdateProvider(type=AuthoritySQL.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Authority record);

    @Update({
        "update t_authority",
        "set auth_name = #{authName,jdbcType=VARCHAR},",
            "url = #{url,jdbcType=VARCHAR},",
          "auth_f_id = #{authFId,jdbcType=VARCHAR},",
          "system_id = #{systemId,jdbcType=VARCHAR},",
          "auth_level = #{authLevel,jdbcType=INTEGER},",
          "auth_f_tree = #{authFTree,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Authority record);
}