package com.system.auth.sql;

import com.system.auth.model.UserGroup;
import com.system.auth.model.provider.UserGroupBulkInsert;
import com.system.auth.model.request.UserGroupListCondition;
import com.system.auth.model.request.UserGroupsBulk;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class UserGroupSQL {
    public String insertByUserGroupList(UserGroupBulkInsert record) {
        String sql = "insert into t_user_group(user_id, group_id, create_user_id, update_time, create_time) VALUES";
        for (String item : record.getGroupIds()) {
            sql += "('" + record.getUserId() + "','" + item + "','" + record.getCreateUserId() + "'," + Long.toString(System.currentTimeMillis()) + "," + Long.toString(System.currentTimeMillis()) + "),";
        }

        return sql.substring(0, sql.length() - 1);
    }

    public String selectBySelective(UserGroupListCondition condition) {
        return new SQL() {{
            SELECT("a.user_id, c.user_name, a.group_id, d.group_name, b.platform_id, b.platform_name, d.description, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_user_group a, t_platform b, t_user c, t_group d, t_user e");
            if (null != condition.getGroupId()) {
                WHERE("a.group_id = #{groupId, jdbcType=VARCHAR}");
            }
            if (null != condition.getGroupName()) {
                condition.setGroupName("%" + condition.getGroupName() + "%");
                WHERE("d.group_name like #{groupName, jdbcType=VARCHAR}");
            }
            if (null != condition.getPlatformId()) {
                WHERE("a.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = e.user_id");
            WHERE("d.platform_id = b.platform_id");
            WHERE("a.user_id = c.user_id");
            WHERE("a.group_id = d.group_id");

            ORDER_BY("c.user_name");
            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String deleteByUserGroupIds(UserGroupsBulk user_groups) {
        String groupIds = "";
        if (null != user_groups.getGroupIds() && 0 < user_groups.getGroupIds().size()) {
            for (String item: user_groups.getGroupIds()) {
                groupIds += "," + item;
            }
        }

        final String condition_group = "group_id in (" + groupIds + ")";
        return new SQL() {{
            DELETE_FROM("t_user_group");
            WHERE("user_id = #{userId}");
            if (null != user_groups.getGroupIds() && 0 < user_groups.getGroupIds().size()) {
                WHERE(condition_group);
            }
        }}.toString();
    }
}
