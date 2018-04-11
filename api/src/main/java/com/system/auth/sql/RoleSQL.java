package com.system.auth.sql;

import com.system.auth.model.Role;
import com.system.auth.model.request.RoleBulk;
import com.system.auth.model.request.RoleListCondition;
import org.apache.ibatis.jdbc.SQL;

public class RoleSQL {
    public String insertSelective(Role record) {
        return new SQL() {{
            INSERT_INTO("t_role");
            if (null != record.getRoleId()) {
                VALUES("role_id", "#{roleId,jdbcType=VARCHAR}");
            }

            if (null != record.getRoleName()) {
                VALUES("role_name", "#{roleName,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateUserId()) {
                VALUES("create_user_id", "#{createUserId,jdbcType=VARCHAR}");
            }

            if (null != record.getPlatformId()) {
                VALUES("platform_id", "#{platformId,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(Role record) {
        return new SQL() {{
            UPDATE("t_role");
            if (null != record.getRoleName()) {
                SET("role_name = #{roleName,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateUserId()) {
                SET("create_user_id = #{createUserId,jdbcType=VARCHAR}");
            }

            if (null != record.getPlatformId()) {
                SET("platform_id = #{platformId,jdbcType=VARCHAR}");
            }

            if (null != record.getDescription()) {
                SET("description = #{description,jdbcType=VARCHAR}");
            }

            if (null != record.getUpdateTime()) {
                SET("update_time = #{updateTime,jdbcType=BIGINT}");
            }
            WHERE("role_id = #{roleId,jdbcType=VARCHAR}");
        }}.toString();
    }

    public String selectBySelective(RoleListCondition condition) {
        return new SQL() {{
            SELECT("a.role_id, a.role_name, a.platform_id, b.platform_name, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_role a, t_platform b, t_user c");
            if (null != condition.getRoleId()) {
                WHERE("a.role_id = #{roleId, jdbcType=VARCHAR}");
            }
            if (null != condition.getRoleName()) {
                final String roleNameCond = "a.role_name like '%" + condition.getRoleName() + "%'";
                WHERE(roleNameCond);
            }
            if (null != condition.getPlatformId()) {
                WHERE("a.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = c.user_id");
            WHERE("a.platform_id = b.platform_id");

            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String selectByPlatformIdAndRoleName(RoleListCondition condition) {
        return new SQL() {{
            SELECT("a.role_id, a.role_name, a.platform_id, b.platform_name, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_role a, t_platform b, t_user c");
            if (null != condition.getRoleName()) {
                WHERE("a.role_name = #{roleName, jdbcType=VARCHAR}");
            }
            if (null != condition.getPlatformId()) {
                WHERE("a.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = c.user_id");
            WHERE("a.platform_id = b.platform_id");

            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String selectByRoleIds(RoleBulk roles) {
        String strRoleIds = "";
        for (String item: roles.getRoleIds()) {
            strRoleIds += ",'" + item + "'";
        }
        final String cond = strRoleIds.length() > 0? strRoleIds.substring(1): "''";
        return "select role_id from t_role where role_id in (" + cond + ")";
    }

    public String deleteByRoleIds(RoleBulk roles) {
        String strRoleIds = "";
        for (String item: roles.getRoleIds()) {
            strRoleIds += ",'" + item + "'";
        }

        final String cond = strRoleIds.length() > 0? strRoleIds.substring(1): "''";

        return "delete from t_role where role_id in (" + cond + ")";
    }
}
