package com.system.auth.sql;

import com.system.auth.model.provider.RoleAuthorityBulkInsert;
import com.system.auth.model.request.RoleAuthorityBulk;
import com.system.auth.model.request.RoleAuthorityListCondition;
import org.apache.ibatis.jdbc.SQL;

public class RoleAuthoritySQL {
    public String insertByRoleAuthorityList(RoleAuthorityBulkInsert record) {
        String sql = "insert into t_role_authority(role_id, auth_id, create_user_id, update_time, create_time) VALUES";
        for (String item : record.getAuthIds()) {
            sql += "('" + record.getRoleId() + "','" + item + "','" + record.getCreateUserId() + "'," + Long.toString(System.currentTimeMillis()) + "," + Long.toString(System.currentTimeMillis()) + "),";
        }

        return sql.substring(0, sql.length() - 1);
    }

    public String selectBySelective(RoleAuthorityListCondition condition) {
        return new SQL() {{
            SELECT("a.role_id, b.role_name, a.auth_id, c.auth_name, d.platform_id, d.platform_name, c.description, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_role_authority a, t_role b, t_authority c, t_platform d, t_user e");
            if (null != condition.getRoleId()) {
                WHERE("a.role_id = #{roleId, jdbcType=VARCHAR}");
            }
            if (null != condition.getRoleName()) {
                final String condStr = "b.role_name like '%" + condition.getRoleName() + "%'";
                WHERE(condStr);
            }
            if (null != condition.getAuthName()) {
                final String condStr = "c.auth_name like '%" + condition.getAuthName() + "%'";
                WHERE(condStr);
            }
            if (null != condition.getAuthId()) {
                WHERE("a.auth_id = #{authId, jdbcType=VARCHAR}");
            }
            if (null != condition.getPlatformId()) {
                WHERE("d.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = e.user_id");
            WHERE("d.platform_id = b.platform_id");
            WHERE("a.auth_id = c.auth_id");
            WHERE("a.role_id = b.role_id");

            ORDER_BY("b.role_name");
            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String deleteByRoleAuthorityIds(RoleAuthorityBulk role_auths) {
        String authIds = "";
        if (null != role_auths.getAuthIds() && 0 < role_auths.getAuthIds().size()) {
            for (String item: role_auths.getAuthIds()) {
                authIds += ",'" + item + "'";
            }
        }

        final String condition_group = authIds.length() > 0? "auth_id in (" + authIds.substring(1) + ")": "";
        return new SQL() {{
            DELETE_FROM("t_role_authority");
            WHERE("role_id = #{roleId}");
            if (null != role_auths.getAuthIds() && 0 < role_auths.getAuthIds().size()) {
                WHERE(condition_group);
            }
        }}.toString();
    }
}
