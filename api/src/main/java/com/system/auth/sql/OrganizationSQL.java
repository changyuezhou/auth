package com.system.auth.sql;

import com.system.auth.model.Organization;
import com.system.auth.model.request.OrganizationBulk;
import com.system.auth.model.request.OrganizationListCondition;
import org.apache.ibatis.jdbc.SQL;

public class OrganizationSQL {
    public String insertSelective(Organization record) {
        return new SQL() {{
            INSERT_INTO("t_organization");
            if (null != record.getOrganizationId()) {
                VALUES("organization_id", "#{organizationId,jdbcType=VARCHAR}");
            }

            if (null != record.getOrganizationName()) {
                VALUES("organization_name", "#{organizationName,jdbcType=VARCHAR}");
            }

            if (null != record.getOrganizationFId()) {
                VALUES("organization_f_id", "#{organizationFId,jdbcType=VARCHAR}");
            }

            if (null != record.getOrganizationLevel()) {
                VALUES("organization_level", "#{organizationLevel,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(Organization record) {
        return new SQL() {{
            UPDATE("t_organization");
            if (null != record.getOrganizationName()) {
                SET("organization_name = #{organizationName,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateUserId()) {
                SET("create_user_id = #{createUserId,jdbcType=VARCHAR}");
            }

            if (null != record.getOrganizationFId()) {
                SET("organization_f_id = #{organizationFId,jdbcType=VARCHAR}");
            }

            if (null != record.getOrganizationLevel()) {
                SET("organization_level = #{organizationLevel,jdbcType=VARCHAR}");
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
            WHERE("organization_id = #{organizationId,jdbcType=VARCHAR}");
        }}.toString();
    }

    public String selectBySelective(OrganizationListCondition condition) {
        return new SQL() {{
            SELECT("a.organization_id, a.organization_name, a.organization_f_id, d.organization_name as organization_f_name, a.organization_level, a.organization_f_tree, a.platform_id, b.platform_name, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_organization a, t_platform b, t_user c, t_organization d");
            if (null != condition.getOrganizationId()) {
                WHERE("a.organization_id = #{organizationId, jdbcType=VARCHAR}");
            }
            if (null != condition.getOrganizationName()) {
                final String organizationNameCond  = "a.organization_name like '%" + condition.getOrganizationName() + "%'";
                WHERE(organizationNameCond);
            }
            if (null != condition.getPlatformId()) {
                WHERE("a.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = c.user_id");
            WHERE("a.platform_id = b.platform_id");
            WHERE("a.organization_f_id = d.organization_id or a.organization_f_id = ''");

            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String selectByPlatformIdAndOrganizationName(OrganizationListCondition condition) {
        return new SQL() {{
            SELECT("a.organization_id, a.organization_name, a.organization_f_id, d.organization_name as organization_f_name, a.organization_level, a.organization_f_tree, a.platform_id, b.platform_name, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_organization a, t_platform b, t_user c, t_organization d");
            if (null != condition.getOrganizationName()) {
                WHERE("a.organization_name = #{organizationName, jdbcType=VARCHAR}");
            }
            if (null != condition.getPlatformId()) {
                WHERE("a.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = c.user_id");
            WHERE("a.platform_id = b.platform_id");
            WHERE("a.organization_f_id = d.organization_id");

            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String selectByOrganizationIds(OrganizationBulk organizations) {
        String strOrganizationIds = "";
        for (String item: organizations.getOrganizationIds()) {
            strOrganizationIds += ",'" + item + "'";
        }
        final String cond = strOrganizationIds.length() > 0? strOrganizationIds.substring(1): "''";

        return "select organization_id from t_organization where organization_id in (" + cond + ")";
    }

    public String deleteByOrganizationId(String organizationId) {
        return "delete from t_organization where organization_id = '" + organizationId + "' or organization_f_tree like '%" + organizationId + "%'";
    }

    public String deleteByOrganizationIds(OrganizationBulk orgs) {
        if (null == orgs.getOrganizationIds() || 0 >= orgs.getOrganizationIds().size()) {
            return "delete from t_organization where organization_id in ('')";
        }

        String strOrganizationIds = "";
        String strDeleteChildren = "";
        for (String item: orgs.getOrganizationIds()) {
            strOrganizationIds += ",'" + item + "'";
            strDeleteChildren += " or organization_f_tree like '%" + item + "%'";
        }
        return "delete from t_organization where organization_id in (" + strOrganizationIds.substring(1) + ")" + strDeleteChildren;
    }
}
