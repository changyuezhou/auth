package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.dao.AuthorityMapper;
import com.system.auth.dao.GroupAuthorityMapper;
import com.system.auth.dao.GroupMapper;
import com.system.auth.model.Authority;
import com.system.auth.model.GroupAuthority;
import com.system.auth.model.ext.GroupAuthorityView;
import com.system.auth.model.provider.GroupAuthorityBulkInsert;
import com.system.auth.model.request.AuthorityBulk;
import com.system.auth.model.request.GroupAuthorityBulk;
import com.system.auth.model.request.GroupAuthorityKey;
import com.system.auth.model.request.GroupAuthorityListCondition;
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
@RequestMapping(value = "/group_authority")

public class GroupAuthorityController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    GroupAuthorityMapper groupAuthorityMapper = session.getMapper(GroupAuthorityMapper.class);

    @ApiOperation(value="创建组权限", notes="根据GroupAuthority对象创建组权限")
    @ApiImplicitParam(name = "group_auth", value = "组权限详细信息", required = true, dataType = "GroupAuthority")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody GroupAuthority group_auth, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group_auth), request, "", SystemLogging.getOperationStart());

        if (!IsGroupIdAuthIdExists(group_auth.getGroupId(), group_auth.getAuthId())) {
            throw new OperationException(OperationException.getUserInputException(), "group id:" + group_auth.getGroupId() + " or authority id:" + group_auth.getAuthId() + " is not exists");
        }

        group_auth.setCreateUserId("U06EA2696AE3B4477B9AC6C28AB49A522");
        group_auth.setCreateTime(java.lang.System.currentTimeMillis());
        group_auth.setUpdateTime(java.lang.System.currentTimeMillis());
        groupAuthorityMapper.insert(group_auth);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group_auth), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="批量创建组权限", notes="根据GroupAuthorityBulk对象批量创建组权限")
    @ApiImplicitParam(name = "group_auths", value = "用户组详细信息", required = true, dataType = "GroupAuthorityBulk")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody GroupAuthorityBulk group_auths, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        if (null == group_auths.getAuthIds() || 0 >= group_auths.getAuthIds().size()) {
            throw new OperationException(OperationException.getUserInputException(), "authority id list must not be empty");
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group_auths), request, "", SystemLogging.getOperationStart());

        if (!IsGroupIdExists(group_auths.getGroupId())) {
            throw new OperationException(OperationException.getUserInputException(),"group id:" + group_auths.getGroupId() + " is not exists");
        }
        if (!IsAuthIdsExists(group_auths.getAuthIds())) {
            throw new OperationException(OperationException.getUserInputException(), "authority ids:" + mapper.writeValueAsString(group_auths.getAuthIds()) + " is not exists");
        }

        GroupAuthorityBulkInsert bulk = new GroupAuthorityBulkInsert();
        bulk.setGroupId(group_auths.getGroupId());
        bulk.setCreateUserId("U06EA2696AE3B4477B9AC6C28AB49A522");
        bulk.setAuthIds(group_auths.getAuthIds());

        groupAuthorityMapper.insertByGroupAuthorityList(bulk);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group_auths), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="查询组权限列表", notes="根据条件查询组权限列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<GroupAuthorityView> list(@Validated @RequestBody GroupAuthorityListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<GroupAuthorityView> group_authority_list = groupAuthorityMapper.selectBySelective(condition);
        PageInfo<GroupAuthorityView> group_authority_list_info = new PageInfo<GroupAuthorityView>(group_authority_list);
        QueryListMessage<GroupAuthorityView> result = new QueryListMessage<GroupAuthorityView>(0, "", group_authority_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(group_authority_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="删除组权限", notes="根据组ID和权限ID删除组权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody GroupAuthorityKey group_auth, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group_auth), request, "", SystemLogging.getOperationStart());

        groupAuthorityMapper.deleteByPrimaryKey(group_auth);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group_auth), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除组权限", notes="根据组ID和权限ID批量删除组权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody GroupAuthorityBulk group_auths, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group_auths), request, "", SystemLogging.getOperationStart());

        groupAuthorityMapper.deleteByGroupAuthorityIds(group_auths);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group_auths), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }


    public Boolean IsGroupIdAuthIdExists(String groupId, String authId) {
        if (false == IsGroupIdExists(groupId)) {
            return false;
        }

        if (false == IsAuthorityIdExists(authId)) {
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
