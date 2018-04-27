package com.system.auth.sql;

import com.system.auth.model.request.UserAuthorityListCondition;
import org.apache.ibatis.jdbc.SQL;

public class UserAuthoritySQL {
    public String selectBySelective(UserAuthorityListCondition condition) {
        return new SQL() {{
            SELECT("user_id, user_name, auth_id, auth_name, url, auth_level, auth_f_id, auth_f_name, platform_id, platform_name, create_user_id, create_user_name, update_time, create_time");
            FROM("v_user_authority");
            if (null != condition.getUserId()) {
                WHERE("user_id = #{userId, jdbcType=VARCHAR}");
            }

            if (null != condition.getUserName()) {
                final String CondStr = "user_name like '%" + condition.getUserName() + "%'";
                WHERE(CondStr);
            }

            if (null != condition.getPlatformId()) {
                WHERE("platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            if (null != condition.getAuthId()) {
                WHERE("auth_id = #{authId, jdbcType=VARCHAR}");
            }

            if (null != condition.getAuthName()) {
                final String CondStr = "auth_name like '%" + condition.getAuthName() + "%'";
                WHERE(CondStr);
            }

            ORDER_BY("user_name");
            ORDER_BY("update_time");

            GROUP_BY("auth_id");
        }}.toString();
    }

    public String selectUserByPlatformIdUserName(UserAuthorityListCondition condition) {
        return new SQL() {{
            SELECT("a.user_id, a.user_name, a.password, a.status, a.mobile_number, a.contact_name, a.description, a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_user a, t_user b, v_user_authority c");
            if (null != condition.getUserName()) {
                WHERE("c.user_name = #{userName, jdbcType=VARCHAR}");
            }

            if (null != condition.getPlatformId()) {
                WHERE("c.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.user_id = c.user_id");
            WHERE("a.create_user_id = b.user_id");

            GROUP_BY("user_id");
        }}.toString();
    }
}
