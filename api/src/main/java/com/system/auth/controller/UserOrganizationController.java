package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.dao.OrganizationMapper;
import com.system.auth.dao.UserMapper;
import com.system.auth.dao.UserOrganizationMapper;
import com.system.auth.model.UserOrganization;
import com.system.auth.model.ext.UserOrganizationView;
import com.system.auth.model.provider.UserOrganizationBulkInsert;
import com.system.auth.model.request.OrganizationBulk;
import com.system.auth.model.request.UserOrganizationBulk;
import com.system.auth.model.request.UserOrganizationKey;
import com.system.auth.model.request.UserOrganizationListCondition;
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
@RequestMapping(value = "/user_organization")
public class UserOrganizationController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    UserOrganizationMapper userOrganizationMapper = session.getMapper(UserOrganizationMapper.class);

    @ApiOperation(value="创建用户组织", notes="根据UserOrganization对象创建用户组织")
    @ApiImplicitParam(name = "user_organization", value = "用户组织详细信息", required = true, dataType = "UserOrganization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody UserOrganization user_organization, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_organization), request, "", SystemLogging.getOperationStart());

        if (!IsUserOrganizationIdExists(user_organization.getUserId(), user_organization.getOrganizationId())) {
            throw new OperationException(OperationException.getUserInputException(), "user id:" + user_organization.getUserId() + " or organization id:" + user_organization.getOrganizationId() + " is not exists");
        }

        user_organization.setCreateTime(java.lang.System.currentTimeMillis());
        user_organization.setUpdateTime(java.lang.System.currentTimeMillis());
        userOrganizationMapper.insert(user_organization);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_organization), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="批量创建用户组织", notes="根据UserOrganization对象批量创建用户组织")
    @ApiImplicitParam(name = "user_organizations", value = "用户组织详细信息", required = true, dataType = "UserOrganizationsBulk")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody UserOrganizationBulk user_organizations, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        if (null == user_organizations.getOrganizationIds() || 0 >= user_organizations.getOrganizationIds().size()) {
            throw new OperationException(OperationException.getUserInputException(), "organization id list must not be empty");
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_organizations), request, "", SystemLogging.getOperationStart());

        if (!IsUserIdExists(user_organizations.getUserId())) {
            throw new OperationException(OperationException.getUserInputException(),"user id:" + user_organizations.getUserId() + " is not exists");
        }

        if (!IsOrganizationIdsExists(user_organizations.getOrganizationIds())) {
            throw new OperationException(OperationException.getUserInputException(), "organization id:" + mapper.writeValueAsString(user_organizations.getOrganizationIds()) + " is not exists");
        }

        UserOrganizationBulkInsert bulk = new UserOrganizationBulkInsert();
        bulk.setUserId(user_organizations.getUserId());
        bulk.setCreateUserId("U06EA2696AE3B4477B9AC6C28AB49A522");
        bulk.setOrganizationIds(user_organizations.getOrganizationIds());

        userOrganizationMapper.insertByUserOrganizationList(bulk);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_organizations), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="查询用户组织列表", notes="根据条件查询组织列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<UserOrganizationView> list(@Validated @RequestBody UserOrganizationListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<UserOrganizationView> user_organization_list = userOrganizationMapper.selectBySelective(condition);
        PageInfo<UserOrganizationView> user_organization_list_info = new PageInfo<UserOrganizationView>(user_organization_list);
        QueryListMessage<UserOrganizationView> result = new QueryListMessage<UserOrganizationView>(0, "", user_organization_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(user_organization_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="删除用户组织", notes="根据用户ID和组织ID删除用户组织")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody UserOrganizationKey user_organization, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_organization), request, "", SystemLogging.getOperationStart());

        userOrganizationMapper.deleteByPrimaryKey(user_organization);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_organization), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除用户组织", notes="根据用户ID和多组织ID批量删除用户组织")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody UserOrganizationBulk user_organizations, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_organizations), request, "", SystemLogging.getOperationStart());

        userOrganizationMapper.deleteByUserOrganizationIds(user_organizations);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user_organizations), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    public Boolean IsUserOrganizationIdExists(String userId, String organizationId) {
        if (false == IsUserIdExists(userId)) {
            return false;
        }

        if (false == IsOrganizationIdExists(organizationId)) {
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

    public Boolean IsOrganizationIdExists(String organizationId) {
        SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
        OrganizationMapper organizationMapper = session.getMapper(OrganizationMapper.class);

        if (null == organizationMapper.selectByPrimaryKey(organizationId)) {
            return false;
        }

        return true;
    }

    public Boolean IsOrganizationIdsExists(List<String> organizationIds) {
        SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();

        OrganizationMapper organizationMapper = session.getMapper(OrganizationMapper.class);
        OrganizationBulk organizations = new OrganizationBulk();
        organizations.setOrganizationIds(organizationIds);
        List<String> queryOrganizationIds = organizationMapper.selectByOrganizationIds(organizations);
        if (null == queryOrganizationIds || organizationIds.size() > queryOrganizationIds.size()) {
            return false;
        }

        return true;
    }
}
