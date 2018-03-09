package com.system.auth.dao;

import com.system.auth.model.Session;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface SessionMapper {
    @Delete({
        "delete from t_session",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_session (id, platform_id, ",
        "auth_token_create_time, auth_token_expire, ",
        "auth_token, auth_token_used, ",
        "open_id, user_id, ",
        "code, code_create_time, ",
        "code_expire, code_used, ",
        "access_token, access_token_create_time, ",
        "access_token_expire, access_token_used, ",
        "refresh_token, refresh_token_create_time, ",
        "refresh_token_expire, refresh_token_used)",
        "values (#{id,jdbcType=INTEGER}, #{platformId,jdbcType=INTEGER}, ",
        "#{authTokenCreateTime,jdbcType=INTEGER}, #{authTokenExpire,jdbcType=INTEGER}, ",
        "#{authToken,jdbcType=VARCHAR}, #{authTokenUsed,jdbcType=BIT}, ",
        "#{openId,jdbcType=VARCHAR}, #{userId,jdbcType=INTEGER}, ",
        "#{code,jdbcType=VARCHAR}, #{codeCreateTime,jdbcType=INTEGER}, ",
        "#{codeExpire,jdbcType=INTEGER}, #{codeUsed,jdbcType=BIT}, ",
        "#{accessToken,jdbcType=VARCHAR}, #{accessTokenCreateTime,jdbcType=INTEGER}, ",
        "#{accessTokenExpire,jdbcType=INTEGER}, #{accessTokenUsed,jdbcType=BIT}, ",
        "#{refreshToken,jdbcType=VARCHAR}, #{refreshTokenCreateTime,jdbcType=INTEGER}, ",
        "#{refreshTokenExpire,jdbcType=INTEGER}, #{refreshTokenUsed,jdbcType=BIT})"
    })
    int insert(Session record);

    @InsertProvider(type=SessionSqlProvider.class, method="insertSelective")
    int insertSelective(Session record);

    @Select({
        "select",
        "id, platform_id, auth_token_create_time, auth_token_expire, auth_token, auth_token_used, ",
        "open_id, user_id, code, code_create_time, code_expire, code_used, access_token, ",
        "access_token_create_time, access_token_expire, access_token_used, refresh_token, ",
        "refresh_token_create_time, refresh_token_expire, refresh_token_used",
        "from t_session",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.INTEGER),
        @Result(column="auth_token_create_time", property="authTokenCreateTime", jdbcType=JdbcType.INTEGER),
        @Result(column="auth_token_expire", property="authTokenExpire", jdbcType=JdbcType.INTEGER),
        @Result(column="auth_token", property="authToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="auth_token_used", property="authTokenUsed", jdbcType=JdbcType.BIT),
        @Result(column="open_id", property="openId", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="code_create_time", property="codeCreateTime", jdbcType=JdbcType.INTEGER),
        @Result(column="code_expire", property="codeExpire", jdbcType=JdbcType.INTEGER),
        @Result(column="code_used", property="codeUsed", jdbcType=JdbcType.BIT),
        @Result(column="access_token", property="accessToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="access_token_create_time", property="accessTokenCreateTime", jdbcType=JdbcType.INTEGER),
        @Result(column="access_token_expire", property="accessTokenExpire", jdbcType=JdbcType.INTEGER),
        @Result(column="access_token_used", property="accessTokenUsed", jdbcType=JdbcType.BIT),
        @Result(column="refresh_token", property="refreshToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="refresh_token_create_time", property="refreshTokenCreateTime", jdbcType=JdbcType.INTEGER),
        @Result(column="refresh_token_expire", property="refreshTokenExpire", jdbcType=JdbcType.INTEGER),
        @Result(column="refresh_token_used", property="refreshTokenUsed", jdbcType=JdbcType.BIT)
    })
    Session selectByPrimaryKey(Integer id);

    @UpdateProvider(type=SessionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Session record);

    @Update({
        "update t_session",
        "set platform_id = #{platformId,jdbcType=INTEGER},",
          "auth_token_create_time = #{authTokenCreateTime,jdbcType=INTEGER},",
          "auth_token_expire = #{authTokenExpire,jdbcType=INTEGER},",
          "auth_token = #{authToken,jdbcType=VARCHAR},",
          "auth_token_used = #{authTokenUsed,jdbcType=BIT},",
          "open_id = #{openId,jdbcType=VARCHAR},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "code = #{code,jdbcType=VARCHAR},",
          "code_create_time = #{codeCreateTime,jdbcType=INTEGER},",
          "code_expire = #{codeExpire,jdbcType=INTEGER},",
          "code_used = #{codeUsed,jdbcType=BIT},",
          "access_token = #{accessToken,jdbcType=VARCHAR},",
          "access_token_create_time = #{accessTokenCreateTime,jdbcType=INTEGER},",
          "access_token_expire = #{accessTokenExpire,jdbcType=INTEGER},",
          "access_token_used = #{accessTokenUsed,jdbcType=BIT},",
          "refresh_token = #{refreshToken,jdbcType=VARCHAR},",
          "refresh_token_create_time = #{refreshTokenCreateTime,jdbcType=INTEGER},",
          "refresh_token_expire = #{refreshTokenExpire,jdbcType=INTEGER},",
          "refresh_token_used = #{refreshTokenUsed,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Session record);
}