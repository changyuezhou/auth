package com.system.auth.api;

import com.system.auth.auth.Auth;
import com.system.auth.bean.*;
import com.system.auth.dao.UserMapper;
import com.system.auth.model.*;
import com.system.auth.model.System;
import com.system.auth.model.ext.AuthorityView;
import com.system.auth.model.ext.UserAuthorityView;
import com.system.auth.model.ext.UserView;
import com.system.auth.model.request.*;
import com.system.auth.model.response.AccessTokenResponse;
import com.system.auth.model.response.ApplyAuthTokenResponse;
import com.system.auth.model.response.AuthorizationCodeResponse;
import com.system.auth.model.response.RefreshTokenResponse;
import com.system.auth.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.MessageDigest;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SessionAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static String authToken = "";
    private static String code = "";
    private static String openId = "";
    private static String accessToken = "";
    private static String refreshToken = "";

    private static String groupId = "";
    private static String groupName = "prefix_test_name";

    private static String authId = "";
    private static String authName = "prefix_test_name";

    private static String userId = "";
    private static String userName = "prefix_test_name";
    private static String userPassword = "386ADA9F63B54EECB2B49663043CC744";

    private static String platformId = "";
    private static String platformName = "prefix_test_name";
    private static String secret = "80CE9FC7CEC4490B91061A0FA7E045F1";

    private static String systemId = "";
    private static String systemName = "prefix_test_name";

    private static int time_diff_base = 10000;

    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    UserMapper userMapper = session.getMapper(UserMapper.class);

    // add session user
    @Test
    public void d_test01() throws Exception {
        User user = new User();
        user.setUserId(UserBean.getUserId());
        user.setUserName(UserBean.getUserName());
        user.setDescription(UserBean.getDescription());
        user.setCreateUserId(UserBean.getUserId());
        user.setCreateTime(UserBean.getCreateTime());
        user.setUpdateTime(UserBean.getUpdateTime());
        user.setPassword(UserBean.getPassword());
        user.setMobileNumber(UserBean.getMobileNumber());
        user.setContactName(UserBean.getContactName());
        user.setStatus(UserBean.getStatus());

        userMapper.insertSelective(user);

        com.system.auth.auth.User auth_user = new com.system.auth.auth.User();
        auth_user.setUserId(UserBean.getUserId());
        auth_user.setUserName(UserBean.getUserName());
        auth_user.setDescription(UserBean.getDescription());
        auth_user.setCreateUserId(UserBean.getUserId());
        auth_user.setCreateTime(UserBean.getCreateTime());
        auth_user.setUpdateTime(UserBean.getUpdateTime());
        auth_user.setMobileNumber(UserBean.getMobileNumber());
        auth_user.setContactName(UserBean.getContactName());
        auth_user.setStatus(UserBean.getStatus());

        String key = SessionBean.getOpenId() + "_" + SessionBean.getAccessToken();

        Auth.setUserInfo(key, auth_user);
    }

    // add user test
    @Test
    public void test01() throws Exception {
        User user = new User();
        user.setUserName(userName);
        user.setDescription("description");
        user.setStatus(1);
        user.setContactName("contact name");
        user.setMobileNumber("138000000000");
        user.setPassword(userPassword);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<User> req_entity = new HttpEntity<User>(user, requestHeaders);

        ResponseEntity<UserAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user/add", req_entity,  UserAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getUserName()).isEqualTo(user.getUserName());

        userId = entity.getBody().getData().getUserId();
    }

    // add system test
    @Test
    public void test02() throws Exception {
        System new_system = new System();
        new_system.setSystemName(systemName);
        new_system.setDescription("测试");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<System> req_entity = new HttpEntity<System>(new_system, requestHeaders);

        ResponseEntity<SystemAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/system/add", req_entity,  SystemAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getSystemName()).isEqualTo(new_system.getSystemName());

        systemId = entity.getBody().getData().getSystemId();
    }

    // add platform test
    @Test
    public void test03() throws Exception {
        Platform new_platform = new Platform();

        new_platform.setSystemId(systemId);
        new_platform.setSecretKey(secret);
        new_platform.setPlatformDomain("www");
        new_platform.setPlatformName(platformName);
        new_platform.setDescription("description");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<Platform> req_entity = new HttpEntity<Platform>(new_platform, requestHeaders);

        ResponseEntity<PlatformAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/platform/add", req_entity,  PlatformAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getPlatformName()).isEqualTo(new_platform.getPlatformName());

        platformId = entity.getBody().getData().getPlatformId();
    }

    // add group test
    @Test
    public void test04() throws Exception {
        Group new_group = new Group();
        new_group.setPlatformId(platformId);
        new_group.setGroupName(groupName);
        new_group.setDescription("description");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<Group> req_entity = new HttpEntity<Group>(new_group, requestHeaders);

        ResponseEntity<GroupAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/group/add", req_entity,  GroupAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getGroupName()).isEqualTo(new_group.getGroupName());

        groupId = entity.getBody().getData().getGroupId();
    }

    // add authority test
    @Test
    public void test05() throws Exception {
        Authority new_authority = new Authority();

        new_authority.setAuthFId("");
        new_authority.setSystemId(systemId);
        new_authority.setAuthLevel(0);
        new_authority.setAuthName(authName);
        new_authority.setDescription("description");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<Authority> req_entity = new HttpEntity<Authority>(new_authority, requestHeaders);

        ResponseEntity<AuthorityAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/authority/add", req_entity,  AuthorityAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getAuthName()).isEqualTo(new_authority.getAuthName());

        authId = entity.getBody().getData().getAuthId();
    }

    // add group authoriy test
    @Test
    public void test06() throws Exception {
        GroupAuthority new_group_auth = new GroupAuthority();

        new_group_auth.setAuthId(authId);
        new_group_auth.setGroupId(groupId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<GroupAuthority> req_entity = new HttpEntity<GroupAuthority>(new_group_auth, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/group_authority/add", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // add user group test
    @Test
    public void test07() throws Exception {
        UserGroup new_user_group = new UserGroup();

        new_user_group.setUserId(userId);
        new_user_group.setGroupId(groupId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<UserGroup> req_entity = new HttpEntity<UserGroup>(new_user_group, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user_group/add", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // user authority list
    @Test
    public void test08() throws Exception {
        UserAuthorityListCondition condition = new UserAuthorityListCondition();
        condition.setAuthName(authName);
        condition.setPlatformId(platformId);
        condition.setUserId(userId);
        condition.setUserName(userName);
        condition.setCreateUserId(UserBean.getUserId());
        condition.setCreateUserName(UserBean.getUserName());
        condition.setPageSize(10);
        condition.setPageNum(1);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<UserAuthorityListCondition> req_entity = new HttpEntity<UserAuthorityListCondition>(condition, requestHeaders);

        ResponseEntity<UserAuthorityListConditionResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user_authority/platform_authority", req_entity,  UserAuthorityListConditionResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        UserAuthorityView authority_view = entity.getBody().getData().get(0);
        then(authority_view.getUserId()).isEqualTo(userId);
        then(authority_view.getUserName()).isEqualTo(userName);
        then(authority_view.getPlatformId()).isEqualTo(platformId);
        then(authority_view.getPlatformName()).isEqualTo(platformName);
        then(authority_view.getAuthId()).isEqualTo(authId);
        then(authority_view.getAuthName()).isEqualTo(authName);
        then(authority_view.getCreateUserName()).isEqualTo(UserBean.getUserName());
        then(authority_view.getCreateUserId()).isEqualTo(UserBean.getUserId());
    }

    // apply auth token
    @Test
    public void test21() throws Exception {
        ApplyAuthToken applyAuthToken = new ApplyAuthToken();
        applyAuthToken.setPlatformId(platformId);

        String random = Integer.toString(new Long(java.lang.System.currentTimeMillis()).intValue());
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String password = byteArrayToHexString(md5.digest((secret + random).getBytes("utf-8")));

        applyAuthToken.setRandom(random);
        applyAuthToken.setPassword(password);

        ResponseEntity<ApplyAuthTokenResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/auth/apply_auth_token", applyAuthToken,  ApplyAuthTokenResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        ApplyAuthTokenResponse response = entity.getBody().getData();
        then(response.getPlatformId()).isEqualTo(platformId);
        then(response.getAuthToken().isEmpty()).isEqualTo(false);

        authToken = response.getAuthToken();
    }

    // authorization_code
    @Test
    public void test22() throws Exception {
        AuthorizationCode auth_code = new AuthorizationCode();

        auth_code.setPlatformId(platformId);
        auth_code.setAuthToken(authToken);
        auth_code.setUserName(userName);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String password = byteArrayToHexString(md5.digest( (userPassword + auth_code.getAuthToken()).getBytes("utf-8")));
        auth_code.setPassword(password);

        ResponseEntity<AuthorizationCodeResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/auth/authorization_code", auth_code,  AuthorizationCodeResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        AuthorizationCodeResponse response = entity.getBody().getData();
        then(response.getPlatformId()).isEqualTo(platformId);
        then(response.getAuthToken().isEmpty()).isEqualTo(false);
        then(response.getCode().isEmpty()).isEqualTo(false);
        then(response.getOpenId().isEmpty()).isEqualTo(false);

        code = response.getCode();
        openId = response.getOpenId();
    }

    // access_token_request
    @Test
    public void test23() throws Exception {
        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
        accessTokenRequest.setPlatformId(platformId);
        accessTokenRequest.setCode(code);
        accessTokenRequest.setOpenId(openId);

        ResponseEntity<AccessTokenResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/auth/access_token", accessTokenRequest,  AccessTokenResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        AccessTokenResponse response = entity.getBody().getData();
        then(response.getPlatformId()).isEqualTo(platformId);
        then(response.getAccessToken().isEmpty()).isEqualTo(false);
        then(response.getOpenId().isEmpty()).isEqualTo(false);
        then(response.getUserId().isEmpty()).isEqualTo(false);
        then(response.getUserName().isEmpty()).isEqualTo(false);
        then(response.getRefreshToken().isEmpty()).isEqualTo(false);

        then(response.getUserId()).isEqualTo(userId);
        then(response.getUserName()).isEqualTo(userName);

        accessToken = response.getAccessToken();
        refreshToken = response.getRefreshToken();
    }

    // refresh_token
    @Test
    public void test24() throws Exception {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setOpenId(openId);
        refreshTokenRequest.setPlatformId(platformId);
        refreshTokenRequest.setRefreshToken(refreshToken);

        ResponseEntity<RefreshTokenResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/auth/refresh_token", refreshTokenRequest,  RefreshTokenResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        RefreshTokenResponse response = entity.getBody().getData();
        then(response.getPlatformId()).isEqualTo(platformId);
        then(response.getAccessToken().isEmpty()).isEqualTo(false);
        then(response.getOpenId().isEmpty()).isEqualTo(false);
        then(response.getRefreshToken().isEmpty()).isEqualTo(false);

        accessToken = response.getAccessToken();
        refreshToken = response.getRefreshToken();
    }

    // user_info
    @Test
    public void test25() throws Exception {
        UserSessionQuery userSessionQuery = new UserSessionQuery();
        userSessionQuery.setAccessToken(accessToken);
        userSessionQuery.setOpenId(openId);
        userSessionQuery.setPlatformId(platformId);

        ResponseEntity<UserQueryByPrimaryKeyResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/auth/user_info", userSessionQuery,  UserQueryByPrimaryKeyResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        UserView response = entity.getBody().getData();
        then(response.getUserId().isEmpty()).isEqualTo(false);
        then(response.getUserName().isEmpty()).isEqualTo(false);

        then(response.getUserId()).isEqualTo(userId);
        then(response.getUserName()).isEqualTo(userName);
    }

    // user_auth_list
    @Test
    public void test26() throws Exception {
        UserSessionQuery userSessionQuery = new UserSessionQuery();
        userSessionQuery.setAccessToken(accessToken);
        userSessionQuery.setOpenId(openId);
        userSessionQuery.setPlatformId(platformId);

        ResponseEntity<UserAuthorityListNoPageResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/auth/user_auth_list", userSessionQuery,  UserAuthorityListNoPageResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().isEmpty()).isEqualTo(false);
        then(entity.getBody().getData().size()).isEqualTo(1);

        UserAuthorityView user_auth_view = entity.getBody().getData().get(0);
        then(user_auth_view.getUserId()).isEqualTo(userId);
        then(user_auth_view.getUserName()).isEqualTo(userName);
        then(user_auth_view.getPlatformName()).isEqualTo(platformName);
        then(user_auth_view.getPlatformId()).isEqualTo(platformId);
        then(user_auth_view.getAuthId()).isEqualTo(authId);
        then(user_auth_view.getAuthName()).isEqualTo(authName);
        then(user_auth_view.getCreateUserId()).isEqualTo(UserBean.getUserId());
        then(user_auth_view.getCreateUserName()).isEqualTo(UserBean.getUserName());
    }

    // delete user group
    @Test
    public void test92() throws Exception {
        UserGroupKey user_group_req = new UserGroupKey();
        user_group_req.setUserId(userId);
        user_group_req.setGroupId(groupId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<UserGroupKey> req_entity = new HttpEntity<UserGroupKey>(user_group_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user_group/delete", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // delete group authority
    @Test
    public void test93() throws Exception {
        GroupAuthorityKey group_req = new GroupAuthorityKey();
        group_req.setAuthId(authId);
        group_req.setGroupId(groupId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<GroupAuthorityKey> req_entity = new HttpEntity<GroupAuthorityKey>(group_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/group_authority/delete", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // delete authority
    @Test
    public void test94() throws Exception {
        AuthorityKey authority_req = new AuthorityKey(authId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<AuthorityKey> req_entity = new HttpEntity<AuthorityKey>(authority_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/authority/delete", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // delete
    @Test
    public void test95() throws Exception {
        GroupKey group_req = new GroupKey(groupId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<GroupKey> req_entity = new HttpEntity<GroupKey>(group_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/group/delete", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // delete
    @Test
    public void test96() throws Exception {
        PlatformKey platform_req = new PlatformKey(platformId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<PlatformKey> req_entity = new HttpEntity<PlatformKey>(platform_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/platform/delete", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // delete
    @Test
    public void test97() throws Exception {
        SystemKey system_req = new SystemKey(systemId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<SystemKey> req_entity = new HttpEntity<SystemKey>(system_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/system/delete", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // user delete
    @Test
    public void test98() throws Exception {
        UserKey user_req = new UserKey(userId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<UserKey> req_entity = new HttpEntity<UserKey>(user_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user/delete", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test99() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<String> req_entity = new HttpEntity<String>("{}", requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user_group/not_found", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }

    // delete session user
    @Test
    public void z_test01() throws Exception {
        userMapper.deleteByPrimaryKey(UserBean.getUserId());
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
