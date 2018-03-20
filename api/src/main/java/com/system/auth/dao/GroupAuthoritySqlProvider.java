package com.system.auth.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.system.auth.model.GroupAuthority;

public class GroupAuthoritySqlProvider {

    public String insertSelective(GroupAuthority record) {
        BEGIN();
        INSERT_INTO("t_group_authority");
        
        if (record.getGroupId() != null) {
            VALUES("group_id", "#{groupId,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthId() != null) {
            VALUES("auth_id", "#{authId,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatformId() != null) {
            VALUES("platform_id", "#{platformId,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(GroupAuthority record) {
        BEGIN();
        UPDATE("t_group_authority");
        
        if (record.getCreateUserId() != null) {
            SET("create_user_id = #{createUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=BIGINT}");
        }
        
        WHERE("group_id = #{groupId,jdbcType=VARCHAR}");
        WHERE("auth_id = #{authId,jdbcType=VARCHAR}");
        WHERE("platform_id = #{platformId,jdbcType=VARCHAR}");
        
        return SQL();
    }
}