package com.system.auth.controller;

import com.system.auth.bean.MessageInterface;
import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryUserMessage;
import com.system.auth.dao.UserMapper;
import com.system.auth.model.User;
import com.system.auth.util.MybatisUtil;
import io.swagger.annotations.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    UserMapper userTMapper = session.getMapper(UserMapper.class);

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
    public MessageInterface add(@Validated @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new OperationMessage(CustomErrorController.USER_INPUT_EXCEPTION, result.getAllErrors().get(0).getDefaultMessage());
        }

        if (IsUserNameExists(user.getUserName())) {
            return new OperationMessage(CustomErrorController.SERVICE_EXCEPTION, "user name:" + user.getUserName() + " is exists");
        }

        try {
            user.setStatus(true);
            if (null == user.getDescription()) {
                user.setDescription("");
            }
            user.setCreateUserId(0);
            user.setCreateTime(System.currentTimeMillis());
            user.setUpdateTime(System.currentTimeMillis());
            userTMapper.insert(user);
        } catch (Exception e) {
            return new OperationMessage(CustomErrorController.SERVICE_EXCEPTION, e.toString());
        }

        return new OperationMessage(0, "");
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
    public MessageInterface update(@RequestBody User user, BindingResult result) {
        if (null == user.getUserId() || 0 >= user.getUserId() || Integer.MAX_VALUE < user.getUserId()) {
            return new OperationMessage(CustomErrorController.USER_INPUT_EXCEPTION, "user id must not be null or must between 0 and " + Integer.toString(Integer.MAX_VALUE));
        }

        if (!IsUserIdExists(user.getUserId())) {
            return new OperationMessage(CustomErrorController.USER_INPUT_EXCEPTION, "user id: " + Integer.toString(user.getUserId()) + " is not exists");
        }

        if (IsUserNameExists(user.getUserName())) {
            return new OperationMessage(CustomErrorController.SERVICE_EXCEPTION, "user name:" + user.getUserName() + " is exists");
        }

        try {
            user.setUpdateTime(System.currentTimeMillis());
            userTMapper.updateByPrimaryKey(user);
        } catch (Exception e) {
            return new OperationMessage(CustomErrorController.SERVICE_EXCEPTION, e.toString());
        }

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
    public MessageInterface query(@Validated @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new OperationMessage(CustomErrorController.USER_INPUT_EXCEPTION, result.getAllErrors().get(0).getDefaultMessage());
        }
        return new QueryUserMessage(0, "", userTMapper.selectByPrimaryKey(user.getUserId()));
    }

    public Boolean IsUserNameExists(String userName) {
        if (null != userTMapper.selectByUserName(userName)) {
            return true;
        }

        return false;
    }

    public Boolean IsUserIdExists(Integer userId) {
        if (null != userTMapper.selectByPrimaryKey(userId)) {
            return true;
        }

        return false;
    }
}
