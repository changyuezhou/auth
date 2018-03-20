package com.system.auth.sql;

import com.system.auth.model.System;
import com.system.auth.model.request.SystemBulk;
import com.system.auth.model.request.SystemListCondition;
import org.apache.ibatis.jdbc.SQL;

public class SystemSQL {
    public String insertSelective(System record) {
        return new SQL() {{
            INSERT_INTO("t_system");
            if (null != record.getSystemId()) {
                VALUES("system_id", "#{systemId,jdbcType=VARCHAR}");
            }

            if (null != record.getSystemName()) {
                VALUES("system_name", "#{systemName,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateUserId()) {
                VALUES("create_user_id", "#{createUserId,jdbcType=VARCHAR}");
            }

            if (null != record.getDescription()) {
                VALUES("description", "#{description,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateTime()) {
                VALUES("create_time", "#{createTime,jdbcType=BIGINT}");
            }

            if (null != record.getUpdateTime()) {
                VALUES("update_time", "#{updateTime,jdbcType=BIGINT}");
            }
        }}.toString();
    }

    public String updateByPrimaryKeySelective(System record) {
        return new SQL() {{
            UPDATE("t_system");
            if (null != record.getSystemName()) {
                SET("system_name = #{systemName,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateUserId()) {
                SET("create_user_id = #{createUserId,jdbcType=VARCHAR}");
            }

            if (null != record.getDescription()) {
                SET("description = #{description,jdbcType=VARCHAR}");
            }

            if (null != record.getUpdateTime()) {
                SET("update_time = #{updateTime,jdbcType=BIGINT}");
            }
            WHERE("system_id = #{systemId,jdbcType=VARCHAR}");
        }}.toString();
    }

    public String selectBySelective(SystemListCondition condition) {
        return new SQL() {{
            SELECT("a.system_id, a.system_name, a.description, a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_system a, t_user b");
            if (null != condition.getSystemId()) {
                WHERE("a.system_id = #{systemId, jdbcType=VARCHAR}");
            }
            if (null != condition.getSystemName()) {
                condition.setSystemName("%" + condition.getSystemName() + "%");
                WHERE("a.system_name like #{systemName, jdbcType=VARCHAR}");
            }
            if (null != condition.getCreateUserName()) {
                WHERE("b.user_name = #{createUserName, jdbcType=VARCHAR}");
            }
            if (null != condition.getCreateUserId()) {
                WHERE("a.create_user_id = #{createUserId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = b.user_id");

            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String deleteBySystemIds(SystemBulk systems) {
        String strSystemIds = "";
        for (String item: systems.getSystemIds()) {
            strSystemIds += ",'" + item + "'";
        }
        return "delete from t_system where group_id in (" + strSystemIds.substring(1) + ")";
    }
}
