package com.system.auth.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.system.auth.model.CustomArch;

public class CustomArchSqlProvider {

    public String insertSelective(CustomArch record) {
        BEGIN();
        INSERT_INTO("t_custom_arch");
        
        if (record.getCustomArchId() != null) {
            VALUES("custom_arch_id", "#{customArchId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomArchName() != null) {
            VALUES("custom_arch_name", "#{customArchName,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomArchFId() != null) {
            VALUES("custom_arch_f_id", "#{customArchFId,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatformId() != null) {
            VALUES("platform_id", "#{platformId,jdbcType=VARCHAR}");
        }
        
        if (record.getFlag() != null) {
            VALUES("flag", "#{flag,jdbcType=INTEGER}");
        }
        
        if (record.getCustomArchLevel() != null) {
            VALUES("custom_arch_level", "#{customArchLevel,jdbcType=INTEGER}");
        }
        
        if (record.getCustomArchFTree() != null) {
            VALUES("custom_arch_f_tree", "#{customArchFTree,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(CustomArch record) {
        BEGIN();
        UPDATE("t_custom_arch");
        
        if (record.getCustomArchName() != null) {
            SET("custom_arch_name = #{customArchName,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomArchFId() != null) {
            SET("custom_arch_f_id = #{customArchFId,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatformId() != null) {
            SET("platform_id = #{platformId,jdbcType=VARCHAR}");
        }
        
        if (record.getFlag() != null) {
            SET("flag = #{flag,jdbcType=INTEGER}");
        }
        
        if (record.getCustomArchLevel() != null) {
            SET("custom_arch_level = #{customArchLevel,jdbcType=INTEGER}");
        }
        
        if (record.getCustomArchFTree() != null) {
            SET("custom_arch_f_tree = #{customArchFTree,jdbcType=VARCHAR}");
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
        
        WHERE("custom_arch_id = #{customArchId,jdbcType=VARCHAR}");
        
        return SQL();
    }
}