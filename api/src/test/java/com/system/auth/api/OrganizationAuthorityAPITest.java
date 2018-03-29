package com.system.auth.api;

import com.system.auth.bean.*;
import com.system.auth.model.*;
import com.system.auth.model.System;
import com.system.auth.model.ext.OrganizationAuthorityView;
import com.system.auth.model.provider.OrganizationAuthorityBulkInsert;
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

public class OrganizationAuthorityAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static OrganizationAuthority new_organization_auth = new OrganizationAuthority();

    private static String organizationId = "";
    private static String organizationName = "prefix_test_name";

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

    // add organization test
    @Test
    public void test04() throws Exception {
        Organization new_organization = new Organization();
        new_organization.setPlatformId(platformId);
        new_organization.setOrganizationName(organizationName);
        new_organization.setDescription("description");
        new_organization.setCreateUserId(userId);

        ResponseEntity<OrganizationAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization/add", new_organization,  OrganizationAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getOrganizationName()).isEqualTo(new_organization.getOrganizationName());

        organizationId = entity.getBody().getData().getOrganizationId();
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
        new_organization_auth.setAuthId(authId);
        new_organization_auth.setOrganizationId(organizationId);
        new_organization_auth.setCreateUserId(userId);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization_authority/add", new_organization_auth,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // list
    @Test
    public void test22() throws Exception {
        OrganizationAuthorityListCondition condition = new OrganizationAuthorityListCondition();
        condition.setOrganizationName(organizationName);
        condition.setOrganizationId(new_organization_auth.getOrganizationId());
        condition.setPlatformId(platformId);
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<OrganizationAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization_authority/list", condition,  OrganizationAuthorityListResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        OrganizationAuthorityView organization_authority_view = entity.getBody().getData().get(0);
        then(organization_authority_view.getPlatformId()).isEqualTo(platformId);
        then(organization_authority_view.getPlatformName()).isEqualTo(platformName);
        then(organization_authority_view.getOrganizationId()).isEqualTo(new_organization_auth.getOrganizationId());
        then(organization_authority_view.getOrganizationName()).isEqualTo(organizationName);
        then(organization_authority_view.getCreateUserName()).isEqualTo(userName);
        then(organization_authority_view.getCreateUserId()).isEqualTo(userId);
    }

    // delete
    @Test
    public void test23() throws Exception {
        OrganizationAuthorityKey organization_req = new OrganizationAuthorityKey();
        organization_req.setAuthId(authId);
        organization_req.setOrganizationId(organizationId);
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization_authority/delete", organization_req,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // add bulk test
    @Test
    public void test25() throws Exception {
        OrganizationAuthorityBulkInsert organization_auths = new OrganizationAuthorityBulkInsert();
        List<String> list = new ArrayList<String>();
        list.add(authId);
        organization_auths.setAuthIds(list);
        organization_auths.setOrganizationId(organizationId);
        organization_auths.setCreateUserId(userId);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization_authority/bulk/add", organization_auths,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    // list
    @Test
    public void test26() throws Exception {
        OrganizationAuthorityListCondition condition = new OrganizationAuthorityListCondition();
        condition.setOrganizationName(organizationName);
        condition.setOrganizationId(new_organization_auth.getOrganizationId());
        condition.setPlatformId(platformId);
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<OrganizationAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization_authority/list", condition,  OrganizationAuthorityListResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        OrganizationAuthorityView organization_authority_view = entity.getBody().getData().get(0);
        then(organization_authority_view.getPlatformId()).isEqualTo(platformId);
        then(organization_authority_view.getPlatformName()).isEqualTo(platformName);
        then(organization_authority_view.getOrganizationId()).isEqualTo(new_organization_auth.getOrganizationId());
        then(organization_authority_view.getOrganizationName()).isEqualTo(organizationName);
        then(organization_authority_view.getCreateUserName()).isEqualTo(userName);
        then(organization_authority_view.getCreateUserId()).isEqualTo(userId);
    }

    // bulk delete
    @Test
    public void test27() throws Exception {
        OrganizationAuthorityBulk organization_auths = new OrganizationAuthorityBulk();
        List<String> list = new ArrayList<String>();
        list.add(authId);
        organization_auths.setAuthIds(list);
        organization_auths.setOrganizationId(organizationId);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/organization_authority/bulk/delete", organization_auths,  OperationMessage.class);
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
        OrganizationKey organization_req = new OrganizationKey(organizationId);
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
                "http://localhost:" + this.port + "/organization_authority/not_found", "",  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }
}
