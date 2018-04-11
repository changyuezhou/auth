package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.bean.QueryListNoPageMessage;
import com.system.auth.bean.ResponseMessage;
import com.system.auth.dao.PlatformMapper;
import com.system.auth.dao.SessionMapper;
import com.system.auth.dao.UserAuthorityMapper;
import com.system.auth.model.Platform;
import com.system.auth.model.Session;
import com.system.auth.model.ext.UserAuthorityView;
import com.system.auth.model.ext.UserView;
import com.system.auth.model.ext.UserViewPassword;
import com.system.auth.model.request.*;
import com.system.auth.model.response.AccessTokenResponse;
import com.system.auth.model.response.ApplyAuthTokenResponse;
import com.system.auth.model.response.AuthorizationCodeResponse;
import com.system.auth.model.response.RefreshTokenResponse;
import com.system.auth.util.MybatisUtil;
import com.system.auth.util.SystemLogging;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/auth")

public class SessionController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    SessionMapper sessionMapper = session.getMapper(SessionMapper.class);

    @Value("${session.token.expire: 600}")
    private Integer tokenExpire;

    @Value("${session.refresh_token.expire: 84600}")
    private Integer refreshTokenExpire;

    @ApiOperation(value = "平台申请AuthToken", notes = "根据ApplyAuthToken对象申请Token")
    @ApiImplicitParam(name = "applyAuthToken", value = "平台信息", required = true, dataType = "ApplyAuthToken")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/apply_auth_token", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseMessage<ApplyAuthTokenResponse> add(@Validated @RequestBody ApplyAuthToken applyAuthToken, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(applyAuthToken), request, "", SystemLogging.getOperationStart());

        PlatformMapper platformMapper = session.getMapper(PlatformMapper.class);
        Platform platform = platformMapper.selectByPrimaryKey(applyAuthToken.getPlatformId());
        if (null == platform) {
            throw new OperationException(OperationException.getUserInputException(), "platform id:" + applyAuthToken.getPlatformId() + " is not exists");
        }

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        String password = byteArrayToHexString(md5.digest((platform.getSecretKey() + applyAuthToken.getRandom()).getBytes("utf-8")));

        if (!password.equalsIgnoreCase(applyAuthToken.getPassword())) {
            throw new OperationException(OperationException.getUserInputException(), "password:" + applyAuthToken.getPassword() + " is not correct");
        }

        final String authToken = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        Session userSession = new Session();
        userSession.setPlatformId(applyAuthToken.getPlatformId());
        userSession.setAuthToken(authToken);
        userSession.setAuthTokenCreateTime(new Long((java.lang.System.currentTimeMillis() / 1000)).intValue());
        userSession.setAuthTokenExpire(new Long(java.lang.System.currentTimeMillis() / 1000).intValue() + tokenExpire);

        sessionMapper.insertByAuthToken(userSession);

        ApplyAuthTokenResponse response = new ApplyAuthTokenResponse();
        response.setPlatformId(applyAuthToken.getPlatformId());
        response.setAuthToken(authToken);

        ResponseMessage<ApplyAuthTokenResponse> result = new ResponseMessage<ApplyAuthTokenResponse>(0, "", response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(applyAuthToken), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value = "申请Code", notes = "根据AuthToken对象申请Code")
    @ApiImplicitParam(name = "auth_code", value = "认证信息", required = true, dataType = "AuthorizationCode")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/authorization_code", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseMessage<AuthorizationCodeResponse> add(@Validated @RequestBody AuthorizationCode auth_code, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth_code), request, "", SystemLogging.getOperationStart());

        if (null == sessionMapper.ValidAuthToken(auth_code)) {
            throw new OperationException(OperationException.getUserInputException(), "auth token is invalid");
        }

        UserAuthorityMapper userAuthorityMapper = session.getMapper(UserAuthorityMapper.class);
        UserAuthorityListCondition condition = new UserAuthorityListCondition();
        condition.setUserName(auth_code.getUserName());
        condition.setPlatformId(auth_code.getPlatformId());
        UserViewPassword userView = userAuthorityMapper.selectUserByPlatformIdUserName(condition);
        if (null == userView) {
            throw new OperationException(OperationException.getUserInputException(), "user name:" + auth_code.getUserName() + " is not exists in platform id:" + auth_code.getPlatformId());
        }

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String password = byteArrayToHexString(md5.digest((auth_code.getAuthToken() + userView.getPassword()).getBytes("utf-8")));
        if (!password.equalsIgnoreCase(auth_code.getPassword())) {
            throw new OperationException(OperationException.getUserInputException(), "user name:" + auth_code.getUserName() + " password is not correct");
        }

        String openId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        Session userSession = new Session();
        userSession.setAuthToken(auth_code.getAuthToken());
        userSession.setOpenId(openId);
        userSession.setUserId(userView.getUserId());
        userSession.setCode(code);
        userSession.setCodeCreateTime(new Long((java.lang.System.currentTimeMillis() / 1000)).intValue());
        userSession.setCodeExpire(new Long(java.lang.System.currentTimeMillis() / 1000).intValue() + tokenExpire);

        sessionMapper.updateCodeByAuthToken(userSession);

        sessionMapper.UsedAuthToken(auth_code.getAuthToken());

        AuthorizationCodeResponse response = new AuthorizationCodeResponse();
        response.setCode(code);
        response.setOpenId(openId);
        response.setAuthToken(auth_code.getAuthToken());
        response.setPlatformId(auth_code.getPlatformId());

        ResponseMessage<AuthorizationCodeResponse> result = new ResponseMessage<AuthorizationCodeResponse>(0, "", response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth_code), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value = "获取AccessToken", notes = "根据AccessTokenRequest对象获取AccessToken")
    @ApiImplicitParam(name = "access_token_request", value = "认证信息", required = true, dataType = "AccessTokenRequest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/access_token", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseMessage<AccessTokenResponse> add(@Validated @RequestBody AccessTokenRequest access_token_request, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(access_token_request), request, "", SystemLogging.getOperationStart());

        if (null == sessionMapper.ValidCodeOpenId(access_token_request)) {
            throw new OperationException(OperationException.getUserInputException(), "open id:" + access_token_request.getOpenId() + " code:" + access_token_request.getCode() + " is invalid");
        }

        UserView user_view = sessionMapper.selectUserByOpenIdPlatformId(access_token_request);
        if (null == user_view) {
            throw new OperationException(OperationException.getUserInputException(), "open id:" + access_token_request.getOpenId() + " code:" + access_token_request.getCode() + " has no user information");
        }

        Session userSession = new Session();
        userSession.setPlatformId(access_token_request.getPlatformId());
        userSession.setCode(access_token_request.getCode());
        userSession.setCode(access_token_request.getCode());

        String access_token = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        userSession.setAccessToken(access_token);
        userSession.setAccessTokenCreateTime(new Long((java.lang.System.currentTimeMillis() / 1000)).intValue());
        Integer access_token_expire = new Long(java.lang.System.currentTimeMillis() / 1000).intValue() + tokenExpire;
        userSession.setAccessTokenExpire(access_token_expire);

        String refresh_token = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        userSession.setRefreshToken(refresh_token);
        userSession.setRefreshTokenCreateTime(new Long((java.lang.System.currentTimeMillis() / 1000)).intValue());
        Integer refresh_token_expire = new Long(java.lang.System.currentTimeMillis() / 1000).intValue() + refreshTokenExpire;
        userSession.setRefreshTokenExpire(refresh_token_expire);

        sessionMapper.updateAccessTokenRefreshTokenByCode(userSession);

        sessionMapper.UsedCode(access_token_request.getCode());

        AccessTokenResponse response = new AccessTokenResponse();
        response.setPlatformId(access_token_request.getPlatformId());
        response.setAccessToken(access_token);
        response.setAccessTokenExpire(access_token_expire);
        response.setRefreshToken(refresh_token);
        response.setRefreshTokenExpire(refresh_token_expire);
        response.setOpenId(access_token_request.getOpenId());
        response.setUserId(user_view.getUserId());
        response.setUserName(user_view.getUserName());

        ResponseMessage<AccessTokenResponse> result = new ResponseMessage<AccessTokenResponse>(0, "", response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(access_token_request), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value = "RefreshTokenRequest", notes = "根据RefreshTokenRequest对象获取RefreshToken")
    @ApiImplicitParam(name = "refresh_token_request", value = "认证信息", required = true, dataType = "RefreshTokenRequest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseMessage<RefreshTokenResponse> add(@Validated @RequestBody RefreshTokenRequest refresh_token_request, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(refresh_token_request), request, "", SystemLogging.getOperationStart());

        if (null == sessionMapper.ValidRefreshToken(refresh_token_request)) {
            throw new OperationException(OperationException.getUserInputException(), "open id:" + refresh_token_request.getOpenId()
                    + " platform id:" + refresh_token_request.getPlatformId()
                    + " refresh token: " + refresh_token_request.getRefreshToken() + "is invalid");
        }

        Session userSession = new Session();
        userSession.setOpenId(refresh_token_request.getOpenId());
        userSession.setPlatformId(refresh_token_request.getPlatformId());

        String access_token = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        userSession.setAccessToken(access_token);
        userSession.setAccessTokenCreateTime(new Long((java.lang.System.currentTimeMillis() / 1000)).intValue());
        Integer access_token_expire = new Long(java.lang.System.currentTimeMillis() / 1000).intValue() + tokenExpire;
        userSession.setAccessTokenExpire(access_token_expire);

        String refresh_token = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        userSession.setRefreshToken(refresh_token);
        userSession.setRefreshTokenCreateTime(new Long((java.lang.System.currentTimeMillis() / 1000)).intValue());
        Integer refresh_token_expire = new Long(java.lang.System.currentTimeMillis() / 1000).intValue() + refreshTokenExpire;
        userSession.setRefreshTokenExpire(refresh_token_expire);

        sessionMapper.updateRefreshTokenByOpenId(userSession);

        RefreshTokenResponse response = new RefreshTokenResponse();
        response.setPlatformId(refresh_token_request.getPlatformId());
        response.setOpenId(refresh_token_request.getOpenId());
        response.setRefreshToken(refresh_token);
        response.setRefreshTokenExpire(refresh_token_expire);
        response.setAccessToken(access_token);
        response.setAccessTokenExpire(access_token_expire);

        ResponseMessage<RefreshTokenResponse> result = new ResponseMessage<RefreshTokenResponse>(0, "", response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(refresh_token_request), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value = "获取用户信息", notes = "根据UserSessionQuery对象获取用户信息")
    @ApiImplicitParam(name = "user_session", value = "用户OpenId和Access Token", required = true, dataType = "UserSessionQuery")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/user_info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseMessage<UserView> user_info(@Validated @RequestBody UserSessionQuery user_session, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_session), request, "", SystemLogging.getOperationStart());

        UserView user_view = sessionMapper.selectUserByOpenIdAccessToken(user_session);
        if (null == user_view) {
            throw new OperationException(OperationException.getUserInputException(), "open id:" + user_session.getOpenId() + " can not find user information");
        }

        ResponseMessage<UserView> result = new ResponseMessage<UserView>(0, "", user_view);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_session), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value = "获取用户权限列表", notes = "根据UserSessionQuery对象获取用户权限列表")
    @ApiImplicitParam(name = "user_session", value = "用户OpenId和AccessToken", required = true, dataType = "UserSessionQuery")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/user_auth_list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public QueryListNoPageMessage<UserAuthorityView> user_auth_list(@Validated @RequestBody UserSessionQuery user_session, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_session), request, "", SystemLogging.getOperationStart());

        List<UserAuthorityView> user_list = sessionMapper.selectUserAuthorityByUserSession(user_session);
        if (null == user_list) {
            throw new OperationException(OperationException.getUserInputException(), "open id:" + user_session.getOpenId()
                    + " access token:" + user_session.getAccessToken()
                    + " can not find user authority list");
        }

        QueryListNoPageMessage<UserAuthorityView> result = new QueryListNoPageMessage<UserAuthorityView>(0, "", user_list);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_session), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    private String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

        int n = b;
        if (n < 0) n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
