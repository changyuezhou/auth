package com.system.auth.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.system.auth.model.OrganizationAuthority;

public class OrganizationAuthoritySqlProvider {

    public String insertSelective(OrganizationAuthority record) {
        BEGIN();
        INSERT_INTO("t_organization_authority");
        
        if (record.getOrganizationId() != null) {
            VALUES("organization_id", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthId() != null) {
            VALUES("auth_id", "#{authId,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(OrganizationAuthority record) {
        BEGIN();
        UPDATE("t_organization_authority");
        
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
        WHERE("auth_id = #{authId,jdbcType=VARCHAR}");
        
        return SQL();
    }
}