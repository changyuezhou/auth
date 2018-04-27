package com.system.auth.dao;

import com.system.auth.model.Platform;
import com.system.auth.model.ext.AuthorityView;
import com.system.auth.model.ext.PlatformView;
import com.system.auth.model.request.AuthorityListCondition;
import com.system.auth.model.request.PlatformBulk;
import com.system.auth.model.request.PlatformListCondition;
import com.system.auth.sql.AuthoritySQL;
import com.system.auth.sql.PlatformSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface PlatformMapper {
    @Delete({
        "delete from t_platform",
        "where platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String platformId);

    @DeleteProvider(type = PlatformSQL.class, method = "deleteByPlatformIds")
    int deleteByPlatformIds(PlatformBulk platforms);

    @Insert({
        "insert into t_platform (platform_id, platform_name, ",
        "secret_key, system_id, ",
        "platform_domain, description, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{platformId,jdbcType=VARCHAR}, #{platformName,jdbcType=VARCHAR}, ",
        "#{secretKey,jdbcType=VARCHAR}, #{systemId,jdbcType=VARCHAR}, ",
        "#{platformDomain,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(Platform record);

    @InsertProvider(type=PlatformSQL.class, method="insertSelective")
    int insertSelective(Platform record);

    @Select({
        "select",
        "a.platform_id, a.platform_name, a.secret_key, a.system_id, b.system_name, a.platform_domain, a.description, ",
        "a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time",
        "from t_platform a, t_system b, t_user c",
        "where a.platform_id = #{platformId,jdbcType=VARCHAR}",
            "and a.system_id = b.system_id and a.create_user_id = c.user_id"
    })
    @Results({
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
        @Result(column="secret_key", property="secretKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR),
        @Result(column="system_name", property="systemName", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_domain", property="platformDomain", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    PlatformView selectByPrimaryKey(String platformId);

    @Select({
            "select",
            "a.platform_id, a.platform_name, a.secret_key, a.system_id, b.system_name, a.platform_domain, a.description, ",
            "a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time",
            "from t_platform a, t_system b, t_user c",
            "where a.platform_name = #{platformName,jdbcType=VARCHAR}",
            "and a.system_id = b.system_id and a.create_user_id = c.user_id"
    })
    @Results({
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="secret_key", property="secretKey", jdbcType=JdbcType.VARCHAR),
            @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR),
            @Result(column="system_name", property="systemName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_domain", property="platformDomain", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    PlatformView selectByPlatformName(String platformName);

    @SelectProvider(type=PlatformSQL.class, method="selectBySelective")
    @Results({
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="secret_key", property="secretKey", jdbcType=JdbcType.VARCHAR),
            @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR),
            @Result(column="system_name", property="systemName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_domain", property="platformDomain", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<PlatformView> selectBySelective(PlatformListCondition condition);

    @SelectProvider(type = PlatformSQL.class, method = "selectAuthListByCondition")
    @Results({
            @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_f_id", property="authFId", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_f_name", property="authFName", jdbcType=JdbcType.VARCHAR),
            @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_level", property="authLevel", jdbcType=JdbcType.INTEGER),
            @Result(column="auth_f_tree", property="authFTree", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<AuthorityView> selectAuthListByCondition(PlatformListCondition condition);

    @UpdateProvider(type=PlatformSQL.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Platform record);

    @Update({
        "update t_platform",
        "set platform_name = #{platformName,jdbcType=VARCHAR},",
          "secret_key = #{secretKey,jdbcType=VARCHAR},",
          "system_id = #{systemId,jdbcType=VARCHAR},",
          "platform_domain = #{platformDomain,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where platform_id = #{platformId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Platform record);
}