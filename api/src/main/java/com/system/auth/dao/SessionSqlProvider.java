package com.system.auth.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.system.auth.model.Session;

public class SessionSqlProvider {

    public String insertSelective(Session record) {
        BEGIN();
        INSERT_INTO("t_session");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getPlatformId() != null) {
            VALUES("platform_id", "#{platformId,jdbcType=INTEGER}");
        }
        
        if (record.getAuthTokenCreateTime() != null) {
            VALUES("auth_token_create_time", "#{authTokenCreateTime,jdbcType=INTEGER}");
        }
        
        if (record.getAuthTokenExpire() != null) {
            VALUES("auth_token_expire", "#{authTokenExpire,jdbcType=INTEGER}");
        }
        
        if (record.getAuthToken() != null) {
            VALUES("auth_token", "#{authToken,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthTokenUsed() != null) {
            VALUES("auth_token_used", "#{authTokenUsed,jdbcType=BIT}");
        }
        
        if (record.getOpenId() != null) {
            VALUES("open_id", "#{openId,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            VALUES("user_id", "#{userId,jdbcType=INTEGER}");
        }
        
        if (record.getCode() != null) {
            VALUES("code", "#{code,jdbcType=VARCHAR}");
        }
        
        if (record.getCodeCreateTime() != null) {
            VALUES("code_create_time", "#{codeCreateTime,jdbcType=INTEGER}");
        }
        
        if (record.getCodeExpire() != null) {
            VALUES("code_expire", "#{codeExpire,jdbcType=INTEGER}");
        }
        
        if (record.getCodeUsed() != null) {
            VALUES("code_used", "#{codeUsed,jdbcType=BIT}");
        }
        
        if (record.getAccessToken() != null) {
            VALUES("access_token", "#{accessToken,jdbcType=VARCHAR}");
        }
        
        if (record.getAccessTokenCreateTime() != null) {
            VALUES("access_token_create_time", "#{accessTokenCreateTime,jdbcType=INTEGER}");
        }
        
        if (record.getAccessTokenExpire() != null) {
            VALUES("access_token_expire", "#{accessTokenExpire,jdbcType=INTEGER}");
        }
        
        if (record.getAccessTokenUsed() != null) {
            VALUES("access_token_used", "#{accessTokenUsed,jdbcType=BIT}");
        }
        
        if (record.getRefreshToken() != null) {
            VALUES("refresh_token", "#{refreshToken,jdbcType=VARCHAR}");
        }
        
        if (record.getRefreshTokenCreateTime() != null) {
            VALUES("refresh_token_create_time", "#{refreshTokenCreateTime,jdbcType=INTEGER}");
        }
        
        if (record.getRefreshTokenExpire() != null) {
            VALUES("refresh_token_expire", "#{refreshTokenExpire,jdbcType=INTEGER}");
        }
        
        if (record.getRefreshTokenUsed() != null) {
            VALUES("refresh_token_used", "#{refreshTokenUsed,jdbcType=BIT}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(Session record) {
        BEGIN();
        UPDATE("t_session");
        
        if (record.getPlatformId() != null) {
            SET("platform_id = #{platformId,jdbcType=INTEGER}");
        }
        
        if (record.getAuthTokenCreateTime() != null) {
            SET("auth_token_create_time = #{authTokenCreateTime,jdbcType=INTEGER}");
        }
        
        if (record.getAuthTokenExpire() != null) {
            SET("auth_token_expire = #{authTokenExpire,jdbcType=INTEGER}");
        }
        
        if (record.getAuthToken() != null) {
            SET("auth_token = #{authToken,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthTokenUsed() != null) {
            SET("auth_token_used = #{authTokenUsed,jdbcType=BIT}");
        }
        
        if (record.getOpenId() != null) {
            SET("open_id = #{openId,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            SET("user_id = #{userId,jdbcType=INTEGER}");
        }
        
        if (record.getCode() != null) {
            SET("code = #{code,jdbcType=VARCHAR}");
        }
        
        if (record.getCodeCreateTime() != null) {
            SET("code_create_time = #{codeCreateTime,jdbcType=INTEGER}");
        }
        
        if (record.getCodeExpire() != null) {
            SET("code_expire = #{codeExpire,jdbcType=INTEGER}");
        }
        
        if (record.getCodeUsed() != null) {
            SET("code_used = #{codeUsed,jdbcType=BIT}");
        }
        
        if (record.getAccessToken() != null) {
            SET("access_token = #{accessToken,jdbcType=VARCHAR}");
        }
        
        if (record.getAccessTokenCreateTime() != null) {
            SET("access_token_create_time = #{accessTokenCreateTime,jdbcType=INTEGER}");
        }
        
        if (record.getAccessTokenExpire() != null) {
            SET("access_token_expire = #{accessTokenExpire,jdbcType=INTEGER}");
        }
        
        if (record.getAccessTokenUsed() != null) {
            SET("access_token_used = #{accessTokenUsed,jdbcType=BIT}");
        }
        
        if (record.getRefreshToken() != null) {
            SET("refresh_token = #{refreshToken,jdbcType=VARCHAR}");
        }
        
        if (record.getRefreshTokenCreateTime() != null) {
            SET("refresh_token_create_time = #{refreshTokenCreateTime,jdbcType=INTEGER}");
        }
        
        if (record.getRefreshTokenExpire() != null) {
            SET("refresh_token_expire = #{refreshTokenExpire,jdbcType=INTEGER}");
        }
        
        if (record.getRefreshTokenUsed() != null) {
            SET("refresh_token_used = #{refreshTokenUsed,jdbcType=BIT}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }
}