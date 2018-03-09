package com.system.auth.dao;

import com.system.auth.model.Platform;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface PlatformMapper {
    @Delete({
        "delete from t_platform",
        "where platform_id = #{platformId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer platformId);

    @Insert({
        "insert into t_platform (platform_id, platform_name, ",
        "secret_key, system_id, ",
        "platform_domain, description, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{platformId,jdbcType=INTEGER}, #{platformName,jdbcType=VARCHAR}, ",
        "#{secretKey,jdbcType=VARCHAR}, #{systemId,jdbcType=INTEGER}, ",
        "#{platformDomain,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=INTEGER}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(Platform record);

    @InsertProvider(type=PlatformSqlProvider.class, method="insertSelective")
    int insertSelective(Platform record);

    @Select({
        "select",
        "platform_id, platform_name, secret_key, system_id, platform_domain, description, ",
        "create_user_id, update_time, create_time",
        "from t_platform",
        "where platform_id = #{platformId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
        @Result(column="secret_key", property="secretKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="system_id", property="systemId", jdbcType=JdbcType.INTEGER),
        @Result(column="platform_domain", property="platformDomain", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    Platform selectByPrimaryKey(Integer platformId);

    @UpdateProvider(type=PlatformSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Platform record);

    @Update({
        "update t_platform",
        "set platform_name = #{platformName,jdbcType=VARCHAR},",
          "secret_key = #{secretKey,jdbcType=VARCHAR},",
          "system_id = #{systemId,jdbcType=INTEGER},",
          "platform_domain = #{platformDomain,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where platform_id = #{platformId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Platform record);
}