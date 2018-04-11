package com.system.auth.api;

import com.system.auth.bean.*;
import com.system.auth.model.System;
import com.system.auth.model.User;
import com.system.auth.model.ext.SystemView;
import com.system.auth.model.request.SystemKey;
import com.system.auth.model.request.SystemListCondition;
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
public class SystemAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static System new_system = new System();
    private static String name_prefix = "prefix_test_name_测试";

    private static String userId = "";
    private static String userName = "prefix_test_name_测试";

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

    // add test
    @Test
    public void test22() throws Exception {
        new_system.setSystemName(name_prefix);
        new_system.setCreateUserId(userId);
        new_system.setDescription("测试");
        ResponseEntity<SystemAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/system/add", new_system,  SystemAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getSystemName()).isEqualTo(new_system.getSystemName());

        new_system.setCreateTime(java.lang.System.currentTimeMillis());
        new_system.setUpdateTime(java.lang.System.currentTimeMillis());
        new_system.setSystemId(entity.getBody().getData().getSystemId());
    }

    // query
    @Test
    public void test23() throws Exception {
        SystemKey system_req = new SystemKey(new_system.getSystemId());
        ResponseEntity<SystemQueryByPrimaryKeyResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/system/query", system_req,  SystemQueryByPrimaryKeyResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        SystemView system_view = entity.getBody().getData();
        then(system_view.getSystemId()).isEqualTo(new_system.getSystemId());
        then(system_view.getSystemName()).isEqualTo(new_system.getSystemName());
        then(system_view.getCreateUserId()).isEqualTo(userId);
        then(system_view.getCreateUserName()).isEqualTo(userName);
        then(system_view.getCreateTime()/time_diff_base).isEqualTo(new_system.getCreateTime()/time_diff_base);
        then(system_view.getUpdateTime()/time_diff_base).isEqualTo(new_system.getUpdateTime()/time_diff_base);
        then(system_view.getDescription()).isEqualTo(new_system.getDescription());
    }

    // update
    @Test
    public void test24() throws Exception {
        new_system.setSystemName(name_prefix + "修改测试");
        new_system.setDescription("修改");

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/system/update", new_system,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        new_system.setUpdateTime(java.lang.System.currentTimeMillis());
    }

    // list
    @Test
    public void test25() throws Exception {
        SystemListCondition condition = new SystemListCondition();
        condition.setSystemName(name_prefix);
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<SystemListByConditionResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/system/list", condition,  SystemListByConditionResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        SystemView system_view = entity.getBody().getData().get(0);
        then(system_view.getSystemId()).isEqualTo(new_system.getSystemId());
        then(system_view.getSystemName()).isEqualTo(new_system.getSystemName());
        then(system_view.getCreateUserName()).isEqualTo(userName);
        then(system_view.getCreateUserId()).isEqualTo(new_system.getCreateUserId());
        then(system_view.getCreateTime()/time_diff_base).isEqualTo(new_system.getCreateTime()/time_diff_base);
        then(system_view.getUpdateTime()/time_diff_base).isEqualTo(new_system.getUpdateTime()/time_diff_base);
        then(system_view.getDescription()).isEqualTo(new_system.getDescription());
    }

    // delete
    @Test
    public void test26() throws Exception {
        SystemKey user_req = new SystemKey(new_system.getSystemId());
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
                "http://localhost:" + this.port + "/system/not_found", "",  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }
}
