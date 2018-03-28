package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.bean.ResponseMessage;
import com.system.auth.dao.OrganizationMapper;
import com.system.auth.dao.PlatformMapper;
import com.system.auth.model.Organization;
import com.system.auth.model.ext.OrganizationView;
import com.system.auth.model.request.OrganizationBulk;
import com.system.auth.model.request.OrganizationKey;
import com.system.auth.model.request.OrganizationListCondition;
import com.system.auth.model.response.OrganizationAddResponse;
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
@RequestMapping(value = "/organization")
public class OrganizationController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    OrganizationMapper organizationMapper = session.getMapper(OrganizationMapper.class);

    @ApiOperation(value="创建组织", notes="根据Organization对象创建组织")
    @ApiImplicitParam(name = "organization", value = "组织详细信息", required = true, dataType = "Organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<OrganizationAddResponse> add(@Validated @RequestBody Organization organization, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization), request, "", SystemLogging.getOperationStart());

        if (!IsPlatformIdExists(organization.getPlatformId())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id:" + organization.getPlatformId() + " is not exists");
        }

        if (IsOrganizationNameExists(organization.getPlatformId(), organization.getOrganizationName())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id:" + organization.getPlatformId() + " organization name:" + organization.getOrganizationName() + " is exists");
        }

        if (null != organization.getOrganizationFId() && 0 < organization.getOrganizationFId().length()) {
            OrganizationView view = organizationMapper.selectByPrimaryKey(organization.getOrganizationFId());
            if (null == view) {
                throw new OperationException(OperationException.getUserInputException(), "platform id:" + organization.getPlatformId() + " organization farther id:" + organization.getOrganizationFId() + " is not exists");
            }

            organization.setOrganizationLevel(view.getOrganizationLevel() + 1);
        } else {
            organization.setOrganizationLevel(0);
        }

        String authId = "O" + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        organization.setOrganizationId(authId);
        organization.setCreateTime(java.lang.System.currentTimeMillis());
        organization.setUpdateTime(java.lang.System.currentTimeMillis());
        organizationMapper.insertSelective(organization);

        OrganizationAddResponse group_response = new OrganizationAddResponse(organization.getOrganizationId(), organization.getOrganizationName());
        ResponseMessage<OrganizationAddResponse> result = new ResponseMessage<OrganizationAddResponse>(0, "", group_response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="修改权限", notes="根据Organization对象修改权限")
    @ApiImplicitParam(name = "auth", value = "权限详细信息", required = true, dataType = "Organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage update(@RequestBody Organization organization, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization), request, "", SystemLogging.getOperationStart());

        if (null == organization.getOrganizationId()) {
            throw new OperationException(OperationException.getUserInputException(), "organization id must not be null");
        }

        OrganizationView organization_view = organizationMapper.selectByPrimaryKey(organization.getOrganizationId());
        if (null == organization_view) {
            throw new OperationException(OperationException.getUserInputException(), "organization id: " + organization.getOrganizationId() + " is not exists");
        }

        if (null != organization.getPlatformId() && !organization.getPlatformId().equalsIgnoreCase(organization_view.getPlatformId()) && IsPlatformIdExists(organization.getPlatformId())) {
            throw new OperationException(OperationException.getServiceException(), "platform id:" + organization.getPlatformId() + " is exists");
        }

        String platformId = (null == organization.getPlatformId())? organization_view.getPlatformId(): organization_view.getPlatformId();

        if (!IsPlatformIdExists(organization.getPlatformId())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id:" + organization.getPlatformId() + " is not exists");
        }

        if (null != organization.getOrganizationName() && !organization.getOrganizationName().equalsIgnoreCase(organization_view.getOrganizationName()) && IsOrganizationNameExists(platformId, organization.getOrganizationName())) {
            throw new OperationException(OperationException.getServiceException(), "platform id:" + platformId + " organization name:" + organization.getOrganizationName() +  " is exists");
        }

        organization.setUpdateTime(java.lang.System.currentTimeMillis());
        organizationMapper.updateByPrimaryKeySelective(organization);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="删除组织", notes="根据组织ID删除组织")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody OrganizationKey organization, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization), request, "", SystemLogging.getOperationStart());

        if (null == organization.getOrganizationId()) {
            throw new OperationException(OperationException.getUserInputException(), "organization id must not be null");
        }

        organizationMapper.deleteByPrimaryKey(organization.getOrganizationId());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除组织", notes="根据组织ID批量删除组织")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody OrganizationBulk organizations, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organizations), request, "", SystemLogging.getOperationStart());

        if (null == organizations.getOrganizationIds()) {
            throw new OperationException(OperationException.getUserInputException(), "organization id list must not be null");
        }

        organizationMapper.deleteByOrganizationIds(organizations);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organizations), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="查询组织", notes="根据组织ID查询详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<OrganizationView> query(@Validated @RequestBody OrganizationKey organization, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization), request, "", SystemLogging.getOperationStart());

        OrganizationView result = organizationMapper.selectByPrimaryKey(organization.getOrganizationId());
        if (null == result) {
            throw new OperationException(OperationException.getRecordIsNotExists(), OperationException.getNoRecordsMsg());
        }

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(organization), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return new ResponseMessage<OrganizationView>(0, "", result);
    }

    @ApiOperation(value="查询组织列表", notes="根据条件查询组织列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<OrganizationView> list(@Validated @RequestBody OrganizationListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<OrganizationView> organization_list = organizationMapper.selectBySelective(condition);
        PageInfo<OrganizationView> organization_list_info = new PageInfo<OrganizationView>(organization_list);
        QueryListMessage<OrganizationView> result = new QueryListMessage<OrganizationView>(0, "", organization_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(organization_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    public Boolean IsOrganizationNameExists(String platformId, String organizationName) {
        OrganizationListCondition condition = new OrganizationListCondition();
        condition.setPlatformId(platformId);
        condition.setOrganizationName(organizationName);
        if (null != organizationMapper.selectByPlatformIdAndOrganizationName(condition)) {
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
