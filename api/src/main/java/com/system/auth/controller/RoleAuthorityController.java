package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.auth.Auth;
import com.system.auth.auth.UserInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.dao.AuthorityMapper;
import com.system.auth.dao.RoleAuthorityMapper;
import com.system.auth.dao.RoleMapper;
import com.system.auth.model.Authority;
import com.system.auth.model.RoleAuthority;
import com.system.auth.model.ext.RoleAuthorityView;
import com.system.auth.model.provider.RoleAuthorityBulkInsert;
import com.system.auth.model.request.AuthorityBulk;
import com.system.auth.model.request.RoleAuthorityBulk;
import com.system.auth.model.request.RoleAuthorityKey;
import com.system.auth.model.request.RoleAuthorityListCondition;
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
@RequestMapping(value = "/api/role_authority")
public class RoleAuthorityController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    RoleAuthorityMapper roleAuthorityMapper = session.getMapper(RoleAuthorityMapper.class);

    @ApiOperation(value="创建角色权限", notes="根据RoleAuthority对象创建角色权限")
    @ApiImplicitParam(name = "role_auth", value = "角色权限详细信息", required = true, dataType = "RoleAuthority")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody RoleAuthority role_auth, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role_auth), request, "", SystemLogging.getOperationStart());

        if (!IsRoleIdAuthIdExists(role_auth.getRoleId(), role_auth.getAuthId())) {
            throw new OperationException(OperationException.getUserInputException(), "role id:" + role_auth.getRoleId() + " or authority id:" + role_auth.getAuthId() + " is not exists");
        }

        role_auth.setCreateTime(java.lang.System.currentTimeMillis());
        role_auth.setUpdateTime(java.lang.System.currentTimeMillis());
        role_auth.setCreateUserId(user_info.getData().getUserId());
        roleAuthorityMapper.insert(role_auth);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role_auth), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="批量创建角色权限", notes="根据RoleAuthorityBulk对象批量创建角色权限")
    @ApiImplicitParam(name = "role_auths", value = "用户角色详细信息", required = true, dataType = "RoleAuthorityBulkInsert")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody RoleAuthorityBulkInsert role_auths, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        if (null == role_auths.getAuthIds() || 0 >= role_auths.getAuthIds().size()) {
            throw new OperationException(OperationException.getUserInputException(), "authority id list must not be empty");
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role_auths), request, "", SystemLogging.getOperationStart());

        if (!IsRoleIdExists(role_auths.getRoleId())) {
            throw new OperationException(OperationException.getUserInputException(),"role id:" + role_auths.getRoleId() + " is not exists");
        }
        if (!IsAuthIdsExists(role_auths.getAuthIds())) {
            throw new OperationException(OperationException.getUserInputException(), "authority ids:" + mapper.writeValueAsString(role_auths.getAuthIds()) + " is not exists");
        }

        role_auths.setCreateUserId(user_info.getData().getUserId());
        role_auths.setCreateTime(java.lang.System.currentTimeMillis());
        role_auths.setUpdateTime(java.lang.System.currentTimeMillis());
        roleAuthorityMapper.insertByRoleAuthorityList(role_auths);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role_auths), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="查询角色权限列表", notes="根据条件查询角色权限列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<RoleAuthorityView> list(@Validated @RequestBody RoleAuthorityListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            QueryListMessage<RoleAuthorityView> result = new QueryListMessage<RoleAuthorityView>(302, user_info.getMsg(), null);

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<RoleAuthorityView> role_authority_list = roleAuthorityMapper.selectBySelective(condition);
        PageInfo<RoleAuthorityView> role_authority_list_info = new PageInfo<RoleAuthorityView>(role_authority_list);
        QueryListMessage<RoleAuthorityView> result = new QueryListMessage<RoleAuthorityView>(0, "", role_authority_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(role_authority_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="删除角色权限", notes="根据角色ID和权限ID删除角色权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody RoleAuthorityKey role_auth, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role_auth), request, "", SystemLogging.getOperationStart());

        roleAuthorityMapper.deleteByPrimaryKey(role_auth);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role_auth), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除角色权限", notes="根据角色ID和权限ID批量删除角色权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody RoleAuthorityBulk role_auths, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role_auths), request, "", SystemLogging.getOperationStart());

        roleAuthorityMapper.deleteByRoleAuthorityIds(role_auths);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role_auths), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }


    public Boolean IsRoleIdAuthIdExists(String roleId, String authId) {
        if (false == IsRoleIdExists(roleId)) {
            return false;
        }

        if (false == IsAuthorityIdExists(authId)) {
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

    public Boolean IsAuthorityIdExists(String authId) {
        SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
        AuthorityMapper authorityMapper = session.getMapper(AuthorityMapper.class);

        if (null == authorityMapper.selectByPrimaryKey(authId)) {
            return false;
        }

        return true;
    }

    public Boolean IsAuthIdsExists(List<String> authIds) {
        SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();

        AuthorityMapper authorityMapper = session.getMapper(AuthorityMapper.class);
        AuthorityBulk auths = new AuthorityBulk();
        auths.setAuthIds(authIds);
        List<String> queryAuthIds = authorityMapper.selectByAuthIds(auths);
        if (null == queryAuthIds || authIds.size() > queryAuthIds.size()) {
            return false;
        }

        return true;
    }
}
