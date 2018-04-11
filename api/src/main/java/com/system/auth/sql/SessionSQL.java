package com.system.auth.sql;

import com.system.auth.model.Session;
import com.system.auth.model.request.AccessTokenRequest;
import com.system.auth.model.request.AuthorizationCode;
import com.system.auth.model.request.RefreshTokenRequest;
import com.system.auth.model.request.UserSessionQuery;
import org.apache.ibatis.jdbc.SQL;

public class SessionSQL {
    public String insertByAuthToken(Session session) {
        return new SQL() {{
            INSERT_INTO("t_session");
            if (null != session.getPlatformId()) {
                VALUES("platform_id", "#{platformId,jdbcType=VARCHAR}");
            }

            if (null != session.getAuthToken()) {
                VALUES("auth_token", "#{authToken,jdbcType=VARCHAR}");
            }

            if (null != session.getAuthTokenCreateTime()) {
                VALUES("auth_token_create_time", "#{authTokenCreateTime,jdbcType=VARCHAR}");
            }

            if (null != session.getAuthTokenExpire()) {
                VALUES("auth_token_expire", "#{authTokenExpire,jdbcType=VARCHAR}");
            }
        }}.toString();
    }

    public String updateCodeByAuthToken(Session session) {
        return new SQL() {{
            UPDATE("t_session");
            if (null != session.getCode()) {
                SET("code = #{code,jdbcType=VARCHAR}");
            }

            if (null != session.getCodeCreateTime()) {
                SET("code_create_time = #{codeCreateTime,jdbcType=VARCHAR}");
            }

            if (null != session.getCodeExpire()) {
                SET("code_expire = #{codeExpire,jdbcType=VARCHAR}");
            }

            if (null != session.getUserId()) {
                SET("user_id = #{userId,jdbcType=VARCHAR}");
            }

            if (null != session.getOpenId()) {
                SET("open_id = #{openId,jdbcType=VARCHAR}");
            }

            WHERE("auth_token = #{authToken, jdbcType=VARCHAR}");
        }}.toString();
    }

    public String updateAccessTokenRefreshTokenByCode(Session session) {
        return new SQL() {{
            UPDATE("t_session");
            if (null != session.getAccessToken()) {
                SET("access_token = #{accessToken,jdbcType=VARCHAR}");
            }

            if (null != session.getAccessTokenCreateTime()) {
                SET("access_token_create_time = #{accessTokenCreateTime,jdbcType=VARCHAR}");
            }

            if (null != session.getAccessTokenExpire()) {
                SET("access_token_expire = #{accessTokenExpire,jdbcType=VARCHAR}");
            }

            if (null != session.getRefreshToken()) {
                SET("refresh_token = #{refreshToken,jdbcType=VARCHAR}");
            }

            if (null != session.getRefreshTokenCreateTime()) {
                SET("refresh_token_create_time = #{refreshTokenCreateTime,jdbcType=VARCHAR}");
            }

            if (null != session.getRefreshTokenExpire()) {
                SET("refresh_token_expire = #{refreshTokenExpire,jdbcType=VARCHAR}");
            }

            if (null != session.getOpenId()) {
                SET("open_id = #{openId,jdbcType=VARCHAR}");
            }

            if (null != session.getUserId()) {
                SET("user_id = #{userId,jdbcType=VARCHAR}");
            }

            WHERE("code = #{code, jdbcType=VARCHAR}");
        }}.toString();
    }

    public String updateRefreshTokenByOpenId(Session session) {
        return new SQL() {{
            UPDATE("t_session");
            if (null != session.getAccessToken()) {
                SET("access_token = #{accessToken,jdbcType=VARCHAR}");
            }

            if (null != session.getAccessTokenCreateTime()) {
                SET("access_token_create_time = #{accessTokenCreateTime,jdbcType=VARCHAR}");
            }

            if (null != session.getAccessTokenExpire()) {
                SET("access_token_expire = #{accessTokenExpire,jdbcType=VARCHAR}");
            }

            if (null != session.getRefreshToken()) {
                SET("refresh_token = #{refreshToken,jdbcType=VARCHAR}");
            }

            if (null != session.getRefreshTokenCreateTime()) {
                SET("refresh_token_create_time = #{refreshTokenCreateTime,jdbcType=VARCHAR}");
            }

            if (null != session.getRefreshTokenExpire()) {
                SET("refresh_token_expire = #{refreshTokenExpire,jdbcType=VARCHAR}");
            }

            WHERE("open_id = #{openId, jdbcType=VARCHAR}");
            WHERE("platform_id = #{platformId, jdbcType=VARCHAR}");
        }}.toString();
    }

    public String ValidAuthToken(AuthorizationCode request) {
        Integer now = new Long(System.currentTimeMillis()/1000).intValue();

        return "select auth_token from t_session where auth_token = '" + request.getAuthToken() + "' and platform_id = '" + request.getPlatformId()  + "' and auth_token_used = 0 and auth_token_expire >" + Integer.toString(now);
    }

    public String ValidCode(String code) {
        Integer now = new Long(System.currentTimeMillis()/1000).intValue();
        return "select code from t_session where code ='" + code + "' and code_used = 0 and code_expire > " + Integer.toString(now);
    }

    public String ValidCodeOpenId(AccessTokenRequest request) {
        Integer now = new Long(System.currentTimeMillis()/1000).intValue();
        return "select open_id from t_session where open_id = '" + request.getOpenId() + "' and platform_id = '" + request.getPlatformId() +  "' and code = '" + request.getCode() +  "' and code_used = 0 and code_expire > " + Integer.toString(now);
    }

    public String ValidAccessToken(String accessToken) {
        Integer now = new Long(System.currentTimeMillis()/1000).intValue();
        return "select access_token from t_session where access_token ='" + accessToken + "' and access_token_used = 0 and access_token_expire > " + Integer.toString(now);
    }

    public String ValidRefreshToken(RefreshTokenRequest request) {
        Integer now = new Long(System.currentTimeMillis()/1000).intValue();
        return "select refresh_token from t_session where refresh_token ='" + request.getRefreshToken() + "' and platform_id = '" + request.getPlatformId() + "' and refresh_token_used = 0 and refresh_token_expire > " + Integer.toString(now);
    }

    public String UsedAuthToken(String authToken) {
        return "update t_session set auth_token_used=1 where auth_token = '" + authToken + "'";

    }

    public String UsedCode(String code) {
        return "update t_session set code_used=1 where code = '" + code + "'";

    }

    public String UsedAccessToken(String accessToken) {
        return "update t_session set access_token_used=1 where access_token = '" + accessToken + "'";
    }

    public String UsedRefreshToken(String refreshToken) {
        return "update t_session set refresh_token_used=1 where refresh_token = '" + refreshToken + "'";
    }

    public String selectUserByOpenIdPlatformId(AccessTokenRequest request) {
        return new SQL() {{
            SELECT("a.user_id, a.user_name, a.password, a.status, a.mobile_number, a.contact_name, a.description, a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_user a, t_user b, t_session c");
            WHERE("c.open_id = #{openId, jdbcType=VARCHAR}");
            WHERE("c.platform_id = #{platformId, jdbcType=VARCHAR}");
            WHERE("c.code = #{code, jdbcType=VARCHAR}");
            WHERE("a.user_id = c.user_id");
            WHERE("a.create_user_id = b.user_id");
        }}.toString();
    }

    public String selectUserByOpenIdAccessToken(UserSessionQuery request) {
        return new SQL() {{
            SELECT("a.user_id, a.user_name, a.password, a.status, a.mobile_number, a.contact_name, a.description, a.create_user_id, b.user_name as create_user_name, a.update_time, a.create_time");
            FROM("t_user a, t_user b, t_session c");
            WHERE("c.open_id = #{openId, jdbcType=VARCHAR}");
            WHERE("c.access_token = #{accessToken, jdbcType=VARCHAR}");
            Integer now = new Long(System.currentTimeMillis()/1000).intValue();
            WHERE("c.access_token_expire > " + Integer.toString(now));
            WHERE("a.user_id = c.user_id");
            WHERE("a.create_user_id = b.user_id");
        }}.toString();
    }

    public String selectUserAuthorityByUserSession(UserSessionQuery request) {
        return new SQL() {{
            SELECT("a.user_id, a.user_name, a.auth_id, a.auth_name, a.platform_id, a.platform_name, a.create_user_id, a.create_user_name, a.update_time, a.create_time");
            FROM("v_user_authority a, t_session b");

            WHERE("b.open_id = #{openId, jdbcType=VARCHAR}");
            WHERE("b.access_token = #{accessToken, jdbcType=VARCHAR}");
            Integer now = new Long(System.currentTimeMillis()/1000).intValue();
            WHERE("b.access_token_expire > " + Integer.toString(now));
            WHERE("b.platform_id = #{platformId, jdbcType=VARCHAR}");

            WHERE("a.user_id = b.user_id");

            ORDER_BY("auth_name");
            ORDER_BY("update_time");
        }}.toString();
    }
}
