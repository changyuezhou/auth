package com.system.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.auth.bean.*;
import com.system.auth.model.request.UserBulk;
import com.system.auth.model.request.UserKey;
import com.system.auth.model.response.UserAddResponse;
import com.system.auth.dao.UserMapper;
import com.system.auth.model.User;
import com.system.auth.model.request.UserListCondition;
import com.system.auth.model.ext.UserView;
import com.system.auth.util.MybatisUtil;
import com.system.auth.util.SystemLogging;
import io.swagger.annotations.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    UserMapper userMapper = session.getMapper(UserMapper.class);

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细信息", required = true, dataType = "User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
            )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<UserAddResponse> add(@Validated @RequestBody User user, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user), request, "", SystemLogging.getOperationStart());

        if (IsUserNameExists(user.getUserName())) {
            throw new OperationException(OperationException.getUserInputException(), "user name:" + user.getUserName() + " is exists");
        }

        String userId = "U" + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        user.setUserId(userId);
        if (null == user.getCreateUserId() || 0 >= user.getCreateUserId().length()) {
            user.setCreateUserId(userId);
        }
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        userMapper.insertSelective(user);

        UserAddResponse user_response = new UserAddResponse(user.getUserId(), user.getUserName());
        ResponseMessage<UserAddResponse> result = new ResponseMessage<UserAddResponse>(0, "", user_response);
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="修改用户", notes="根据User对象修改用户")
    @ApiImplicitParam(name = "user", value = "用户详细信息", required = true, dataType = "User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage update(@RequestBody User user, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user), request, "", SystemLogging.getOperationStart());

        if (null == user.getUserId()) {
            throw new OperationException(OperationException.getUserInputException(), "user id must not be null");
        }

        if (null != user.getStatus() && 0 == user.getStatus()) {
            throw new OperationException(OperationException.getUserInputException(), "user status can not be 0");
        }

        UserView user_view = userMapper.selectByPrimaryKey(user.getUserId());
        if (null == user_view) {
            throw new OperationException(OperationException.getUserInputException(), "user id: " + user.getUserId() + " is not exists");
        }

        if (null != user.getUserName() && !user_view.getUserName().equalsIgnoreCase(user.getUserName()) && IsUserNameExists(user.getUserName())) {
            throw new OperationException(OperationException.getServiceException(), "user name:" + user.getUserName() + " is exists");
        }

        user.setUpdateTime(System.currentTimeMillis());
        userMapper.updateByPrimaryKeySelective(user);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="删除用户", notes="根据用户ID删除用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody UserKey user, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user), request, "", SystemLogging.getOperationStart());

        if (null == user.getUserId()) {
            throw new OperationException(OperationException.getUserInputException(), "user id must not be null");
        }

        userMapper.deleteByPrimaryKey(user.getUserId());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="批量删除用户", notes="根据用户ID批量删除用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/bulk/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public OperationMessage delete(@RequestBody UserBulk users, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(users), request, "", SystemLogging.getOperationStart());

        if (null == users.getUserIds()) {
            throw new OperationException(OperationException.getUserInputException(), "user id list must not be null");
        }

        userMapper.deleteByUserIds(users);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(users), request, mapper.writeValueAsString(new OperationMessage(0, "")), SystemLogging.getSUCCESS());

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="查询用户", notes="根据用户ID查询用户详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<UserView> query(@Validated @RequestBody UserKey user, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user), request, "", SystemLogging.getOperationStart());

        UserView result = userMapper.selectByPrimaryKey(user.getUserId());
        if (null == result) {
            throw new OperationException(OperationException.getRecordIsNotExists(), OperationException.getNoRecordsMsg());
        }

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(user), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return new ResponseMessage<UserView>(0, "", result);
    }

    @ApiOperation(value="查询用户列表", notes="根据条件查询用户列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<UserView> list(@Validated @RequestBody UserListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<UserView> user_list = userMapper.selectBySelective(condition);
        PageInfo<UserView> user_list_info = new PageInfo<UserView>(user_list);
        QueryListMessage<UserView> result = new QueryListMessage<UserView>(0, "", user_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(user_list_info.getTotal());

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    public Boolean IsUserNameExists(String userName) {
        if (null != userMapper.selectByUserName(userName)) {
            return true;
        }

        return false;
    }
}
