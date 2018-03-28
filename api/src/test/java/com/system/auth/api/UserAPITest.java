package com.system.auth.api;

import com.system.auth.bean.*;
import com.system.auth.model.request.UserListCondition;
import com.system.auth.model.request.UserKey;
import com.system.auth.model.User;
import com.system.auth.model.ext.UserView;
import org.junit.*;
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

public class UserAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static User new_user = new User();
    private static String name_prefix = "prefix_test_name";

    private static int time_diff_base = 10000;

    // add test
    @Test
    public void test01() throws Exception {
        new_user.setUserId("");
        new_user.setUserName(name_prefix + "_测试");
        new_user.setDescription("description");
        new_user.setStatus(1);
        new_user.setContactName("contact name");
        new_user.setMobileNumber("138000000000");
        new_user.setPassword("12345678901234567890");

        ResponseEntity<UserAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/user/add", new_user,  UserAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getUserName()).isEqualTo(new_user.getUserName());

        new_user.setCreateTime(System.currentTimeMillis());
        new_user.setUpdateTime(System.currentTimeMillis());
        new_user.setUserId(entity.getBody().getData().getUserId());
        new_user.setCreateUserId(entity.getBody().getData().getUserId());
    }

    // query
    @Test
    public void test02() throws Exception {
        UserKey user_req = new UserKey(new_user.getUserId());
        ResponseEntity<UserQueryByPrimaryKeyResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/user/query", user_req,  UserQueryByPrimaryKeyResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        UserView user_view = entity.getBody().getData();
        then(user_view.getUserId()).isEqualTo(new_user.getUserId());
        then(user_view.getUserName()).isEqualTo(new_user.getUserName());
        then(user_view.getCreateUserName()).isEqualTo(new_user.getUserName());
        then(user_view.getCreateUserId()).isEqualTo(new_user.getUserId());
        then(user_view.getStatus()).isEqualTo(new_user.getStatus());
        then(user_view.getContactName()).isEqualTo(new_user.getContactName());
        then(user_view.getMobileNumber()).isEqualTo(new_user.getMobileNumber());
        then(user_view.getCreateTime()/time_diff_base).isEqualTo(new_user.getCreateTime()/time_diff_base);
        then(user_view.getUpdateTime()/time_diff_base).isEqualTo(new_user.getUpdateTime()/time_diff_base);
        then(user_view.getDescription()).isEqualTo(new_user.getDescription());
    }

    // update
    @Test
    public void test03() throws Exception {
        new_user.setUserName(name_prefix + "修改测试");
        new_user.setDescription("修改");

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/user/update", new_user,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        new_user.setUpdateTime(System.currentTimeMillis());
    }

    // list
    @Test
    public void test04() throws Exception {
        UserListCondition condition = new UserListCondition();
        condition.setUserName(name_prefix);
        condition.setMobileNumber(new_user.getMobileNumber());
        condition.setContactName(new_user.getContactName());
        condition.setUserId(new_user.getUserId());
        condition.setStatus(new_user.getStatus());
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<UserListByConditionResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/user/list", condition,  UserListByConditionResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        UserView user_view = entity.getBody().getData().get(0);
        then(user_view.getUserId()).isEqualTo(new_user.getUserId());
        then(user_view.getUserName()).isEqualTo(new_user.getUserName());
        then(user_view.getCreateUserName()).isEqualTo(new_user.getUserName());
        then(user_view.getCreateUserId()).isEqualTo(new_user.getUserId());
        then(user_view.getStatus()).isEqualTo(new_user.getStatus());
        then(user_view.getContactName()).isEqualTo(new_user.getContactName());
        then(user_view.getMobileNumber()).isEqualTo(new_user.getMobileNumber());
        then(user_view.getCreateTime()/time_diff_base).isEqualTo(new_user.getCreateTime()/time_diff_base);
        then(user_view.getUpdateTime()/time_diff_base).isEqualTo(new_user.getUpdateTime()/time_diff_base);
        then(user_view.getDescription()).isEqualTo(new_user.getDescription());
    }

    // delete
    @Test
    public void test05() throws Exception {
        UserKey user_req = new UserKey(new_user.getUserId());
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/user/delete", user_req,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test06() throws Exception {
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/user/not_found", "",  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }
}
