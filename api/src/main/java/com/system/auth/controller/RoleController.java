package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.bean.ResponseMessage;
import com.system.auth.dao.PlatformMapper;
import com.system.auth.dao.RoleMapper;
import com.system.auth.model.Platform;
import com.system.auth.model.Role;
import com.system.auth.model.ext.RoleView;
import com.system.auth.model.request.RoleBulk;
import com.system.auth.model.request.RoleKey;
import com.system.auth.model.request.RoleListCondition;
import com.system.auth.model.response.RoleAddResponse;
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
@RequestMapping(value = "/api/role")
public class RoleController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    RoleMapper roleMapper = session.getMapper(RoleMapper.class);

    @ApiOperation(value="创建角色", notes="根据Role对象创建角色")
    @ApiImplicitParam(name = "role", value = "角色详细信息", required = true, dataType = "Role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<RoleAddResponse> add(@Validated @RequestBody Role role, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role), request, "", SystemLogging.getOperationStart());

        if (!IsPlatformIdExists(role.getPlatformId())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id:" + role.getPlatformId() + " is not exists");
        }

        if (IsRoleNameExists(role.getPlatformId(), role.getRoleName())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id:" + role.getPlatformId() + " group name:" + role.getRoleName() + " is exists");
        }

        String roleId = "R" + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        role.setRoleId(roleId);
        role.setCreateTime(java.lang.System.currentTimeMillis());
        role.setUpdateTime(java.lang.System.currentTimeMillis());
        roleMapper.insertSelective(role);

        RoleAddResponse group_response = new RoleAddResponse(role.getRoleId(), role.getRoleName());
        ResponseMessage<RoleAddResponse> result = new ResponseMessage<RoleAddResponse>(0, "", group_response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="修改角色", notes="根据Role对象修改角色")
    @ApiImplicitParam(name = "role", value = "角色详细信息", required = true, dataType = "Role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage update(@RequestBody Role role, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role), request, "", SystemLogging.getOperationStart());

        if (null == role.getRoleId()) {
            throw new OperationException(OperationException.getUserInputException(), "role id must not be null");
        }

        RoleView group_view = roleMapper.selectByPrimaryKey(role.getRoleId());
        if (null == group_view) {
            throw new OperationException(OperationException.getUserInputException(), "role id: " + role.getRoleId() + " is not exists");
        }

        String platformId = (null == role.getPlatformId())? group_view.getPlatformId(): role.getPlatformId();

        if (null != role.getPlatformId() && !role.getPlatformId().equalsIgnoreCase(group_view.getPlatformId()) && !IsPlatformIdExists(role.getPlatformId())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id: " + role.getPlatformId() + " is not exists");
        }

        if (null != role.getRoleName() && !role.getRoleName().equalsIgnoreCase(group_view.getRoleName()) && IsRoleNameExists(platformId, role.getRoleName())) {
            throw new OperationException(OperationException.getServiceException(), "platform id:" + platformId + " role name:" + role.getRoleName() +  " is exists");
        }

        role.setUpdateTime(java.lang.System.currentTimeMillis());
        roleMapper.updateByPrimaryKeySelective(role);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="删除角色", notes="根据角色ID删除角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody RoleKey role, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role), request, "", SystemLogging.getOperationStart());

        if (null == role.getRoleId()) {
            throw new OperationException(OperationException.getUserInputException(), "role id must not be null");
        }

        roleMapper.deleteByPrimaryKey(role.getRoleId());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除角色", notes="根据角色ID批量删除角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody RoleBulk roles, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(roles), request, "", SystemLogging.getOperationStart());

        if (null == roles.getRoleIds()) {
            throw new OperationException(OperationException.getUserInputException(), "role id list must not be null");
        }

        roleMapper.deleteByRoleIds(roles);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(roles), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="查询角色", notes="根据角色ID查询详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<RoleView> query(@Validated @RequestBody RoleKey role, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role), request, "", SystemLogging.getOperationStart());

        RoleView result = roleMapper.selectByPrimaryKey(role.getRoleId());
        if (null == result) {
            throw new OperationException(OperationException.getRecordIsNotExists(), OperationException.getNoRecordsMsg());
        }

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(role), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return new ResponseMessage<RoleView>(0, "", result);
    }

    @ApiOperation(value="查询角色列表", notes="根据条件查询角色列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<RoleView> list(@Validated @RequestBody RoleListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<RoleView> role_list = roleMapper.selectBySelective(condition);
        PageInfo<RoleView> role_list_info = new PageInfo<RoleView>(role_list);
        QueryListMessage<RoleView> result = new QueryListMessage<RoleView>(0, "", role_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(role_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    public Boolean IsRoleNameExists(String platformId, String roleName) {
        RoleListCondition condition = new RoleListCondition();
        condition.setPlatformId(platformId);
        condition.setRoleName(roleName);
        if (null != roleMapper.selectByPlatformIdAndRoleName(condition)) {
            return true;
        }

        return false;
    }


    public Boolean IsPlatformIdExists(String platformId) {
        PlatformMapper platformMapper = session.getMapper(PlatformMapper.class);

        if (null != platformMapper.selectByPrimaryKey(platformId)) {
            return true;
        }

        return false;
    }
}
