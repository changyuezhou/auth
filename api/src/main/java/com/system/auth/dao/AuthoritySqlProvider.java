package com.system.auth.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.system.auth.model.Authority;

public class AuthoritySqlProvider {

    public String insertSelective(Authority record) {
        BEGIN();
        INSERT_INTO("t_authority");
        
        if (record.getAuthId() != null) {
            VALUES("auth_id", "#{authId,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthName() != null) {
            VALUES("auth_name", "#{authName,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthFId() != null) {
            VALUES("auth_f_id", "#{authFId,jdbcType=VARCHAR}");
        }
        
        if (record.getSystemId() != null) {
            VALUES("system_id", "#{systemId,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthLevel() != null) {
            VALUES("auth_level", "#{authLevel,jdbcType=INTEGER}");
        }
        
        if (record.getAuthFTree() != null) {
            VALUES("auth_f_tree", "#{authFTree,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(Authority record) {
        BEGIN();
        UPDATE("t_authority");
        
        if (record.getAuthName() != null) {
            SET("auth_name = #{authName,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthFId() != null) {
            SET("auth_f_id = #{authFId,jdbcType=VARCHAR}");
        }
        
        if (record.getSystemId() != null) {
            SET("system_id = #{systemId,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthLevel() != null) {
            SET("auth_level = #{authLevel,jdbcType=INTEGER}");
        }
        
        if (record.getAuthFTree() != null) {
            SET("auth_f_tree = #{authFTree,jdbcType=VARCHAR}");
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
        
        WHERE("auth_id = #{authId,jdbcType=VARCHAR}");
        
        return SQL();
    }
}