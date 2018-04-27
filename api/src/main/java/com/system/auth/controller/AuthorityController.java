package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.auth.Auth;
import com.system.auth.auth.UserInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.bean.ResponseMessage;
import com.system.auth.dao.AuthorityMapper;
import com.system.auth.dao.SystemMapper;
import com.system.auth.model.Authority;
import com.system.auth.model.ext.AuthorityView;
import com.system.auth.model.request.AuthorityBulk;
import com.system.auth.model.request.AuthorityKey;
import com.system.auth.model.request.AuthorityListCondition;
import com.system.auth.model.response.AuthorityAddResponse;
import com.system.auth.util.MybatisUtil;
import com.system.auth.util.SystemLogging;
import io.swagger.annotations.*;
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
@RequestMapping(value = "/api/authority")
public class AuthorityController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    AuthorityMapper authorityMapper = session.getMapper(AuthorityMapper.class);

    @ApiOperation(value="创建权限", notes="根据Authority对象创建权限")
    @ApiImplicitParam(name = "auth", value = "权限详细信息", required = true, dataType = "Authority")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<AuthorityAddResponse> add(@Validated @RequestBody Authority auth, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }
        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            ResponseMessage<AuthorityAddResponse> result = new ResponseMessage<AuthorityAddResponse>(302, user_info.getMsg(), null);

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth), request, "", SystemLogging.getOperationStart());

        if (!IsSystemIdExists(auth.getSystemId())) {
            throw new OperationException(OperationException.getUserInputException(), "system id:" + auth.getSystemId() + " is not exists");
        }

        if (IsAuthNameExists(auth.getSystemId(), auth.getAuthName())) {
            throw new OperationException(OperationException.getUserInputException(), "system id:" + auth.getSystemId() + " authority name:" + auth.getAuthName() + " is exists");
        }

        String authId = "A" + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        if (null != auth.getAuthFId() && 0 < auth.getAuthFId().length()) {
            AuthorityView view = authorityMapper.selectByPrimaryKey(auth.getAuthFId());
            if (null == view) {
                throw new OperationException(OperationException.getUserInputException(), "system id:" + auth.getSystemId() + " authority farther id:" + auth.getAuthFId() + " is not exists");
            }

            auth.setAuthLevel(view.getAuthLevel() + 1);
        } else {
            auth.setAuthLevel(0);
            auth.setAuthFId(authId);
        }

        auth.setAuthId(authId);
        auth.setCreateTime(java.lang.System.currentTimeMillis());
        auth.setUpdateTime(java.lang.System.currentTimeMillis());
        auth.setCreateUserId(user_info.getData().getUserId());
        authorityMapper.insertSelective(auth);

        AuthorityAddResponse group_response = new AuthorityAddResponse(auth.getAuthId(), auth.getAuthName());
        ResponseMessage<AuthorityAddResponse> result = new ResponseMessage<AuthorityAddResponse>(0, "", group_response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="修改权限", notes="根据Authority对象修改权限")
    @ApiImplicitParam(name = "auth", value = "权限详细信息", required = true, dataType = "Authority")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage update(@RequestBody Authority auth, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth), request, "", SystemLogging.getOperationStart());

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        if (null == auth.getAuthId()) {
            throw new OperationException(OperationException.getUserInputException(), "authority id must not be null");
        }

        AuthorityView auth_view = authorityMapper.selectByPrimaryKey(auth.getAuthId());
        if (null == auth_view) {
            throw new OperationException(OperationException.getUserInputException(), "authority id: " + auth.getAuthFId() + " is not exists");
        }

        if (null != auth.getAuthFId() && !auth.getAuthFId().equalsIgnoreCase(auth_view.getAuthFId())) {
            throw new OperationException(OperationException.getUserInputException(), "authority parent id can not be changed and you can delete this authority");
        }

        if (null != auth.getSystemId() && !auth.getSystemId().equalsIgnoreCase(auth_view.getSystemId()) && IsSystemIdExists(auth.getSystemId())) {
            throw new OperationException(OperationException.getServiceException(), "system id:" + auth.getSystemId() + " is exists");
        }

        String systemId = (null == auth.getSystemId())? auth_view.getSystemId(): auth_view.getSystemId();

        if (!IsSystemIdExists(auth.getSystemId())) {
            throw new OperationException(OperationException.getUserInputException(), "system id:" + auth.getSystemId() + " is not exists");
        }

        if (null != auth.getAuthName() && !auth.getAuthName().equalsIgnoreCase(auth_view.getAuthName()) && IsAuthNameExists(systemId, auth.getAuthName())) {
            throw new OperationException(OperationException.getServiceException(), "system id:" + systemId + " authority name:" + auth.getAuthName() +  " is exists");
        }

        auth.setUpdateTime(java.lang.System.currentTimeMillis());
        authorityMapper.updateByPrimaryKeySelective(auth);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="删除权限", notes="根据权限ID删除权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody AuthorityKey auth, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth), request, "", SystemLogging.getOperationStart());

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        if (null == auth.getAuthId()) {
            throw new OperationException(OperationException.getUserInputException(), "authority id must not be null");
        }

        authorityMapper.deleteByPrimaryKey(auth.getAuthId());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除权限", notes="根据权限ID批量删除权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody AuthorityBulk auths, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auths), request, "", SystemLogging.getOperationStart());

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        if (null == auths.getAuthIds()) {
            throw new OperationException(OperationException.getUserInputException(), "authority id list must not be null");
        }

        authorityMapper.deleteByAuthorityIds(auths);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auths), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="查询权限", notes="根据权限ID查询详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<AuthorityView> query(@Validated @RequestBody AuthorityKey auth, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            ResponseMessage<AuthorityView> result = new ResponseMessage<AuthorityView>(302, user_info.getMsg(), null);

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth), request, "", SystemLogging.getOperationStart());

        AuthorityView result = authorityMapper.selectByPrimaryKey(auth.getAuthId());
        if (null == result) {
            throw new OperationException(OperationException.getRecordIsNotExists(), OperationException.getNoRecordsMsg());
        }

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(auth), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return new ResponseMessage<AuthorityView>(0, "", result);
    }

    @ApiOperation(value="查询权限列表", notes="根据条件查询权限列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<AuthorityView> list(@Validated @RequestBody AuthorityListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            QueryListMessage<AuthorityView> result = new QueryListMessage<AuthorityView>(302, user_info.getMsg(), null);

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<AuthorityView> authority_list = authorityMapper.selectBySelective(condition);
        PageInfo<AuthorityView> authority_list_info = new PageInfo<AuthorityView>(authority_list);
        QueryListMessage<AuthorityView> result = new QueryListMessage<AuthorityView>(0, "", authority_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(authority_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    public Boolean IsAuthNameExists(String systemId, String authName) {
        AuthorityListCondition condition = new AuthorityListCondition();
        condition.setSystemId(systemId);
        condition.setAuthName(authName);

        if (null == authorityMapper.selectBySystemIdAuthName(condition)) {
            return false;
        }

        return true;
    }

    public Boolean IsSystemIdExists(String systemId) {
        SystemMapper systemMapper = session.getMapper(SystemMapper.class);

        if (null != systemMapper.selectByPrimaryKey(systemId)) {
            return true;
        }

        return false;
    }
}
