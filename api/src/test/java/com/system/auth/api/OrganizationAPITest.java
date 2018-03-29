package com.system.auth.api;

import com.system.auth.bean.*;
import com.system.auth.model.Organization;
import com.system.auth.model.Platform;
import com.system.auth.model.System;
import com.system.auth.model.User;
import com.system.auth.model.ext.OrganizationView;
import com.system.auth.model.request.*;
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

public class OrganizationAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Organization new_organization = new Organization();
    private static String name_prefix = "prefix_test_name";

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
        new_platform.setPlatformName(name_prefix);
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

    // add test
    @Test
    public void test21() throws Exception {
        new_organization.setPlatformId(platformId);
        new_organization.setOrganizationName(name_prefix);
        new_organization.setDescription("description");
        new_organization.setCreateUserId(userId);

        ResponseEntity<OrganizationAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization/add", new_organization,  OrganizationAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getOrganizationName()).isEqualTo(new_organization.getOrganizationName());

        new_organization.setOrganizationId(entity.getBody().getData().getOrganizationId());
        new_organization.setCreateTime(java.lang.System.currentTimeMillis());
        new_organization.setUpdateTime(java.lang.System.currentTimeMillis());
    }

    // query
    @Test
    public void test22() throws Exception {
        OrganizationKey organization_req = new OrganizationKey(new_organization.getOrganizationId());
        ResponseEntity<OrganizationQueryByPrimaryKeyResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization/query", organization_req,  OrganizationQueryByPrimaryKeyResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        OrganizationView organization_view = entity.getBody().getData();
        then(organization_view.getOrganizationId()).isEqualTo(new_organization.getOrganizationId());
        then(organization_view.getOrganizationName()).isEqualTo(new_organization.getOrganizationName());
        then(organization_view.getPlatformId()).isEqualTo(platformId);
        then(organization_view.getPlatformName()).isEqualTo(platformName);
        then(organization_view.getCreateUserId()).isEqualTo(userId);
        then(organization_view.getCreateUserName()).isEqualTo(userName);
        then(organization_view.getCreateTime()/time_diff_base).isEqualTo(new_organization.getCreateTime()/time_diff_base);
        then(organization_view.getUpdateTime()/time_diff_base).isEqualTo(new_organization.getUpdateTime()/time_diff_base);
        then(organization_view.getDescription()).isEqualTo(new_organization.getDescription());
    }

    // update
    @Test
    public void test23() throws Exception {
        new_organization.setOrganizationName(name_prefix + "修改测试");
        new_organization.setDescription("修改");

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization/update", new_organization,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        new_organization.setUpdateTime(java.lang.System.currentTimeMillis());
    }

    // list
    @Test
    public void test24() throws Exception {
        OrganizationListCondition condition = new OrganizationListCondition();
        condition.setOrganizationName(name_prefix);
        condition.setOrganizationId(new_organization.getOrganizationId());
        condition.setPlatformId(platformId);
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<OrganizationListByConditionResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization/list", condition,  OrganizationListByConditionResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        OrganizationView organization_view = entity.getBody().getData().get(0);
        then(organization_view.getPlatformId()).isEqualTo(platformId);
        then(organization_view.getPlatformName()).isEqualTo(platformName);
        then(organization_view.getOrganizationId()).isEqualTo(new_organization.getOrganizationId());
        then(organization_view.getOrganizationName()).isEqualTo(new_organization.getOrganizationName());
        then(organization_view.getCreateUserName()).isEqualTo(userName);
        then(organization_view.getCreateUserId()).isEqualTo(userId);
        then(organization_view.getCreateTime()/time_diff_base).isEqualTo(new_organization.getCreateTime()/time_diff_base);
        then(organization_view.getUpdateTime()/time_diff_base).isEqualTo(new_organization.getUpdateTime()/time_diff_base);
        then(organization_view.getDescription()).isEqualTo(new_organization.getDescription());
    }

    // delete
    @Test
    public void test25() throws Exception {
        OrganizationKey organization_req = new OrganizationKey(new_organization.getOrganizationId());
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization/delete", organization_req,  OperationMessage.class);
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
                "http://localhost:" + this.port + "/organization/not_found", "",  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }

}
