package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.dao.AuthorityMapper;
import com.system.auth.dao.OrganizationAuthorityMapper;
import com.system.auth.dao.OrganizationMapper;
import com.system.auth.model.Authority;
import com.system.auth.model.OrganizationAuthority;
import com.system.auth.model.ext.OrganizationAuthorityView;
import com.system.auth.model.provider.OrganizationAuthorityBulkInsert;
import com.system.auth.model.request.AuthorityBulk;
import com.system.auth.model.request.OrganizationAuthorityBulk;
import com.system.auth.model.request.OrganizationAuthorityKey;
import com.system.auth.model.request.OrganizationAuthorityListCondition;
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
@RequestMapping(value = "/api/organization_authority")
public class OrganizationAuthorityController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    OrganizationAuthorityMapper organizationAuthorityMapper = session.getMapper(OrganizationAuthorityMapper.class);

    @ApiOperation(value="创建组织权限", notes="根据OrganizationAuthority对象创建组织权限")
    @ApiImplicitParam(name = "organization_auth", value = "组织权限详细信息", required = true, dataType = "OrganizationAuthority")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody OrganizationAuthority organization_auth, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization_auth), request, "", SystemLogging.getOperationStart());

        if (!IsOrganizationIdAuthIdExists(organization_auth.getOrganizationId(), organization_auth.getAuthId())) {
            throw new OperationException(OperationException.getUserInputException(), "organization id:" + organization_auth.getOrganizationId() + " or authority id:" + organization_auth.getAuthId() + " is not exists");
        }

        organization_auth.setCreateTime(java.lang.System.currentTimeMillis());
        organization_auth.setUpdateTime(java.lang.System.currentTimeMillis());
        organizationAuthorityMapper.insert(organization_auth);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization_auth), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="批量创建组织权限", notes="根据OrganizationAuthorityBulk对象批量创建组织权限")
    @ApiImplicitParam(name = "organization_auths", value = "用户组织详细信息", required = true, dataType = "OrganizationAuthorityBulkInsert")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage add(@Validated @RequestBody OrganizationAuthorityBulkInsert organization_auths, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        if (null == organization_auths.getAuthIds() || 0 >= organization_auths.getAuthIds().size()) {
            throw new OperationException(OperationException.getUserInputException(), "authority id list must not be empty");
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization_auths), request, "", SystemLogging.getOperationStart());

        if (!IsOrganizationIdExists(organization_auths.getOrganizationId())) {
            throw new OperationException(OperationException.getUserInputException(),"organization id:" + organization_auths.getOrganizationId() + " is not exists");
        }
        if (!IsAuthIdsExists(organization_auths.getAuthIds())) {
            throw new OperationException(OperationException.getUserInputException(), "authority ids:" + mapper.writeValueAsString(organization_auths.getAuthIds()) + " is not exists");
        }

        organizationAuthorityMapper.insertByOrganizationAuthorityList(organization_auths);

        OperationMessage result = new OperationMessage(0, "");
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization_auths), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="查询组织权限列表", notes="根据条件查询组织权限列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<OrganizationAuthorityView> list(@Validated @RequestBody OrganizationAuthorityListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<OrganizationAuthorityView> organization_authority_list = organizationAuthorityMapper.selectBySelective(condition);
        PageInfo<OrganizationAuthorityView> organization_authority_list_info = new PageInfo<OrganizationAuthorityView>(organization_authority_list);
        QueryListMessage<OrganizationAuthorityView> result = new QueryListMessage<OrganizationAuthorityView>(0, "", organization_authority_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(organization_authority_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="删除组织权限", notes="根据组织ID和权限ID删除组织权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody OrganizationAuthorityKey organization_auth, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization_auth), request, "", SystemLogging.getOperationStart());

        organizationAuthorityMapper.deleteByPrimaryKey(organization_auth);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization_auth), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除组织权限", notes="根据组织ID和权限ID批量删除组织权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@Validated @RequestBody OrganizationAuthorityBulk organization_auths, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization_auths), request, "", SystemLogging.getOperationStart());

        organizationAuthorityMapper.deleteByOrganizationAuthorityIds(organization_auths);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization_auths), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }


    public Boolean IsOrganizationIdAuthIdExists(String organizationId, String authId) {
        if (false == IsOrganizationIdExists(organizationId)) {
            return false;
        }

        if (false == IsAuthorityIdExists(authId)) {
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
