package com.system.auth.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.system.auth.model.Organization;

public class OrganizationSqlProvider {

    public String insertSelective(Organization record) {
        BEGIN();
        INSERT_INTO("t_organization");
        
        if (record.getOrganizationId() != null) {
            VALUES("organization_id", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            VALUES("organization_name", "#{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationFId() != null) {
            VALUES("organization_f_id", "#{organizationFId,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatformId() != null) {
            VALUES("platform_id", "#{platformId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationLevel() != null) {
            VALUES("organization_level", "#{organizationLevel,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationFTree() != null) {
            VALUES("organization_f_tree", "#{organizationFTree,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(Organization record) {
        BEGIN();
        UPDATE("t_organization");
        
        if (record.getOrganizationName() != null) {
            SET("organization_name = #{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationFId() != null) {
            SET("organization_f_id = #{organizationFId,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatformId() != null) {
            SET("platform_id = #{platformId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationLevel() != null) {
            SET("organization_level = #{organizationLevel,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationFTree() != null) {
            SET("organization_f_tree = #{organizationFTree,jdbcType=VARCHAR}");
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
        
        WHERE("organization_id = #{organizationId,jdbcType=VARCHAR}");
        
        return SQL();
    }
}