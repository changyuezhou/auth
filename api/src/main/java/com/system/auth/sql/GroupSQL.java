package com.system.auth.sql;

import com.system.auth.model.Group;
import com.system.auth.model.request.GroupBulk;
import com.system.auth.model.request.GroupListCondition;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class GroupSQL {
    public String insertSelective(Group record) {
        return new SQL() {{
            INSERT_INTO("t_group");
            if (null != record.getGroupId()) {
                VALUES("group_id", "#{groupId,jdbcType=VARCHAR}");
            }

            if (null != record.getGroupName()) {
                VALUES("group_name", "#{groupName,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(Group record) {
        return new SQL() {{
            UPDATE("t_group");
            if (null != record.getGroupName()) {
                SET("group_name = #{groupName,jdbcType=VARCHAR}");
            }

            if (null != record.getCreateUserId()) {
                SET("create_user_id = #{createUserId,jdbcType=VARCHAR}");
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
            WHERE("group_id = #{groupId,jdbcType=VARCHAR}");
        }}.toString();
    }

    public String selectBySelective(GroupListCondition condition) {
        return new SQL() {{
            SELECT("a.group_id, a.group_name, a.platform_id, b.platform_name, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_group a, t_platform b, t_user c");
            if (null != condition.getGroupId()) {
                WHERE("a.group_id = #{groupId, jdbcType=VARCHAR}");
            }
            if (null != condition.getGroupName()) {
                condition.setGroupName("%" + condition.getGroupName() + "%");
                WHERE("a.group_name like #{groupName, jdbcType=VARCHAR}");
            }
            if (null != condition.getPlatformId()) {
                WHERE("a.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = c.user_id");
            WHERE("a.platform_id = b.platform_id");

            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String selectByPlatformIdAndGroupName(GroupListCondition condition) {
        return new SQL() {{
            SELECT("a.group_id, a.group_name, a.platform_id, b.platform_name, a.description, a.create_user_id, c.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_group a, t_platform b, t_user c");
            if (null != condition.getGroupName()) {
                WHERE("a.group_name = #{groupName, jdbcType=VARCHAR}");
            }
            if (null != condition.getPlatformId()) {
                WHERE("a.platform_id = #{platformId, jdbcType=VARCHAR}");
            }

            WHERE("a.create_user_id = c.user_id");
            WHERE("a.platform_id = b.platform_id");

            ORDER_BY("a.update_time");
        }}.toString();
    }

    public String selectByGroupIds(GroupBulk groups) {
        String strGroupIds = "";
        for (String item: groups.getGroupIds()) {
            strGroupIds += ",'" + item + "'";
        }
        return "select group_id from t_group where group_id in (" + strGroupIds.substring(1) + ")";
    }

    public String deleteByGroupIds(GroupBulk groups) {
        String strGroupIds = "";
        for (String item: groups.getGroupIds()) {
            strGroupIds += ",'" + item + "'";
        }
        return "delete from t_group where group_id in (" + strGroupIds.substring(1) + ")";
    }
}
