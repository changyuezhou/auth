package com.system.auth.sql;

import com.system.auth.model.User;
import com.system.auth.sql.condition.UserListCondition;
import org.apache.ibatis.jdbc.SQL;

public class UserSQL {
    public String insertSelective(User record) {
        return "";
    }

    public String updateByPrimaryKeySelective(User record) {
        return "";
    }

    public String selectBySelective(UserListCondition condition) {
        return new SQL() {{
            SELECT("a.user_id, a.user_name, a.password, a.status, a.mobile_number, a.contact_name, a.description, a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_user a, t_user b");
            if (null != condition.getUserId()) {
                WHERE("a.user_id = #{userId, jdbcType=INTEGER}");
            }
            if (null != condition.getContactName()) {
                WHERE("a.contact_name like #{contactName, jdbcType=VARCHAR}");
            }
            if (null != condition.getMobileNumber()) {
                WHERE("a.mobile_number like #{mobileNumber, jdbcType=VARCHAR}");
            }
            if (null != condition.getStatus()) {
                WHERE("a.status = #{status, jdbcType=INTEGER}");
            }
            if (null != condition.getUserName()) {
                WHERE("a.user_name = #{userName, jdbcType=VARCHAR}");
            }

            WHERE("a.user_id = b.user_id");

            ORDER_BY("a.update_time");
        }}.toString();
    }
}
