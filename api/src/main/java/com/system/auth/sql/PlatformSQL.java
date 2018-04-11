package com.system.auth.sql;

import com.system.auth.model.Platform;
import com.system.auth.model.request.PlatformBulk;
import com.system.auth.model.request.PlatformListCondition;
import org.apache.ibatis.jdbc.SQL;

public class PlatformSQL {
    public String insertSelective(Platform record) {
        return new SQL() {{
            INSERT_INTO("t_platform");
            if (null != record.getPlatformId()) {
                VALUES("platform_id", "#{platformId,jdbcType=VARCHAR}");
            }

            if (null != record.getSystemId()) {
                VALUES("system_id", "#{systemId,jdbcType=VARCHAR}");
            }

            if (null != record.getPlatformName()) {
                VALUES("platform_name", "#{platformName,jdbcType=VARCHAR}");
            }

            if (null != record.getPlatformDomain()) {
                VALUES("platform_domain", "#{platformDomain,jdbcType=VARCHAR}");
            }

            if (null != record.getSecretKey()) {
                VALUES("secret_key", "#{secretKey,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(Platform record) {
        return new SQL() {{
            UPDATE("t_platform");
            if (null != record.getPlatformName()) {
                SET("platform_name = #{platformName,jdbcType=VARCHAR}");
            }

            if (null != record.getSystemId()) {
                SET("system_id = #{systemId,jdbcType=VARCHAR}");
            }

            if (null != record.getPlatformDomain()) {
                SET("platform_domain = #{platformDomain,jdbcType=VARCHAR}");
            }

            if (null != record.getSecretKey()) {
                SET("secret_key = #{secretKey,jdbcType=VARCHAR}");
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
            WHERE("platform_id = #{platformId,jdbcType=VARCHAR}");
        }}.toString();
    }

    public String selectBySelective(PlatformListCondition condition) {
        return new SQL() {{
            SELECT("a.platform_id, a.platform_name, a.system_id, b.system_name, a.platform_domain, a.secret_key, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_platform a, t_system b, t_user c");
            if (null != condition.getPlatformId()) {
                WHERE("a.platform_id = #{platformId, jdbcType=VARCHAR}");
            }
            if (null != condition.getPlatformName()) {
                condition.setPlatformName("%" + condition.getPlatformName() + "%");
                WHERE("a.platform_name like #{platformName, jdbcType=VARCHAR}");
            }
            if (null != condition.getSystemId()) {
                WHERE("a.system_id = #{systemId, jdbcType=VARCHAR}");
            }
            if (null != condition.getSystemName()) {
                condition.setSystemName("%" + condition.getSystemName() + "%");
                WHERE("b.system_name like #{systemName, jdbcType=VARCHAR}");
            }
            if (null != condition.getCreateUserName()) {
                WHERE("c.user_name = #{createUserName, jdbcType=VARCHAR}");
            }
            if (null != condition.getCreateUserId()) {
                WHERE("a.create_user_id = #{createUserId, jdbcType=VARCHAR}");
            }

            WHERE("a.system_id = b.system_id");
            WHERE("a.create_user_id = c.user_id");

            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String deleteByPlatformIds(PlatformBulk platforms) {
        String strPlatformIds = "";
        for (String item: platforms.getPlatformIds()) {
            strPlatformIds += ",'" + item + "'";
        }
        final String cond = strPlatformIds.length() > 0? strPlatformIds.substring(1): "''";

        return "delete from t_platform where group_id in (" + cond + ")";
    }
}
