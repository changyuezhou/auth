package com.system.auth.api;

import com.system.auth.bean.GroupAddResponseTest;
import com.system.auth.bean.GroupListByConditionResponseTest;
import com.system.auth.bean.GroupQueryByPrimaryKeyResponseTest;
import com.system.auth.bean.OperationMessage;
import com.system.auth.model.Group;
import com.system.auth.model.ext.GroupView;
import com.system.auth.model.request.GroupKey;
import com.system.auth.model.request.GroupListCondition;
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
public class GroupAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Group new_group = new Group();
    private static String name_prefix = "prefix_test_name";

    private static String userId = "U06EA2696AE3B4477B9AC6C28AB49A522";
    private static String userName = "admin@system.com";

    private static String platformId = "P386ADA9F63B54EECB2B49663043CC744";
    private static String platformName = "VPON RTB竞价平台";

    private static int time_diff_base = 10000;

    @BeforeClass
    public static void setUp() {
        new_group.setPlatformId(platformId);
        new_group.setGroupName(name_prefix + "_测试");
        new_group.setDescription("description");
    }

    // add test
    @Test
    public void test01() throws Exception {
        ResponseEntity<GroupAddResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group/add", new_group,  GroupAddResponseTest.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
        then(entity.getBody().getData().getGroupName()).isEqualTo(new_group.getGroupName());

        new_group.setGroupId(entity.getBody().getData().getGroupId());
        new_group.setCreateTime(java.lang.System.currentTimeMillis());
        new_group.setUpdateTime(java.lang.System.currentTimeMillis());
        new_group.setCreateUserId(userId);
    }

    // query
    @Test
    public void test02() throws Exception {
        GroupKey platform_req = new GroupKey(new_group.getGroupId());
        ResponseEntity<GroupQueryByPrimaryKeyResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group/query", platform_req,  GroupQueryByPrimaryKeyResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        GroupView group_view = entity.getBody().getData();
        then(group_view.getGroupId()).isEqualTo(new_group.getGroupId());
        then(group_view.getGroupName()).isEqualTo(new_group.getGroupName());
        then(group_view.getPlatformId()).isEqualTo(platformId);
        then(group_view.getPlatformName()).isEqualTo(platformName);
        then(group_view.getCreateUserId()).isEqualTo(userId);
        then(group_view.getCreateUserName()).isEqualTo(userName);
        then(group_view.getCreateTime()/time_diff_base).isEqualTo(new_group.getCreateTime()/time_diff_base);
        then(group_view.getUpdateTime()/time_diff_base).isEqualTo(new_group.getUpdateTime()/time_diff_base);
        then(group_view.getDescription()).isEqualTo(new_group.getDescription());
    }

    // update
    @Test
    public void test03() throws Exception {
        new_group.setGroupName(name_prefix + "修改测试");
        new_group.setDescription("修改");

        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group/update", new_group,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        new_group.setUpdateTime(java.lang.System.currentTimeMillis());
    }

    // list
    @Test
    public void test04() throws Exception {
        GroupListCondition condition = new GroupListCondition();
        condition.setGroupName(name_prefix);
        condition.setGroupId(new_group.getGroupId());
        condition.setPlatformId(platformId);
        condition.setCreateUserId(userId);
        condition.setCreateUserName(userName);
        condition.setPageSize(10);
        condition.setPageNum(0);

        ResponseEntity<GroupListByConditionResponseTest> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group/list", condition,  GroupListByConditionResponseTest.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");

        then(entity.getBody().getData().size()).isEqualTo(1);

        GroupView platform_view = entity.getBody().getData().get(0);
        then(platform_view.getPlatformId()).isEqualTo(platformId);
        then(platform_view.getPlatformName()).isEqualTo(platformName);
        then(platform_view.getGroupId()).isEqualTo(new_group.getGroupId());
        then(platform_view.getGroupName()).isEqualTo(new_group.getGroupName());
        then(platform_view.getCreateUserName()).isEqualTo(userName);
        then(platform_view.getCreateUserId()).isEqualTo(userId);
        then(platform_view.getCreateTime()/time_diff_base).isEqualTo(new_group.getCreateTime()/time_diff_base);
        then(platform_view.getUpdateTime()/time_diff_base).isEqualTo(new_group.getUpdateTime()/time_diff_base);
        then(platform_view.getDescription()).isEqualTo(new_group.getDescription());
    }

    // delete
    @Test
    public void test05() throws Exception {
        GroupKey group_req = new GroupKey(new_group.getGroupId());
        ResponseEntity<OperationMessage> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/group/delete", group_req,  OperationMessage.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }
}
