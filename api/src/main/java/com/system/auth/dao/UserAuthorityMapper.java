package com.system.auth.dao;

import com.system.auth.model.User;
import com.system.auth.model.ext.UserAuthorityView;
import com.system.auth.model.ext.UserView;
import com.system.auth.model.ext.UserViewPassword;
import com.system.auth.model.request.UserAuthorityListCondition;
import com.system.auth.model.request.UserBulk;
import com.system.auth.sql.UserAuthoritySQL;
import com.system.auth.sql.UserSQL;
import com.system.auth.model.request.UserListCondition;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserAuthorityMapper {
    @SelectProvider(type = UserAuthoritySQL.class, method = "selectBySelective")
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_level", property="authLevel", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_f_id", property="authFId", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_f_name", property="authFName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<UserAuthorityView> selectBySelective(UserAuthorityListCondition condition);

    @SelectProvider(type = UserAuthoritySQL.class, method = "selectUserByPlatformIdUserName")
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="mobile_number", property="mobileNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="contact_name", property="contactName", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    UserViewPassword selectUserByPlatformIdUserName(UserAuthorityListCondition condition);
}
