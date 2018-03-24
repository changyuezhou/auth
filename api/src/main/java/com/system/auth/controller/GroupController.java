package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.OperationException;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryListMessage;
import com.system.auth.bean.ResponseMessage;
import com.system.auth.dao.GroupMapper;
import com.system.auth.dao.PlatformMapper;
import com.system.auth.model.Group;
import com.system.auth.model.Platform;
import com.system.auth.model.ext.GroupView;
import com.system.auth.model.request.GroupBulk;
import com.system.auth.model.request.GroupKey;
import com.system.auth.model.request.GroupListCondition;
import com.system.auth.model.response.GroupAddResponse;
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
@RequestMapping(value = "/group")
public class GroupController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    GroupMapper groupMapper = session.getMapper(GroupMapper.class);

    @ApiOperation(value="创建组", notes="根据Group对象创建组")
    @ApiImplicitParam(name = "group", value = "组详细信息", required = true, dataType = "Group")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<GroupAddResponse> add(@Validated @RequestBody Group group, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group), request, "", SystemLogging.getOperationStart());

        if (!IsPlatformIdExists(group.getPlatformId())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id:" + group.getPlatformId() + " is not exists");
        }

        if (IsGroupNameExists(group.getPlatformId(), group.getGroupName())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id:" + group.getPlatformId() + " group name:" + group.getGroupName() + " is exists");
        }

        String groupId = "G" + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        group.setGroupId(groupId);
        group.setCreateUserId("U06EA2696AE3B4477B9AC6C28AB49A522");
        group.setCreateTime(java.lang.System.currentTimeMillis());
        group.setUpdateTime(java.lang.System.currentTimeMillis());
        groupMapper.insertSelective(group);

        GroupAddResponse group_response = new GroupAddResponse(group.getGroupId(), group.getGroupName());
        ResponseMessage<GroupAddResponse> result = new ResponseMessage<GroupAddResponse>(0, "", group_response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="修改组", notes="根据Group对象修改组")
    @ApiImplicitParam(name = "group", value = "组详细信息", required = true, dataType = "Group")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage update(@RequestBody Group group, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group), request, "", SystemLogging.getOperationStart());

        if (null == group.getGroupId()) {
            throw new OperationException(OperationException.getUserInputException(), "group id must not be null");
        }

        GroupView group_view = groupMapper.selectByPrimaryKey(group.getGroupId());
        if (null == group_view) {
            throw new OperationException(OperationException.getUserInputException(), "group id: " + group.getGroupId() + " is not exists");
        }

        String platformId = (null == group.getPlatformId())? group_view.getPlatformId(): group.getPlatformId();

        if (null != group.getPlatformId() && !group.getPlatformId().equalsIgnoreCase(group_view.getPlatformId()) && !IsPlatformIdExists(group.getPlatformId())) {
            throw new OperationException(OperationException.getUserInputException(), "platform id: " + group.getPlatformId() + " is not exists");
        }

        if (null != group.getGroupName() && !group.getGroupName().equalsIgnoreCase(group_view.getGroupName()) && IsGroupNameExists(platformId, group.getGroupName())) {
            throw new OperationException(OperationException.getServiceException(), "platform id:" + platformId + " group name:" + group.getGroupName() +  " is exists");
        }

        group.setUpdateTime(java.lang.System.currentTimeMillis());
        groupMapper.updateByPrimaryKeySelective(group);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="删除组", notes="根据组ID删除组")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody GroupKey group, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group), request, "", SystemLogging.getOperationStart());

        if (null == group.getGroupId()) {
            throw new OperationException(OperationException.getUserInputException(), "group id must not be null");
        }

        groupMapper.deleteByPrimaryKey(group.getGroupId());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除组", notes="根据组ID批量删除组")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody GroupBulk groups, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(groups), request, "", SystemLogging.getOperationStart());

        if (null == groups.getGroupIds()) {
            throw new OperationException(OperationException.getUserInputException(), "group id list must not be null");
        }

        groupMapper.deleteByGroupIds(groups);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(groups), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="查询组", notes="根据用户组ID查询详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<GroupView> query(@Validated @RequestBody GroupKey group, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group), request, "", SystemLogging.getOperationStart());

        GroupView result = groupMapper.selectByPrimaryKey(group.getGroupId());
        if (null == result) {
            throw new OperationException(OperationException.getRecordIsNotExists(), OperationException.getNoRecordsMsg());
        }

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(group), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return new ResponseMessage<GroupView>(0, "", result);
    }

    @ApiOperation(value="查询组列表", notes="根据条件查询组列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<GroupView> list(@Validated @RequestBody GroupListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<GroupView> group_list = groupMapper.selectBySelective(condition);
        PageInfo<GroupView> group_list_info = new PageInfo<GroupView>(group_list);
        QueryListMessage<GroupView> result = new QueryListMessage<GroupView>(0, "", group_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(group_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    public Boolean IsGroupNameExists(String platformId, String groupName) {
        GroupListCondition condition = new GroupListCondition();
        condition.setPlatformId(platformId);
        condition.setGroupName(groupName);
        if (null != groupMapper.selectByPlatformIdAndGroupName(condition)) {
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
