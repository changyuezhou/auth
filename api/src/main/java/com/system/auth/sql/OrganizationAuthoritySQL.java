package com.system.auth.sql;

import com.system.auth.model.provider.OrganizationAuthorityBulkInsert;
import com.system.auth.model.request.OrganizationAuthorityBulk;
import com.system.auth.model.request.OrganizationAuthorityListCondition;
import org.apache.ibatis.jdbc.SQL;

public class OrganizationAuthoritySQL {
    public String insertByOrganizationAuthorityList(OrganizationAuthorityBulkInsert record) {
        String sql = "insert into t_organization_authority(organization_id, auth_id, create_user_id, update_time, create_time) VALUES";
        for (String item : record.getAuthIds()) {
            sql += "('" + record.getOrganizationId() + "','" + item + "','" + record.getCreateUserId() + "'," + Long.toString(System.currentTimeMillis()) + "," + Long.toString(System.currentTimeMillis()) + "),";
        }

        return sql.substring(0, sql.length() - 1);
    }

    public String selectBySelective(OrganizationAuthorityListCondition condition) {
        return new SQL() {{
            SELECT("a.organization_id, b.organization_name, a.auth_id, c.auth_name, d.platform_id, d.platform_name, c.description, a.create_user_id, e.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_organization_authority a, t_organization b, t_authority c, t_platform d, t_user e");
            if (null != condition.getOrganizationId()) {
                WHERE("a.organization_id = #{organizationId, jdbcType=VARCHAR}");
            }
            if (null != condition.getOrganizationName()) {
                final String condStr = "b.organization_name like '%" + condition.getOrganizationName() + "%'";
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
            WHERE("a.organization_id = b.organization_id");

            ORDER_BY("b.organization_name");
            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String deleteByOrganizationAuthorityIds(OrganizationAuthorityBulk organization_auths) {
        String authIds = "";
        if (null != organization_auths.getAuthIds() && 0 < organization_auths.getAuthIds().size()) {
            for (String item: organization_auths.getAuthIds()) {
                authIds += ",'" + item + "'";
            }
        }

        final String condition_organization = authIds.length() > 0? "auth_id in (" + authIds.substring(1) + ")": "";
        return new SQL() {{
            DELETE_FROM("t_organization_authority");
            WHERE("organization_id = #{organizationId}");
            if (null != organization_auths.getAuthIds() && 0 < organization_auths.getAuthIds().size()) {
                WHERE(condition_organization);
            }
        }}.toString();
    }
}
