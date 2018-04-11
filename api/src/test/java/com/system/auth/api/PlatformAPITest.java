package com.system.auth.api;

import com.system.auth.bean.*;
import com.system.auth.model.Platform;
import com.system.auth.model.System;
import com.system.auth.model.User;
import com.system.auth.model.ext.PlatformView;
import com.system.auth.model.request.PlatformKey;
import com.system.auth.model.request.PlatformListCondition;
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
public class PlatformAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Platform new_platform = new Platform();
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
    public void test23() throws Exception {
        new_platform.setPlatformId("");
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

        new_platform.setPlatformId(entity.getBody().getData().getPlatformId());
        new_platform.setCreateTime(java.lang.System.currentTimeMillis());
        new_platform.setUpdateTime(java.lang.System.currentTimeMillis());
    }

    // query
    @Test
    public void test24() throws Exception {
        PlatformKey platform_req = new PlatformKey(new_platform.getPlatformId());
        ResponseEntity<PlatformQueryByPrimaryKeyResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/platform/query", platform_req,  PlatformQueryByPrimaryKeyResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        PlatformView platform_view = entity.getBody().getData();
        then(platform_view.getPlatformId()).isEqualTo(new_platform.getPlatformId());
        then(platform_view.getPlatformName()).isEqualTo(new_platform.getPlatformName());
        then(platform_view.getPlatformDomain()).isEqualTo(new_platform.getPlatformDomain());
        then(platform_view.getSecretKey()).isEqualTo(new_platform.getSecretKey());
        then(platform_view.getSystemId()).isEqualTo(new_platform.getSystemId());
        then(platform_view.getSystemName()).isEqualTo(systemName);
        then(platform_view.getCreateUserId()).isEqualTo(userId);
        then(platform_view.getCreateUserName()).isEqualTo(userName);
        then(platform_view.getCreateTime()/time_diff_base).isEqualTo(new_platform.getCreateTime()/time_diff_base);
        then(platform_view.getUpdateTime()/time_diff_base).isEqualTo(new_platform.getUpdateTime()/time_diff_base);
        then(platform_view.getDescription()).isEqualTo(new_platform.getDescription());
    }

    // update
    @Test
    public void test25() throws Exception {
        new_platform.setPlatformName(name_prefix + "修改测试");
        new_platform.setDescription("修改");

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/platform/update", new_platform,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        new_platform.setUpdateTime(java.lang.System.currentTimeMillis());
    }

    // list
    @Test
    public void test26() throws Exception {
        PlatformListCondition condition = new PlatformListCondition();
        condition.setSystemName(systemName);
        condition.setPlatformName(name_prefix);
        condition.setPlatformId(new_platform.getPlatformId());
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<PlatformListByConditionResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/platform/list", condition,  PlatformListByConditionResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        PlatformView platform_view = entity.getBody().getData().get(0);
        then(platform_view.getPlatformId()).isEqualTo(new_platform.getPlatformId());
        then(platform_view.getPlatformName()).isEqualTo(new_platform.getPlatformName());
        then(platform_view.getPlatformDomain()).isEqualTo(new_platform.getPlatformDomain());
        then(platform_view.getSecretKey()).isEqualTo(new_platform.getSecretKey());
        then(platform_view.getSystemId()).isEqualTo(systemId);
        then(platform_view.getSystemName()).isEqualTo(systemName);
        then(platform_view.getCreateUserName()).isEqualTo(userName);
        then(platform_view.getCreateUserId()).isEqualTo(new_platform.getCreateUserId());
        then(platform_view.getCreateTime()/time_diff_base).isEqualTo(new_platform.getCreateTime()/time_diff_base);
        then(platform_view.getUpdateTime()/time_diff_base).isEqualTo(new_platform.getUpdateTime()/time_diff_base);
        then(platform_view.getDescription()).isEqualTo(new_platform.getDescription());
    }

    // delete
    @Test
    public void test27() throws Exception {
        PlatformKey platform_req = new PlatformKey(new_platform.getPlatformId());
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
                "http://localhost:" + this.port + "/platform/not_found", "",  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }
}
