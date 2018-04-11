package com.system.auth.dao;

import com.system.auth.model.Session;
import com.system.auth.model.ext.UserAuthorityView;
import com.system.auth.model.ext.UserView;
import com.system.auth.model.request.AccessTokenRequest;
import com.system.auth.model.request.AuthorizationCode;
import com.system.auth.model.request.RefreshTokenRequest;
import com.system.auth.model.request.UserSessionQuery;
import com.system.auth.sql.SessionSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface SessionMapper {
    @InsertProvider(type = SessionSQL.class, method = "insertByAuthToken")
    int insertByAuthToken(Session session);

    @UpdateProvider(type=SessionSQL.class, method = "updateCodeByAuthToken")
    int updateCodeByAuthToken(Session session);

    @UpdateProvider(type = SessionSQL.class, method = "updateRefreshTokenByOpenId")
    int updateRefreshTokenByOpenId(Session session);

    @UpdateProvider(type = SessionSQL.class, method = "updateAccessTokenRefreshTokenByCode")
    int updateAccessTokenRefreshTokenByCode(Session session);

    @SelectProvider(type = SessionSQL.class, method = "ValidCodeOpenId")
    String ValidCodeOpenId(AccessTokenRequest request);

    @SelectProvider(type = SessionSQL.class, method = "ValidAuthToken")
    String ValidAuthToken(AuthorizationCode request);

    @SelectProvider(type = SessionSQL.class, method = "ValidCode")
    String ValidCode(String code);

    @SelectProvider(type = SessionSQL.class, method = "ValidAccessToken")
    String ValidAccessToken(String accessToken);

    @SelectProvider(type = SessionSQL.class, method = "ValidRefreshToken")
    String ValidRefreshToken(RefreshTokenRequest request);


    @UpdateProvider(type = SessionSQL.class, method = "UsedAuthToken")
    int UsedAuthToken(String authToken);


    @UpdateProvider(type = SessionSQL.class, method = "UsedCode")
    int UsedCode(String code);


    @UpdateProvider(type = SessionSQL.class, method = "UsedAccessToken")
    int UsedAccessToken(String accessToken);


    @UpdateProvider(type = SessionSQL.class, method = "UsedRefreshToken")
    int UsedRefreshToken(String refreshToken);

    @SelectProvider(type = SessionSQL.class, method = "selectUserByOpenIdPlatformId")
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
    UserView selectUserByOpenIdPlatformId(AccessTokenRequest request);

    @SelectProvider(type = SessionSQL.class, method = "selectUserByOpenIdAccessToken")
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
    UserView selectUserByOpenIdAccessToken(UserSessionQuery request);

    @SelectProvider(type = SessionSQL.class, method = "selectUserAuthorityByUserSession")
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR),
            @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
            @Result(column="platform_name", property="platformName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    List<UserAuthorityView> selectUserAuthorityByUserSession(UserSessionQuery request);
}