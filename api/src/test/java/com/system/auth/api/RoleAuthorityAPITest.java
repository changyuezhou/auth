package com.system.auth.api;

import com.system.auth.bean.*;
import com.system.auth.model.*;
import com.system.auth.model.System;
import com.system.auth.model.ext.GroupAuthorityView;
import com.system.auth.model.ext.RoleAuthorityView;
import com.system.auth.model.provider.RoleAuthorityBulkInsert;
import com.system.auth.model.request.*;
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

    // add platform test
    @Test
    public void test03() throws Exception {
        Platform new_platform = new Platform();

        new_platform.setSystemId(systemId);
        new_platform.setSecretKey("12345678");
        new_platform.setPlatformDomain("www");
        new_platform.setPlatformName(platformName);
        new_platform.setDescription("description");
        new_platform.setCreateUserId(userId);

        ResponseEntity<PlatformAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/platform/add", new_platform,  PlatformAddResponseTest.class);
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
        new_role.setCreateUserId(userId);

        ResponseEntity<RoleAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/role/add", new_role,  RoleAddResponseTest.class);
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
        new_authority.setCreateUserId(userId);

        ResponseEntity<AuthorityAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/authority/add", new_authority,  AuthorityAddResponseTest.class);
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
        new_role_auth.setCreateUserId(userId);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/role_authority/add", new_role_auth,  OperationMessage.class);
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
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<RoleAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/role_authority/list", condition,  RoleAuthorityListResponseTest.class);

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
        then(role_authority_view.getCreateUserName()).isEqualTo(userName);
        then(role_authority_view.getCreateUserId()).isEqualTo(userId);
    }

    // delete
    @Test
    public void test23() throws Exception {
        RoleAuthorityKey role_req = new RoleAuthorityKey();
        role_req.setAuthId(authId);
        role_req.setRoleId(roleId);
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/role_authority/delete", role_req,  OperationMessage.class);
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
        role_auths.setCreateUserId(userId);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/role_authority/bulk/add", role_auths,  OperationMessage.class);
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
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<RoleAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/role_authority/list", condition,  RoleAuthorityListResponseTest.class);

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
        then(role_authority_view.getCreateUserName()).isEqualTo(userName);
        then(role_authority_view.getCreateUserId()).isEqualTo(userId);
    }

    // bulk delete
    @Test
    public void test28() throws Exception {
        RoleAuthorityBulk role_auths = new RoleAuthorityBulk();
        List<String> list = new ArrayList<String>();
        list.add(authId);
        role_auths.setAuthIds(list);
        role_auths.setRoleId(roleId);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/role_authority/bulk/delete", role_auths,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // delete authority
    @Test
    public void test94() throws Exception {
        AuthorityKey authority_req = new AuthorityKey(authId);
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/authority/delete", authority_req,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // delete
    @Test
    public void test95() throws Exception {
        RoleKey role_req = new RoleKey(roleId);
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/role/delete", role_req,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // delete
    @Test
    public void test96() throws Exception {
        PlatformKey platform_req = new PlatformKey(platformId);
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/platform/delete", platform_req,  OperationMessage.class);
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
                "http://localhost:" + this.port + "/role_authority/not_found", "",  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }
}
