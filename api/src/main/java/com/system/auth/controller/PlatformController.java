package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.bean.ResponseMessage;
import com.system.auth.dao.PlatformMapper;
import com.system.auth.model.Platform;
import com.system.auth.model.request.PlatformBulk;
import com.system.auth.model.request.PlatformKey;
import com.system.auth.model.ext.PlatformView;
import com.system.auth.model.request.PlatformListCondition;
import com.system.auth.model.response.PlatformAddResponse;
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
@RequestMapping(value = "/platform")
public class PlatformController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    PlatformMapper platformMapper = session.getMapper(PlatformMapper.class);

    @ApiOperation(value="创建平台", notes="根据Platform对象创建平台")
    @ApiImplicitParam(name = "platform", value = "平台详细信息", required = true, dataType = "Platform")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<PlatformAddResponse> add(@Validated @RequestBody Platform platform, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platform), request, "", SystemLogging.getOperationStart());

        if (IsPlatformNameExists(platform.getPlatformName())) {
            throw new OperationException(OperationException.getUserInputException(), "platform name:" + platform.getPlatformName() + " is exists");
        }

        String platformId = "P" + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        platform.setPlatformId(platformId);
        platform.setCreateUserId("U06EA2696AE3B4477B9AC6C28AB49A522");
        platform.setCreateTime(java.lang.System.currentTimeMillis());
        platform.setUpdateTime(java.lang.System.currentTimeMillis());
        platformMapper.insertSelective(platform);

        PlatformAddResponse platform_response = new PlatformAddResponse(platform.getPlatformId(), platform.getPlatformName());
        ResponseMessage<PlatformAddResponse> result = new ResponseMessage<PlatformAddResponse>(0, "", platform_response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platform), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="修改平台", notes="根据Platform对象修改平台")
    @ApiImplicitParam(name = "platform", value = "平台详细信息", required = true, dataType = "Platform")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage update(@RequestBody Platform platform, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platform), request, "", SystemLogging.getOperationStart());

        if (null == platform.getPlatformId()) {
            throw new OperationException(OperationException.getUserInputException(), "platform id must not be null");
        }

        if (!IsPlatformIdExists(platform.getPlatformId())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id: " + platform.getPlatformId() + " is not exists");
        }

        if (null != platform.getPlatformName() && !CanUpdate(platform.getPlatformId(), platform.getPlatformName())) {
            throw new OperationException(OperationException.getServiceException(), "platform name:" + platform.getPlatformName() + " is exists");
        }

        platform.setUpdateTime(java.lang.System.currentTimeMillis());
        platformMapper.updateByPrimaryKeySelective(platform);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platform), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="删除平台", notes="根据平台ID删除平台")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody PlatformKey platform, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platform), request, "", SystemLogging.getOperationStart());

        if (null == platform.getPlatformId()) {
            throw new OperationException(OperationException.getUserInputException(), "platform id must not be null");
        }

        platformMapper.deleteByPrimaryKey(platform.getPlatformId());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platform), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除平台", notes="根据平台ID批量删除平台")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody PlatformBulk platforms, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platforms), request, "", SystemLogging.getOperationStart());

        if (null == platforms.getPlatformIds()) {
            throw new OperationException(OperationException.getUserInputException(), "platform id list must not be null");
        }

        platformMapper.deleteByPlatformIds(platforms);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platforms), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="查询平台", notes="根据平台ID查询平台详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<PlatformView> query(@Validated @RequestBody PlatformKey platform, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platform), request, "", SystemLogging.getOperationStart());

        PlatformView result = platformMapper.selectByPrimaryKey(platform.getPlatformId());
        if (null == result) {
            throw new OperationException(OperationException.getRecordIsNotExists(), OperationException.getNoRecordsMsg());
        }

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(platform), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return new ResponseMessage<PlatformView>(0, "", result);
    }

    @ApiOperation(value="查询平台列表", notes="根据条件查询平台列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<PlatformView> list(@Validated @RequestBody PlatformListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<PlatformView> platform_list = platformMapper.selectBySelective(condition);
        PageInfo<PlatformView> platform_list_info = new PageInfo<PlatformView>(platform_list);
        QueryListMessage<PlatformView> result = new QueryListMessage<PlatformView>(0, "", platform_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(platform_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    public Boolean IsPlatformNameExists(String platformName) {
        if (null != platformMapper.selectByPlatformName(platformName)) {
            return true;
        }

        return false;
    }

    public Boolean CanUpdate(String platformId, String platformName) {
        PlatformView platform = platformMapper.selectByPlatformName(platformName);
        if (null == platform) {
            return true;
        }

        if (platformId.equals(platform.getPlatformId())) {
            return true;
        }

        return false;
    }

    public Boolean IsPlatformIdExists(String platformId) {
        if (null != platformMapper.selectByPrimaryKey(platformId)) {
            return true;
        }

        return false;
    }
}
