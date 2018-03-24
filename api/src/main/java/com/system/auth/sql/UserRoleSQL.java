package com.system.auth.sql;

import com.system.auth.model.provider.UserRoleBulkInsert;
import com.system.auth.model.request.UserRoleBulk;
import com.system.auth.model.request.UserRoleListCondition;
import org.apache.ibatis.jdbc.SQL;

public class UserRoleSQL {
    public String insertByUserRoleList(UserRoleBulkInsert record) {
        String sql = "insert into t_user_role(user_id, role_id, create_user_id, update_time, create_time) VALUES";
        for (String item : record.getRoleIds()) {
            sql += "('" + record.getUserId() + "','" + item + "','" + record.getCreateUserId() + "'," + Long.toString(System.currentTimeMillis()) + "," + Long.toString(System.currentTimeMillis()) + "),";
        }

        return sql.substring(0, sql.length() - 1);
    }

    public String selectBySelective(UserRoleListCondition condition) {
        return new SQL() {{
            SELECT("a.user_id, c.user_name, a.role_id, d.role_name, b.platform_id, b.platform_name, d.description, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_user_role a, t_platform b, t_user c, t_role d, t_user e");
            if (null != condition.getRoleId()) {
                WHERE("a.role_id = #{roleId, jdbcType=VARCHAR}");
            }
            if (null != condition.getRoleName()) {
                final String condStr = "d.role_name like '%" + condition.getRoleName() + "%'";
                WHERE(condStr);
            }
            if (null != condition.getUserId()) {
                WHERE("a.user_id = #{userId, jdbcType=VARCHAR}");
            }
            if (null != condition.getUserName()) {
                final String condStr = "c.user_name like '%" + condition.getUserName() + "%'";
                WHERE(condStr);
            }
            if (null != condition.getPlatformId()) {
                WHERE("b.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = e.user_id");
            WHERE("d.platform_id = b.platform_id");
            WHERE("a.user_id = c.user_id");
            WHERE("a.role_id = d.role_id");

            ORDER_BY("c.user_name");
            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String deleteByUserRoleIds(UserRoleBulk user_roles) {
        String roleIds = "";
        if (null != user_roles.getRoleIds() && 0 < user_roles.getRoleIds().size()) {
            for (String item: user_roles.getRoleIds()) {
                roleIds += ",'" + item + "'";
            }
        }

        final String condition_group = roleIds.length() > 0? "role_id in (" + roleIds.substring(1) + ")" : "";
        return new SQL() {{
            DELETE_FROM("t_user_role");
            WHERE("user_id = #{userId}");
            if (null != user_roles.getRoleIds() && 0 < user_roles.getRoleIds().size()) {
                WHERE(condition_group);
            }
        }}.toString();
    }
}
