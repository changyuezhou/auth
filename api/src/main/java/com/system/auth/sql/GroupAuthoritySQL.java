package com.system.auth.sql;

import com.system.auth.model.provider.GroupAuthorityBulkInsert;
import com.system.auth.model.request.GroupAuthorityBulk;
import com.system.auth.model.request.GroupAuthorityListCondition;
import org.apache.ibatis.jdbc.SQL;

public class GroupAuthoritySQL {
    public String insertByGroupAuthorityList(GroupAuthorityBulkInsert record) {
        String sql = "insert into t_group_authority(group_id, auth_id, create_user_id, update_time, create_time) VALUES";
        for (String item : record.getAuthIds()) {
            sql += "('" + record.getGroupId() + "','" + item + "','" + record.getCreateUserId() + "'," + Long.toString(System.currentTimeMillis()) + "," + Long.toString(System.currentTimeMillis()) + "),";
        }

        return sql.substring(0, sql.length() - 1);
    }

    public String selectBySelective(GroupAuthorityListCondition condition) {
        return new SQL() {{
            SELECT("a.group_id, b.group_name, a.auth_id, c.auth_name, d.platform_id, d.platform_name, c.description, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_group_authority a, t_group b, t_authority c, t_platform d, t_user e");
            if (null != condition.getGroupId()) {
                WHERE("a.group_id = #{groupId, jdbcType=VARCHAR}");
            }
            if (null != condition.getGroupName()) {
                final String condStr = "b.group_name like '%" + condition.getGroupName() + "%'";
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
            WHERE("a.group_id = b.group_id");

            ORDER_BY("b.group_name");
            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String deleteByGroupAuthorityIds(GroupAuthorityBulk group_auths) {
        String authIds = "";
        if (null != group_auths.getAuthIds() && 0 < group_auths.getAuthIds().size()) {
            for (String item: group_auths.getAuthIds()) {
                authIds += ",'" + item + "'";
            }
        }

        final String condition_group = authIds.length() > 0? "auth_id in (" + authIds.substring(1) + ")": "";
        return new SQL() {{
            DELETE_FROM("t_group_authority");
            WHERE("group_id = #{groupId}");
            if (null != group_auths.getAuthIds() && 0 < group_auths.getAuthIds().size()) {
                WHERE(condition_group);
            }
        }}.toString();
    }
}
