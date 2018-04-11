package com.system.auth.api;

import com.system.auth.bean.*;
import com.system.auth.model.*;
import com.system.auth.model.System;
import com.system.auth.model.ext.GroupAuthorityView;
import com.system.auth.model.provider.GroupAuthorityBulkInsert;
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

public class GroupAuthorityAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static GroupAuthority new_group_auth = new GroupAuthority();

    private static String groupId = "";
    private static String groupName = "prefix_test_name";

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

    // add group test
    @Test
    public void test04() throws Exception {
        Group new_group = new Group();
        new_group.setPlatformId(platformId);
        new_group.setGroupName(groupName);
        new_group.setDescription("description");
        new_group.setCreateUserId(userId);

        ResponseEntity<GroupAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group/add", new_group,  GroupAddResponseTest.class);
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
        new_group_auth.setAuthId(authId);
        new_group_auth.setGroupId(groupId);
        new_group_auth.setCreateUserId(userId);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group_authority/add", new_group_auth,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // list
    @Test
    public void test22() throws Exception {
        GroupAuthorityListCondition condition = new GroupAuthorityListCondition();
        condition.setGroupName(groupName);
        condition.setGroupId(new_group_auth.getGroupId());
        condition.setPlatformId(platformId);
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<GroupAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group_authority/list", condition,  GroupAuthorityListResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        GroupAuthorityView organization_authority_view = entity.getBody().getData().get(0);
        then(organization_authority_view.getPlatformId()).isEqualTo(platformId);
        then(organization_authority_view.getPlatformName()).isEqualTo(platformName);
        then(organization_authority_view.getGroupId()).isEqualTo(new_group_auth.getGroupId());
        then(organization_authority_view.getGroupName()).isEqualTo(groupName);
        then(organization_authority_view.getCreateUserName()).isEqualTo(userName);
        then(organization_authority_view.getCreateUserId()).isEqualTo(userId);
    }


    // delete
    @Test
    public void test23() throws Exception {
        GroupAuthorityKey group_req = new GroupAuthorityKey();
        group_req.setAuthId(authId);
        group_req.setGroupId(groupId);
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group_authority/delete", group_req,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // add bulk test
    @Test
    public void test26() throws Exception {
        GroupAuthorityBulkInsert group_auths = new GroupAuthorityBulkInsert();
        List<String> list = new ArrayList<String>();
        list.add(authId);
        group_auths.setAuthIds(list);
        group_auths.setGroupId(groupId);
        group_auths.setCreateUserId(userId);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group_authority/bulk/add", group_auths,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // list
    @Test
    public void test27() throws Exception {
        GroupAuthorityListCondition condition = new GroupAuthorityListCondition();
        condition.setGroupName(groupName);
        condition.setGroupId(new_group_auth.getGroupId());
        condition.setPlatformId(platformId);
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<GroupAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group_authority/list", condition,  GroupAuthorityListResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        GroupAuthorityView group_authority_view = entity.getBody().getData().get(0);
        then(group_authority_view.getPlatformId()).isEqualTo(platformId);
        then(group_authority_view.getPlatformName()).isEqualTo(platformName);
        then(group_authority_view.getGroupId()).isEqualTo(new_group_auth.getGroupId());
        then(group_authority_view.getGroupName()).isEqualTo(groupName);
        then(group_authority_view.getCreateUserName()).isEqualTo(userName);
        then(group_authority_view.getCreateUserId()).isEqualTo(userId);
    }

    // bulk delete
    @Test
    public void test28() throws Exception {
        GroupAuthorityBulk group_auths = new GroupAuthorityBulk();
        List<String> list = new ArrayList<String>();
        list.add(authId);
        group_auths.setAuthIds(list);
        group_auths.setGroupId(groupId);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group_authority/bulk/delete", group_auths,  OperationMessage.class);
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
        GroupKey group_req = new GroupKey(groupId);
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group/delete", group_req,  OperationMessage.class);
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
                "http://localhost:" + this.port + "/group_authority/not_found", "",  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }
}
