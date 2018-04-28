package com.system.auth.dao;

import com.system.auth.model.User;
import com.system.auth.model.ext.UserView;
import com.system.auth.model.request.UserBulk;
import com.system.auth.model.request.UserUpdatePassword;
import com.system.auth.sql.UserSQL;
import com.system.auth.model.request.UserListCondition;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserMapper {
    @Delete({
        "delete from t_user",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String userId);

    @DeleteProvider(type = UserSQL.class, method = "deleteByUserIds")
    int deleteByUserIds(UserBulk users);

    @Insert({
        "insert into t_user (user_id, user_name, ",
        "password, status, ",
        "mobile_number, contact_name, ",
        "description, create_user_id, ",
        "update_time, create_time)",
        "values (#{userId,jdbcType=VARCHAR}, #{userName,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
        "#{mobileNumber,jdbcType=VARCHAR}, #{contactName,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{createUserId,jdbcType=VARCHAR}, ",
        "#{updateTime,jdbcType=BIGINT}, #{createTime,jdbcType=BIGINT})"
    })
    int insert(User record);

    @InsertProvider(type=UserSQL.class, method="insertSelective")
    int insertSelective(User record);


    @Select({
        "select",
        "a.user_id, a.user_name, a.password, a.status, a.mobile_number, a.contact_name, a.description, ",
        "a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time",
        "from t_user a, t_user b",
        "where a.user_id = #{userId,jdbcType=VARCHAR} and a.create_user_id=b.user_id"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="mobile_number", property="mobileNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="contact_name", property="contactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    UserView selectByPrimaryKey(String userId);

    @Select({
            "select",
            "password ",
            "from t_user",
            "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    String selectPasswordByPrimaryKey(String userId);

    @Select({
            "select",
            "a.user_id, a.user_name, a.password, a.status, a.mobile_number, a.contact_name, a.description, ",
            "a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time",
            "from t_user a, t_user b",
            "where a.user_name=#{userName, jdbcType=VARCHAR} and a.create_user_id = b.user_id"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="mobile_number", property="mobileNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="contact_name", property="contactName", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    UserView selectByUserName(String userName);

    @SelectProvider(type=UserSQL.class, method="selectBySelective")
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="mobile_number", property="mobileNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="contact_name", property="contactName", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<UserView> selectBySelective(UserListCondition condition);

    @UpdateProvider(type=UserSQL.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update t_user",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "mobile_number = #{mobileNumber,jdbcType=VARCHAR},",
          "contact_name = #{contactName,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(User record);

    @Update({
            "update t_user",
            "set password = #{newPwd,jdbcType=VARCHAR}",
            "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int updatePasswordByPrimaryKey(UserUpdatePassword user);
}