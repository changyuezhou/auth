package com.system.auth.api;

import com.system.auth.auth.Auth;
import com.system.auth.bean.*;
import com.system.auth.dao.UserMapper;
import com.system.auth.model.Authority;
import com.system.auth.model.System;
import com.system.auth.model.User;
import com.system.auth.model.ext.AuthorityView;
import com.system.auth.model.request.AuthorityKey;
import com.system.auth.model.request.AuthorityListCondition;
import com.system.auth.model.request.SystemKey;
import com.system.auth.model.request.UserKey;
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

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorityAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Authority new_authority = new Authority();
    private static String name_prefix = "prefix_test_name";

    private static String userId = "";
    private static String userName = "prefix_test_name";

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
        user.setPassword("12345678901234567890");

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

    // add test
    @Test
    public void test21() throws Exception {
        new_authority.setAuthFId("");
        new_authority.setSystemId(systemId);
        new_authority.setAuthLevel(0);
        new_authority.setAuthName(name_prefix);
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

        new_authority.setAuthId(entity.getBody().getData().getAuthId());
        new_authority.setCreateTime(java.lang.System.currentTimeMillis());
        new_authority.setUpdateTime(java.lang.System.currentTimeMillis());
    }

    // query
    @Test
    public void test22() throws Exception {
        AuthorityKey auth_req = new AuthorityKey(new_authority.getAuthId());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<AuthorityKey> req_entity = new HttpEntity<AuthorityKey>(auth_req, requestHeaders);

        ResponseEntity<AuthorityQueryByPrimaryKeyResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/authority/query", req_entity,  AuthorityQueryByPrimaryKeyResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        AuthorityView auth_view = entity.getBody().getData();
        then(auth_view.getAuthId()).isEqualTo(new_authority.getAuthId());
        then(auth_view.getAuthName()).isEqualTo(new_authority.getAuthName());
        then(auth_view.getAuthFId()).isEqualTo(new_authority.getAuthId());
        then(auth_view.getSystemId()).isEqualTo(systemId);
        then(auth_view.getSystemName()).isEqualTo(systemName);
        then(auth_view.getCreateUserId()).isEqualTo(UserBean.getUserId());
        then(auth_view.getCreateUserName()).isEqualTo(UserBean.getUserName());
        then(auth_view.getCreateTime()/time_diff_base).isEqualTo(new_authority.getCreateTime()/time_diff_base);
        then(auth_view.getUpdateTime()/time_diff_base).isEqualTo(new_authority.getUpdateTime()/time_diff_base);
        then(auth_view.getDescription()).isEqualTo(new_authority.getDescription());
    }

    // update
    @Test
    public void test23() throws Exception {
        new_authority.setAuthName(name_prefix + "修改测试");
        new_authority.setDescription("修改");
        new_authority.setAuthFId(null);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<Authority> req_entity = new HttpEntity<Authority>(new_authority, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/authority/update", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        new_authority.setUpdateTime(java.lang.System.currentTimeMillis());
    }

    // list
    @Test
    public void test24() throws Exception {
        AuthorityListCondition condition = new AuthorityListCondition();
        condition.setAuthName(name_prefix);
        condition.setSystemId(systemId);
        condition.setCreateUserId(UserBean.getUserId());
        condition.setCreateUserName(UserBean.getUserName());
        condition.setPageSize(10);
        condition.setPageNum(0);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<AuthorityListCondition> req_entity = new HttpEntity<AuthorityListCondition>(condition, requestHeaders);

        ResponseEntity<AuthorityListByConditionResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/authority/list", req_entity,  AuthorityListByConditionResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        AuthorityView authority_view = entity.getBody().getData().get(0);
        then(authority_view.getSystemId()).isEqualTo(systemId);
        then(authority_view.getSystemName()).isEqualTo(systemName);
        then(authority_view.getAuthId()).isEqualTo(new_authority.getAuthId());
        then(authority_view.getAuthName()).isEqualTo(new_authority.getAuthName());
        then(authority_view.getCreateUserName()).isEqualTo(UserBean.getUserName());
        then(authority_view.getCreateUserId()).isEqualTo(UserBean.getUserId());
        then(authority_view.getCreateTime()/time_diff_base).isEqualTo(new_authority.getCreateTime()/time_diff_base);
        then(authority_view.getUpdateTime()/time_diff_base).isEqualTo(new_authority.getUpdateTime()/time_diff_base);
        then(authority_view.getDescription()).isEqualTo(new_authority.getDescription());
    }

    // delete
    @Test
    public void test25() throws Exception {
        AuthorityKey authority_req = new AuthorityKey(new_authority.getAuthId());

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
                "http://localhost:" + this.port + "/api/authority/not_found", req_entity,  OperationMessage.class);
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
}
