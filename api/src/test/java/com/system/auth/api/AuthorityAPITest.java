package com.system.auth.api;

import com.system.auth.bean.*;
import com.system.auth.model.Authority;
import com.system.auth.model.System;
import com.system.auth.model.User;
import com.system.auth.model.ext.AuthorityView;
import com.system.auth.model.request.AuthorityKey;
import com.system.auth.model.request.AuthorityListCondition;
import com.system.auth.model.request.SystemKey;
import com.system.auth.model.request.UserKey;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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
        ResponseEntity<UserAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/user/add", user,  UserAddResponseTest.class);
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
        new_system.setCreateUserId(userId);
        new_system.setDescription("测试");
        ResponseEntity<SystemAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/system/add", new_system,  SystemAddResponseTest.class);
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
        new_authority.setCreateUserId(userId);

        ResponseEntity<AuthorityAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/authority/add", new_authority,  AuthorityAddResponseTest.class);
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
        ResponseEntity<AuthorityQueryByPrimaryKeyResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/authority/query", auth_req,  AuthorityQueryByPrimaryKeyResponseTest.class);

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
        then(auth_view.getCreateUserId()).isEqualTo(userId);
        then(auth_view.getCreateUserName()).isEqualTo(userName);
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

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/authority/update", new_authority,  OperationMessage.class);
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
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<AuthorityListByConditionResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/authority/list", condition,  AuthorityListByConditionResponseTest.class);

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
        then(authority_view.getCreateUserName()).isEqualTo(userName);
        then(authority_view.getCreateUserId()).isEqualTo(userId);
        then(authority_view.getCreateTime()/time_diff_base).isEqualTo(new_authority.getCreateTime()/time_diff_base);
        then(authority_view.getUpdateTime()/time_diff_base).isEqualTo(new_authority.getUpdateTime()/time_diff_base);
        then(authority_view.getDescription()).isEqualTo(new_authority.getDescription());
    }

    // delete
    @Test
    public void test25() throws Exception {
        AuthorityKey authority_req = new AuthorityKey(new_authority.getAuthId());
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/authority/delete", authority_req,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // delete
    @Test
    public void test97() throws Exception {
        SystemKey user_req = new SystemKey(systemId);
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/system/delete", user_req,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // user delete
    @Test
    public void test98() throws Exception {
        UserKey user_req = new UserKey(userId);
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/user/delete", user_req,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test99() throws Exception {
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/authority/not_found", "",  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }
}
