package com.system.auth.api;

import com.system.auth.auth.Auth;
import com.system.auth.bean.*;
import com.system.auth.dao.SessionMapper;
import com.system.auth.dao.UserMapper;
import com.system.auth.model.Session;
import com.system.auth.model.request.UserListCondition;
import com.system.auth.model.request.UserKey;
import com.system.auth.model.User;
import com.system.auth.model.ext.UserView;
import com.system.auth.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.*;
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

        /*
        Session session = new Session();
        session.setPlatformId(SessionBean.getPlatformId());
        session.setOpenId(SessionBean.getOpenId());
        session.setCode(SessionBean.getCode());
        session.setUserId(SessionBean.getUserId());
        session.setAccessToken(SessionBean.getAccessToken());
        session.setAuthToken(SessionBean.getAuthToken());
        session.setAuthTokenExpire(SessionBean.getAuthTokenExpire());
        session.setAuthTokenCreateTime(SessionBean.getAuthTokenCreateTime());
        session.setAccessTokenCreateTime(SessionBean.getAccessTokenCreateTime());
        session.setAccessTokenExpire(SessionBean.getAccessTokenExpire());
        session.setRefreshToken(SessionBean.getRefreshToken());
        session.setRefreshTokenCreateTime(SessionBean.getRefreshTokenCreateTime());
        session.setRefreshTokenExpire(SessionBean.getRefreshTokenExpire());
        session.setCodeCreateTime(SessionBean.getCodeCreateTime());
        session.setCodeExpire(SessionBean.getCodeExpire());
        session.setAccessTokenUsed(SessionBean.getAccessTokenUsed());
        session.setAuthTokenUsed(SessionBean.getAuthTokenUsed());
        session.setCodeUsed(SessionBean.getCodeUsed());
        session.setRefreshTokenUsed(SessionBean.getRefreshTokenUsed());

        sessionMapper.insert(session);
        */
    }

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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<User> req_entity = new HttpEntity<User>(new_user, requestHeaders);

        ResponseEntity<UserAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user/add", req_entity,  UserAddResponseTest.class);
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<UserKey> req_entity = new HttpEntity<UserKey>(user_req, requestHeaders);


        ResponseEntity<UserQueryByPrimaryKeyResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user/query", req_entity,  UserQueryByPrimaryKeyResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        UserView user_view = entity.getBody().getData();
        then(user_view.getUserId()).isEqualTo(new_user.getUserId());
        then(user_view.getUserName()).isEqualTo(new_user.getUserName());
        then(user_view.getCreateUserName()).isEqualTo(UserBean.getUserName());
        then(user_view.getCreateUserId()).isEqualTo(UserBean.getUserId());
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<User> req_entity = new HttpEntity<User>(new_user, requestHeaders);


        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user/update", req_entity,  OperationMessage.class);
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<UserListCondition> req_entity = new HttpEntity<UserListCondition>(condition, requestHeaders);

        ResponseEntity<UserListByConditionResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user/list", req_entity,  UserListByConditionResponseTest.class);

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
    public void test06() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "open_id=" + SessionBean.getOpenId());
        requestHeaders.add("Cookie", "access_token=" + SessionBean.getAccessToken());

        HttpEntity<String> req_entity = new HttpEntity<String>("{}", requestHeaders);

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/user/not_found", req_entity,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(OperationException.getServiceException());
        then(entity.getBody().getMsg()).isEqualTo(OperationException.getExceptionMsg());
    }

    // delete session user
    @Test
    public void z_test01() throws Exception {
        userMapper.deleteByPrimaryKey(UserBean.getUserId());
        /*
        Session session = new Session();
        session.setPlatformId(SessionBean.getPlatformId());
        session.setUserId(SessionBean.getUserId());

        sessionMapper.delete(session);
        */
    }
}
