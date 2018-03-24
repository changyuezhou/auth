package com.system.auth.sql;

import com.system.auth.model.provider.UserOrganizationBulkInsert;
import com.system.auth.model.request.UserOrganizationBulk;
import com.system.auth.model.request.UserOrganizationListCondition;
import org.apache.ibatis.jdbc.SQL;

public class UserOrganizationSQL {
    public String insertByUserOrganizationList(UserOrganizationBulkInsert record) {
        String sql = "insert into t_user_organization(user_id, organization_id, create_user_id, update_time, create_time) VALUES";
        for (String item : record.getOrganizationIds()) {
            sql += "('" + record.getUserId() + "','" + item + "','" + record.getCreateUserId() + "'," + Long.toString(System.currentTimeMillis()) + "," + Long.toString(System.currentTimeMillis()) + "),";
        }

        return sql.substring(0, sql.length() - 1);
    }

    public String selectBySelective(UserOrganizationListCondition condition) {
        return new SQL() {{
            SELECT("a.user_id, c.user_name, a.organization_id, d.organization_name, b.platform_id, b.platform_name, d.description, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_user_organization a, t_platform b, t_user c, t_organization d, t_user e");
            if (null != condition.getOrganizationId()) {
                WHERE("a.organization_id = #{organizationId, jdbcType=VARCHAR}");
            }

            if (null != condition.getOrganizationName()) {
                condition.setOrganizationName("%" + condition.getOrganizationName() + "%");
                WHERE("d.organization_name like #{organizationName, jdbcType=VARCHAR}");
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
            WHERE("a.organization_id = d.organization_id");

            ORDER_BY("c.user_name");
            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String deleteByUserOrganizationIds(UserOrganizationBulk user_organizations) {
        String organizationIds = "";
        if (null != user_organizations.getOrganizationIds() && 0 < user_organizations.getOrganizationIds().size()) {
            for (String item: user_organizations.getOrganizationIds()) {
                organizationIds += ",'" + item + "'";
            }
        }

        final String condition_organization = organizationIds.length() > 0? "organization_id in (" + organizationIds.substring(1) + ")" : "";
        return new SQL() {{
            DELETE_FROM("t_user_organization");
            WHERE("user_id = #{userId}");
            if (null != user_organizations.getOrganizationIds() && 0 < user_organizations.getOrganizationIds().size()) {
                WHERE(condition_organization);
            }
        }}.toString();
    }
}
