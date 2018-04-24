package com.system.auth.controller;

import com.system.auth.dao.*;
import com.system.auth.model.UserRole;
import com.system.auth.model.ext.UserRoleView;
import com.system.auth.model.provider.UserRoleBulkInsert;
import com.system.auth.model.request.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
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
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/api/user_role")
public class UserRoleController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    UserRoleMapper userRoleMapper = session.getMapper(UserRoleMapper.class);

    @ApiOperation(value="创建用户角色", notes="根据UserRole对象创建用户角色")
    @ApiImplicitParam(name = "user_role", value = "用户角色详细信息", required = true, dataType = "UserRole")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody UserRole user_role, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_role), request, "", SystemLogging.getOperationStart());

        if (!IsUserRoleIdExists(user_role.getUserId(), user_role.getRoleId())) {
            throw new OperationException(OperationException.getUserInputException(), "user id:" + user_role.getUserId() + " or role id:" + user_role.getRoleId() + " is not exists");
        }

        user_role.setCreateTime(java.lang.System.currentTimeMillis());
        user_role.setUpdateTime(java.lang.System.currentTimeMillis());
        userRoleMapper.insert(user_role);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_role), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="批量创建用户角色", notes="根据UserRoleBulk对象批量创建用户角色")
    @ApiImplicitParam(name = "user_roles", value = "用户角色详细信息", required = true, dataType = "UserRoleBulk")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody UserRoleBulk user_roles, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        if (null == user_roles.getRoleIds() || 0 >= user_roles.getRoleIds().size()) {
            throw new OperationException(OperationException.getUserInputException(), "role id list must not be empty");
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_roles), request, "", SystemLogging.getOperationStart());

        if (!IsUserIdExists(user_roles.getUserId())) {
            throw new OperationException(OperationException.getUserInputException(),"user id:" + user_roles.getUserId() + " is not exists");
        }

        if (!IsRoleIdsExists(user_roles.getRoleIds())) {
            throw new OperationException(OperationException.getUserInputException(), "group id:" + mapper.writeValueAsString(user_roles.getRoleIds()) + " is not exists");
        }

        UserRoleBulkInsert bulk = new UserRoleBulkInsert();
        bulk.setUserId(user_roles.getUserId());
        bulk.setCreateUserId("U06EA2696AE3B4477B9AC6C28AB49A522");
        bulk.setRoleIds(user_roles.getRoleIds());

        userRoleMapper.insertByUserRoleList(bulk);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_roles), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="查询用户角色列表", notes="根据条件查询角色列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<UserRoleView> list(@Validated @RequestBody UserRoleListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<UserRoleView> user_role_list = userRoleMapper.selectBySelective(condition);
        PageInfo<UserRoleView> user_role_list_info = new PageInfo<UserRoleView>(user_role_list);
        QueryListMessage<UserRoleView> result = new QueryListMessage<UserRoleView>(0, "", user_role_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(user_role_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="删除用户角色", notes="根据用户ID和角色ID删除用户角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody UserRoleKey user_role, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_role), request, "", SystemLogging.getOperationStart());

        userRoleMapper.deleteByPrimaryKey(user_role);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_role), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除用户角色", notes="根据用户ID和多角色ID批量删除用户角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody UserRoleBulk user_role, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_role), request, "", SystemLogging.getOperationStart());

        userRoleMapper.deleteByUserRoleIds(user_role);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_role), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    public Boolean IsUserRoleIdExists(String userId, String roleId) {
        if (false == IsUserIdExists(userId)) {
            return false;
        }

        if (false == IsRoleIdExists(roleId)) {
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

    public Boolean IsRoleIdExists(String roleId) {
        SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
        RoleMapper roleMapper = session.getMapper(RoleMapper.class);

        if (null == roleMapper.selectByPrimaryKey(roleId)) {
            return false;
        }

        return true;
    }

    public Boolean IsRoleIdsExists(List<String> roleIds) {
        SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();

        RoleMapper roleMapper = session.getMapper(RoleMapper.class);
        RoleBulk roles = new RoleBulk();
        roles.setRoleIds(roleIds);
        List<String> queryRoleIds = roleMapper.selectByRoleIds(roles);
        if (null == queryRoleIds || roleIds.size() > queryRoleIds.size()) {
            return false;
        }

        return true;
    }
}
