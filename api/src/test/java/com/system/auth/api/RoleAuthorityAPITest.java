package com.system.auth.api;

import com.system.auth.auth.Auth;
import com.system.auth.bean.*;
import com.system.auth.dao.UserMapper;
import com.system.auth.model.*;
import com.system.auth.model.System;
import com.system.auth.model.ext.GroupAuthorityView;
import com.system.auth.model.ext.RoleAuthorityView;
import com.system.auth.model.provider.RoleAuthorityBulkInsert;
import com.system.auth.model.request.*;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class RoleAuthorityAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static RoleAuthority new_role_auth = new RoleAuthority();

    private static String roleId = "";
    private static String roleName = "prefix_test_name";

    private static String authId = "";
    private static String authName = "prefix_test_name";

    private static String userId = "";
    private static String userName = "prefix_test_name";

    private static String platformId = "";
    private static String platformName = "prefix_test_name";

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

    // add platform test
    @Test
    public void test03() throws Exception {
        Platform new_platform = new Platform();

        new_platform.setSystemId(systemId);
        new_platform.setSecretKey("12345678");
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
    // add role test
    @Test
    public void test04() throws Exception {
        Role new_role = new Role();
        new_role.setPlatformId(platformId);
        new_role.setRoleName(roleName);
        new_role.setDescription("description");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<Role> req_entity = new HttpEntity<Role>(new_role, requestHeaders);

        ResponseEntity<RoleAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/role/add", req_entity,  RoleAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getRoleName()).isEqualTo(new_role.getRoleName());

        roleId = entity.getBody().getData().getRoleId();
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

    // add test
    @Test
    public void test21() throws Exception {
        new_role_auth.setAuthId(authId);
        new_role_auth.setRoleId(roleId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<RoleAuthority> req_entity = new HttpEntity<RoleAuthority>(new_role_auth, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/role_authority/add", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // list
    @Test
    public void test22() throws Exception {
        RoleAuthorityListCondition condition = new RoleAuthorityListCondition();
        condition.setRoleName(roleName);
        condition.setRoleId(new_role_auth.getRoleId());
        condition.setPlatformId(platformId);
        condition.setCreateUserId(UserBean.getUserId());
        condition.setCreateUserName(UserBean.getUserName());
        condition.setPageSize(10);
        condition.setPageNum(0);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<RoleAuthorityListCondition> req_entity = new HttpEntity<RoleAuthorityListCondition>(condition, requestHeaders);

        ResponseEntity<RoleAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/role_authority/list", req_entity,  RoleAuthorityListResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        RoleAuthorityView role_authority_view = entity.getBody().getData().get(0);
        then(role_authority_view.getPlatformId()).isEqualTo(platformId);
        then(role_authority_view.getPlatformName()).isEqualTo(platformName);
        then(role_authority_view.getRoleId()).isEqualTo(new_role_auth.getRoleId());
        then(role_authority_view.getRoleName()).isEqualTo(roleName);
        then(role_authority_view.getCreateUserName()).isEqualTo(UserBean.getUserName());
        then(role_authority_view.getCreateUserId()).isEqualTo(UserBean.getUserId());
    }

    // delete
    @Test
    public void test23() throws Exception {
        RoleAuthorityKey role_req = new RoleAuthorityKey();
        role_req.setAuthId(authId);
        role_req.setRoleId(roleId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<RoleAuthorityKey> req_entity = new HttpEntity<RoleAuthorityKey>(role_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/role_authority/delete", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // add bulk test
    @Test
    public void test26() throws Exception {
        RoleAuthorityBulkInsert role_auths = new RoleAuthorityBulkInsert();
        List<String> list = new ArrayList<String>();
        list.add(authId);
        role_auths.setAuthIds(list);
        role_auths.setRoleId(roleId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<RoleAuthorityBulkInsert> req_entity = new HttpEntity<RoleAuthorityBulkInsert>(role_auths, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/role_authority/bulk/add", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test27() throws Exception {
        RoleAuthorityListCondition condition = new RoleAuthorityListCondition();
        condition.setRoleName(roleName);
        condition.setRoleId(new_role_auth.getRoleId());
        condition.setPlatformId(platformId);
        condition.setCreateUserId(UserBean.getUserId());
        condition.setCreateUserName(UserBean.getUserName());
        condition.setPageSize(10);
        condition.setPageNum(0);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<RoleAuthorityListCondition> req_entity = new HttpEntity<RoleAuthorityListCondition>(condition, requestHeaders);

        ResponseEntity<RoleAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/role_authority/list", req_entity,  RoleAuthorityListResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        RoleAuthorityView role_authority_view = entity.getBody().getData().get(0);
        then(role_authority_view.getPlatformId()).isEqualTo(platformId);
        then(role_authority_view.getPlatformName()).isEqualTo(platformName);
        then(role_authority_view.getRoleId()).isEqualTo(new_role_auth.getRoleId());
        then(role_authority_view.getRoleName()).isEqualTo(roleName);
        then(role_authority_view.getCreateUserName()).isEqualTo(UserBean.getUserName());
        then(role_authority_view.getCreateUserId()).isEqualTo(UserBean.getUserId());
    }

    // bulk delete
    @Test
    public void test28() throws Exception {
        RoleAuthorityBulk role_auths = new RoleAuthorityBulk();
        List<String> list = new ArrayList<String>();
        list.add(authId);
        role_auths.setAuthIds(list);
        role_auths.setRoleId(roleId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<RoleAuthorityBulk> req_entity = new HttpEntity<RoleAuthorityBulk>(role_auths, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/role_authority/bulk/delete", req_entity,  OperationMessage.class);
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
        RoleKey role_req = new RoleKey(roleId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<RoleKey> req_entity = new HttpEntity<RoleKey>(role_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/role/delete", req_entity,  OperationMessage.class);
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
                "http://localhost:" + this.port + "/api/role_authority/not_found", req_entity,  OperationMessage.class);
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
