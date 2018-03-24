package com.system.auth.sql;

import com.system.auth.model.Authority;
import com.system.auth.model.request.AuthorityBulk;
import com.system.auth.model.request.AuthorityListCondition;
import org.apache.ibatis.jdbc.SQL;

public class AuthoritySQL {
    public String insertSelective(Authority record) {
        return new SQL() {{
            INSERT_INTO("t_authority");
            if (null != record.getAuthId()) {
                VALUES("auth_id", "#{authId,jdbcType=VARCHAR}");
            }

            if (null != record.getAuthName()) {
                VALUES("auth_name", "#{authName,jdbcType=VARCHAR}");
            }

            if (null != record.getAuthFId()) {
                VALUES("auth_f_id", "#{authFId,jdbcType=VARCHAR}");
            }

            if (null != record.getAuthLevel()) {
                VALUES("auth_level", "#{authLevel,jdbcType=VARCHAR}");
            }

            if (null != record.getSystemId()) {
                VALUES("system_id", "#{systemId,jdbcType=VARCHAR}");
            }

            if (null != record.getAuthFTree()) {
                VALUES("auth_f_tree", "#{authFTree,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(Authority record) {
        return new SQL() {{
            UPDATE("t_authority");

            if (null != record.getAuthName()) {
                SET("auth_name = #{authName,jdbcType=VARCHAR}");
            }

            if (null != record.getAuthFId()) {
                SET("auth_f_id = #{authFId,jdbcType=VARCHAR}");
            }

            if (null != record.getAuthLevel()) {
                SET("auth_level = #{authLevel,jdbcType=VARCHAR}");
            }

            if (null != record.getSystemId()) {
                SET("system_id = #{systemId,jdbcType=VARCHAR}");
            }

            if (null != record.getAuthFTree()) {
                SET("auth_f_tree = #{authFTree,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateUserId()) {
                SET("create_user_id = #{createUserId,jdbcType=VARCHAR}");
            }

            if (null != record.getDescription()) {
                SET("description = #{description,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateTime()) {
                SET("create_time = #{createTime,jdbcType=BIGINT}");
            }

            if (null != record.getUpdateTime()) {
                SET("update_time", "#{updateTime,jdbcType=BIGINT}");
            }
            WHERE("auth_id = #{authId,jdbcType=VARCHAR}");
        }}.toString();
    }

    public String selectBySelective(AuthorityListCondition condition) {
        return new SQL() {{
            SELECT("a.auth_id, a.auth_name, a.auth_f_id, b.auth_name as auth_f_name, a.system_id, c.system_name, a.auth_level, a.auth_f_tree, a.description, a.create_user_id, d.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_authority a, t_authority b, t_system c, t_user d");
            if (null != condition.getAuthId()) {
                WHERE("a.auth_id = #{authId, jdbcType=VARCHAR}");
            }
            if (null != condition.getAuthName()) {
                condition.setAuthName("%" + condition.getAuthName() + "%");
                WHERE("a.auth_name like #{authName, jdbcType=VARCHAR}");
            }
            if (null != condition.getAuthFId()) {
                WHERE("a.auth_f_id = #{authFId, jdbcType=VARCHAR}");
            }
            if (null != condition.getAuthFName()) {
                condition.setAuthFName("%" + condition.getAuthFName() + "%");
                WHERE("b.auth_f_name like #{authFName, jdbcType=VARCHAR}");
            }
            if (null != condition.getAuthLevel()) {
                WHERE("a.auth_level = #{auth_level, jdbcType=VARCHAR}");
            }
            if (null != condition.getCreateUserId()) {
                WHERE("a.create_user_id = #{createUserId, jdbcType=VARCHAR}");
            }
            if (null != condition.getCreateUserName()) {
                WHERE("d.user_name = #{createUserName, jdbcType=VARCHAR}");
            }
            if (null != condition.getSystemId()) {
                WHERE("a.system_id = #{systemId, jdbcType=VARCHAR}");
            }

            WHERE("a.auth_f_id = b.auth_id or a.auth_f_id=''");
            WHERE("a.system_id = c.system_id");
            WHERE("a.create_user_id = d.user_id");

            ORDER_BY("a.update_time");

            GROUP_BY("a.auth_id");
        }}.toString();
    }

    public String selectBySystemIdAuthName(AuthorityListCondition condition) {
        return new SQL() {{
            SELECT("auth_id, auth_name, auth_f_id, system_id, auth_level, auth_f_tree, description, create_user_id, update_time, create_time");
            FROM("t_authority");
            if (null != condition.getAuthName()) {
                WHERE("auth_name = #{authName, jdbcType=VARCHAR}");
            }

            if (null != condition.getSystemId()) {
                WHERE("system_id = #{systemId, jdbcType=VARCHAR}");
            }
        }}.toString();
    }

    public String selectByAuthIds(AuthorityBulk auths) {
        String strAuthIds = "";
        for (String item: auths.getAuthIds()) {
            strAuthIds += ",'" + item + "'";
        }
        final String cond = strAuthIds.length() > 0? strAuthIds.substring(1): "''";

        return "select auth_id from t_authority where auth_id in (" + cond + ")";
    }

    public String deleteByAuthorityId(String authId) {
        return "delete from t_authority where auth_id = '" + authId + "' or auth_f_tree like '%" + authId + "%'";
    }

    public String deleteByAuthorityIds(AuthorityBulk auths) {
        if (null == auths.getAuthIds() || 0 >= auths.getAuthIds().size()) {
            return "delete from t_authority where auth_id in ('')";
        }

        String strAuthorityIds = "";
        String strDeleteChildren = "";
        for (String item: auths.getAuthIds()) {
            strAuthorityIds += ",'" + item + "'";
            strDeleteChildren += " or auth_f_tree like '%" + item + "%'";
        }
        return "delete from t_authority where auth_id in (" + strAuthorityIds.substring(1) + ")" + strDeleteChildren;
    }
}
