package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.dao.GroupMapper;
import com.system.auth.dao.UserGroupMapper;
import com.system.auth.dao.UserMapper;
import com.system.auth.model.UserGroup;
import com.system.auth.model.ext.UserGroupView;
import com.system.auth.model.provider.UserGroupBulkInsert;
import com.system.auth.model.request.GroupBulk;
import com.system.auth.model.request.UserGroupKey;
import com.system.auth.model.request.UserGroupListCondition;
import com.system.auth.model.request.UserGroupsBulk;
import com.system.auth.util.MybatisUtil;
import com.system.auth.util.SystemLogging;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/user_group")
public class UserGroupController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    UserGroupMapper userGroupMapper = session.getMapper(UserGroupMapper.class);

    @ApiOperation(value="创建用户组", notes="根据UserGroup对象创建用户组")
    @ApiImplicitParam(name = "user_group", value = "用户组详细信息", required = true, dataType = "UserGroup")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody UserGroup user_group, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_group), request, "", SystemLogging.getOperationStart());

        if (!IsUserGroupIdExists(user_group.getUserId(), user_group.getGroupId())) {
            throw new OperationException(OperationException.getUserInputException(), "user id:" + user_group.getUserId() + " or group id:" + user_group.getGroupId() + " is not exists");
        }

        user_group.setCreateUserId("U06EA2696AE3B4477B9AC6C28AB49A522");
        user_group.setCreateTime(java.lang.System.currentTimeMillis());
        user_group.setUpdateTime(java.lang.System.currentTimeMillis());
        userGroupMapper.insert(user_group);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_group), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="批量创建用户组", notes="根据UserGroup对象批量创建用户组")
    @ApiImplicitParam(name = "user_groups", value = "用户组详细信息", required = true, dataType = "UserGroupsBulk")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody UserGroupsBulk user_groups, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        if (null == user_groups.getGroupIds() || 0 >= user_groups.getGroupIds().size()) {
            throw new OperationException(OperationException.getUserInputException(), "group id list must not be empty");
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_groups), request, "", SystemLogging.getOperationStart());

        if (!IsGroupIdsExists(user_groups.getGroupIds())) {
            throw new OperationException(OperationException.getUserInputException(), "user id:" + user_groups.getUserId() + " or group id:" + mapper.writeValueAsString(user_groups.getGroupIds()) + " is not exists");
        }

        UserGroupBulkInsert bulk = new UserGroupBulkInsert();
        bulk.setUserId(user_groups.getUserId());
        bulk.setCreateUserId("U06EA2696AE3B4477B9AC6C28AB49A522");
        bulk.setGroupIds(user_groups.getGroupIds());

        userGroupMapper.insertByUserGroupList(bulk);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_groups), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="查询用户组列表", notes="根据条件查询组列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<UserGroupView> list(@Validated @RequestBody UserGroupListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<UserGroupView> user_group_list = userGroupMapper.selectBySelective(condition);
        PageInfo<UserGroupView> user_group_list_info = new PageInfo<UserGroupView>(user_group_list);
        QueryListMessage<UserGroupView> result = new QueryListMessage<UserGroupView>(0, "", user_group_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(user_group_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="删除用户组", notes="根据用户ID和组ID删除用户组")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody UserGroupKey user_group, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_group), request, "", SystemLogging.getOperationStart());

        userGroupMapper.deleteByPrimaryKey(user_group);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_group), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除用户组", notes="根据用户ID和多组ID批量删除用户组")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody UserGroupsBulk user_groups, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_groups), request, "", SystemLogging.getOperationStart());

        userGroupMapper.deleteByUserGroupIds(user_groups);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_groups), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    public Boolean IsUserGroupIdExists(String userId, String groupId) {
        if (false == IsUserIdExists(userId)) {
            return false;
        }

        if (false == IsGroupIdExists(groupId)) {
            return false;
        }

        return true;
    }

    public Boolean IsUserIdExists(String userId) {
        SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        if (null == userMapper.selectByPrimaryKey(userId)) {
            return false;
        }

        return true;
    }

    public Boolean IsGroupIdExists(String groupId) {
        SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
        GroupMapper groupMapper = session.getMapper(GroupMapper.class);

        if (null == groupMapper.selectByPrimaryKey(groupId)) {
            return false;
        }

        return true;
    }

    public Boolean IsGroupIdsExists(List<String> groupIds) {
        SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();

        GroupMapper groupMapper = session.getMapper(GroupMapper.class);
        GroupBulk groups = new GroupBulk();
        groups.setGroupIds(groupIds);
        List<String> queryGroupIds = groupMapper.selectByGroupIds(groups);
        if (null == queryGroupIds || groupIds.size() > queryGroupIds.size()) {
            return false;
        }

        return true;
    }
}
