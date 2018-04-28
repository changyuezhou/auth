package com.system.auth.api;

import com.system.auth.auth.Auth;
import com.system.auth.bean.*;
import com.system.auth.dao.UserMapper;
import com.system.auth.model.*;
import com.system.auth.model.System;
import com.system.auth.model.ext.OrganizationAuthorityView;
import com.system.auth.model.provider.OrganizationAuthorityBulkInsert;
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

    // add organization test
    @Test
    public void test04() throws Exception {
        Organization new_organization = new Organization();
        new_organization.setPlatformId(platformId);
        new_organization.setOrganizationName(organizationName);
        new_organization.setDescription("description");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<Organization> req_entity = new HttpEntity<Organization>(new_organization, requestHeaders);

        ResponseEntity<OrganizationAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/organization/add", req_entity,  OrganizationAddResponseTest.class);
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
        new_organization_auth.setAuthId(authId);
        new_organization_auth.setOrganizationId(organizationId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<OrganizationAuthority> req_entity = new HttpEntity<OrganizationAuthority>(new_organization_auth, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/organization_authority/add", req_entity,  OperationMessage.class);
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
        condition.setCreateUserId(UserBean.getUserId());
        condition.setCreateUserName(UserBean.getUserName());
        condition.setPageSize(10);
        condition.setPageNum(0);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<OrganizationAuthorityListCondition> req_entity = new HttpEntity<OrganizationAuthorityListCondition>(condition, requestHeaders);

        ResponseEntity<OrganizationAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/organization_authority/list", req_entity,  OrganizationAuthorityListResponseTest.class);

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
        then(organization_authority_view.getCreateUserName()).isEqualTo(UserBean.getUserName());
        then(organization_authority_view.getCreateUserId()).isEqualTo(UserBean.getUserId());
    }

    // delete
    @Test
    public void test23() throws Exception {
        OrganizationAuthorityKey organization_req = new OrganizationAuthorityKey();
        organization_req.setAuthId(authId);
        organization_req.setOrganizationId(organizationId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<OrganizationAuthorityKey> req_entity = new HttpEntity<OrganizationAuthorityKey>(organization_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/organization_authority/delete", req_entity,  OperationMessage.class);
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<OrganizationAuthorityBulkInsert> req_entity = new HttpEntity<OrganizationAuthorityBulkInsert>(organization_auths, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/organization_authority/bulk/add", req_entity,  OperationMessage.class);
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
        condition.setCreateUserId(UserBean.getUserId());
        condition.setCreateUserName(UserBean.getUserName());
        condition.setPageSize(10);
        condition.setPageNum(0);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<OrganizationAuthorityListCondition> req_entity = new HttpEntity<OrganizationAuthorityListCondition>(condition, requestHeaders);

        ResponseEntity<OrganizationAuthorityListResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/organization_authority/list", req_entity,  OrganizationAuthorityListResponseTest.class);

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
        then(organization_authority_view.getCreateUserName()).isEqualTo(UserBean.getUserName());
        then(organization_authority_view.getCreateUserId()).isEqualTo(UserBean.getUserId());
    }

    // bulk delete
    @Test
    public void test27() throws Exception {
        OrganizationAuthorityBulk organization_auths = new OrganizationAuthorityBulk();
        List<String> list = new ArrayList<String>();
        list.add(authId);
        organization_auths.setAuthIds(list);
        organization_auths.setOrganizationId(organizationId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<OrganizationAuthorityBulk> req_entity = new HttpEntity<OrganizationAuthorityBulk>(organization_auths, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/organization_authority/bulk/delete", req_entity,  OperationMessage.class);
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
        OrganizationKey organization_req = new OrganizationKey(organizationId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<OrganizationKey> req_entity = new HttpEntity<OrganizationKey>(organization_req, requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/organization/delete", req_entity,  OperationMessage.class);
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
                "http://localhost:" + this.port + "/api/organization_authority/not_found", req_entity,  OperationMessage.class);
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
