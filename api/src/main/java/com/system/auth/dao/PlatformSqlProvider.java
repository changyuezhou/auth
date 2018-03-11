package com.system.auth.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.system.auth.model.Platform;

public class PlatformSqlProvider {

    public String insertSelective(Platform record) {
        BEGIN();
        INSERT_INTO("t_platform");
        
        if (record.getPlatformId() != null) {
            VALUES("platform_id", "#{platformId,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatformName() != null) {
            VALUES("platform_name", "#{platformName,jdbcType=VARCHAR}");
        }
        
        if (record.getSecretKey() != null) {
            VALUES("secret_key", "#{secretKey,jdbcType=VARCHAR}");
        }
        
        if (record.getSystemId() != null) {
            VALUES("system_id", "#{systemId,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatformDomain() != null) {
            VALUES("platform_domain", "#{platformDomain,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            VALUES("description", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateUserId() != null) {
            VALUES("create_user_id", "#{createUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=BIGINT}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(Platform record) {
        BEGIN();
        UPDATE("t_platform");
        
        if (record.getPlatformName() != null) {
            SET("platform_name = #{platformName,jdbcType=VARCHAR}");
        }
        
        if (record.getSecretKey() != null) {
            SET("secret_key = #{secretKey,jdbcType=VARCHAR}");
        }
        
        if (record.getSystemId() != null) {
            SET("system_id = #{systemId,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatformDomain() != null) {
            SET("platform_domain = #{platformDomain,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            SET("description = #{description,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateUserId() != null) {
            SET("create_user_id = #{createUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=BIGINT}");
        }
        
        WHERE("platform_id = #{platformId,jdbcType=VARCHAR}");
        
        return SQL();
    }
}