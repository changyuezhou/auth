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
import com.system.auth.dao.SystemMapper;
import com.system.auth.model.request.SystemBulk;
import com.system.auth.model.request.SystemKey;
import com.system.auth.model.ext.SystemView;
import com.system.auth.model.System;
import com.system.auth.model.request.SystemListCondition;
import com.system.auth.model.response.SystemAddResponse;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/system")
public class SystemController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    SystemMapper systemMapper = session.getMapper(SystemMapper.class);

    @ApiOperation(value="创建系统", notes="根据System对象创建系统")
    @ApiImplicitParam(name = "system", value = "系统详细信息", required = true, dataType = "System")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<SystemAddResponse> add(@Validated @RequestBody System system, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            ResponseMessage<SystemAddResponse> result = new ResponseMessage<SystemAddResponse>(302, user_info.getMsg(),  null);

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(system), request, "", SystemLogging.getOperationStart());

        if (IsSystemNameExists(system.getSystemName())) {
            throw new OperationException(OperationException.getUserInputException(), "system name:" + system.getSystemName() + " is exists");
        }

        String systemId = "S" + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        system.setSystemId(systemId);
        system.setCreateTime(java.lang.System.currentTimeMillis());
        system.setUpdateTime(java.lang.System.currentTimeMillis());
        system.setCreateUserId(user_info.getData().getUserId());
        systemMapper.insertSelective(system);

        SystemAddResponse user_response = new SystemAddResponse(system.getSystemId(), system.getSystemName());
        ResponseMessage<SystemAddResponse> result = new ResponseMessage<SystemAddResponse>(0, "", user_response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(system), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="修改系统", notes="根据System对象修改系统")
    @ApiImplicitParam(name = "system", value = "系统详细信息", required = true, dataType = "System")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage update(@RequestBody System system, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(system), request, "", SystemLogging.getOperationStart());

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        if (null == system.getSystemId()) {
            throw new OperationException(OperationException.getUserInputException(), "system id must not be null");
        }

        SystemView system_view = systemMapper.selectByPrimaryKey(system.getSystemId());

        if (null == system_view) {
            throw new OperationException(OperationException.getUserInputException(), "system id: " + system.getSystemId() + " is not exists");
        }

        if (null != system.getSystemName() && !system_view.getSystemName().equalsIgnoreCase(system.getSystemName()) && IsSystemNameExists(system.getSystemName())) {
            throw new OperationException(OperationException.getServiceException(), "system name:" + system.getSystemName() + " is exists");
        }

        system.setUpdateTime(java.lang.System.currentTimeMillis());
        systemMapper.updateByPrimaryKeySelective(system);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(system), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="删除系统", notes="根据系统ID删除系统")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody SystemKey system, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(system), request, "", SystemLogging.getOperationStart());

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        if (null == system.getSystemId()) {
            throw new OperationException(OperationException.getUserInputException(), "system id must not be null");
        }

        systemMapper.deleteByPrimaryKey(system.getSystemId());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(system), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除系统", notes="根据系统ID批量删除系统")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody SystemBulk systems, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(systems), request, "", SystemLogging.getOperationStart());

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            OperationMessage result = new OperationMessage(302, user_info.getMsg());

            return result;
        }

        if (null == systems.getSystemIds()) {
            throw new OperationException(OperationException.getUserInputException(), "system id list must not be null");
        }

        systemMapper.deleteBySystemIds(systems);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(systems), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="查询系统", notes="根据系统ID查询系统详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<SystemView> query(@Validated @RequestBody SystemKey system, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            ResponseMessage<SystemView> result = new ResponseMessage<SystemView>(302, user_info.getMsg(), null);

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(system), request, "", SystemLogging.getOperationStart());

        SystemView result = systemMapper.selectByPrimaryKey(system.getSystemId());
        if (null == result) {
            throw new OperationException(OperationException.getRecordIsNotExists(), OperationException.getNoRecordsMsg());
        }

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(system), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return new ResponseMessage<SystemView>(0, "", result);
    }

    @ApiOperation(value="查询系统列表", notes="根据条件查询系统列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<SystemView> list(@Validated @RequestBody SystemListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            QueryListMessage<SystemView> result = new QueryListMessage<SystemView>(302, user_info.getMsg(), null);

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<SystemView> system_list = systemMapper.selectBySelective(condition);
        PageInfo<SystemView> system_list_info = new PageInfo<SystemView>(system_list);
        QueryListMessage<SystemView> result = new QueryListMessage<SystemView>(0, "", system_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(system_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    public Boolean IsSystemNameExists(String systemName) {
        if (null != systemMapper.selectBySystemName(systemName)) {
            return true;
        }

        return false;
    }
}
