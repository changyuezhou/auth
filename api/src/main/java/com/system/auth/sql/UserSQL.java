package com.system.auth.sql;

import com.system.auth.model.User;
import com.system.auth.model.request.UserBulk;
import com.system.auth.model.request.UserListCondition;
import org.apache.ibatis.jdbc.SQL;

public class UserSQL {
    public String insertSelective(User record) {
        return new SQL() {{
            INSERT_INTO("t_user");
            if (null != record.getUserId()) {
                VALUES("user_id", "#{userId,jdbcType=VARCHAR}");
            }

            if (null != record.getUserName()) {
                VALUES("user_name", "#{userName,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateUserId()) {
                VALUES("create_user_id", "#{createUserId,jdbcType=VARCHAR}");
            }

            if (null != record.getContactName()) {
                VALUES("contact_name", "#{contactName,jdbcType=VARCHAR}");
            }

            if (null != record.getMobileNumber()) {
                VALUES("mobile_number", "#{mobileNumber,jdbcType=VARCHAR}");
            }

            if (null != record.getDescription()) {
                VALUES("description", "#{description,jdbcType=VARCHAR}");
            }

            if (null != record.getPassword()) {
                VALUES("password", "#{password,jdbcType=VARCHAR}");
            }

            if (null != record.getStatus()) {
                VALUES("status", "#{status,jdbcType=INTEGER}");
            }

            if (null != record.getCreateTime()) {
                VALUES("create_time", "#{createTime,jdbcType=BIGINT}");
            }

            if (null != record.getUpdateTime()) {
                VALUES("update_time", "#{updateTime,jdbcType=BIGINT}");
            }
        }}.toString();
    }

    public String updateByPrimaryKeySelective(User record) {
        return new SQL() {{
            UPDATE("t_user");
            if (null != record.getUserName()) {
                SET("user_name = #{userName,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateUserId()) {
                SET("create_user_id = #{createUserId,jdbcType=VARCHAR}");
            }


            if (null != record.getContactName()) {
                SET("contact_name = #{contactName,jdbcType=VARCHAR}");
            }

            if (null != record.getMobileNumber()) {
                SET("mobile_number = #{mobileNumber,jdbcType=VARCHAR}");
            }

            if (null != record.getDescription()) {
                SET("description = #{description,jdbcType=VARCHAR}");
            }

            if (null != record.getPassword()) {
                SET("password = #{password,jdbcType=VARCHAR}");
            }

            if (null != record.getStatus()) {
                SET("status = #{status,jdbcType=INTEGER}");
            }

            if (null != record.getUpdateTime()) {
                SET("update_time = #{updateTime,jdbcType=BIGINT}");
            }
            WHERE("user_id = #{userId,jdbcType=VARCHAR}");
        }}.toString();
    }

    public String selectBySelective(UserListCondition condition) {
        return new SQL() {{
            SELECT("a.user_id, a.user_name, a.password, a.status, a.mobile_number, a.contact_name, a.description, a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_user a, t_user b");
            if (null != condition.getUserId()) {
                WHERE("a.user_id = #{userId, jdbcType=VARCHAR}");
            }
            if (null != condition.getContactName()) {
                condition.setContactName("%" + condition.getContactName() + "%");
                WHERE("a.contact_name like #{contactName, jdbcType=VARCHAR}");
            }
            if (null != condition.getMobileNumber()) {
                condition.setMobileNumber("%" + condition.getMobileNumber() + "%");
                WHERE("a.mobile_number like #{mobileNumber, jdbcType=VARCHAR}");
            }
            if (null != condition.getStatus()) {
                WHERE("a.status = #{status, jdbcType=INTEGER}");
            }
            if (null != condition.getUserName()) {
                condition.setUserName("%" + condition.getUserName() + "%");
                WHERE("a.user_name like #{userName, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = b.user_id");

            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String deleteUserByUserName() {
        return new SQL() {{
            DELETE_FROM("t_user");
            WHERE("user_name = #{userName, jdbcType=VARCHAR}");
        }}.toString();
    }

    public String deleteByUserIds(UserBulk users) {
        String strUserIds = "";
        for (String item: users.getUserIds()) {
            strUserIds += ",'" + item + "'";
        }

        final String cond = strUserIds.length() > 0? strUserIds.substring(1): "''";

        return "delete from t_user where group_id in (" + cond + ")";
    }
}
