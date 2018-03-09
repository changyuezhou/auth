package com.system.auth.controller;

import com.system.auth.bean.OperationMessage;
import com.system.auth.bean.QueryUserMessage;
import com.system.auth.dao.UserMapper;
import com.system.auth.model.User;
import com.system.auth.util.MybatisUtil;
import io.swagger.annotations.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    UserMapper userTMapper = session.getMapper(UserMapper.class);

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细信息", required = true, dataType = "User")
    @ApiModelProperty(value = "用户ID", name = "userId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public OperationMessage add(@RequestBody User user) {
        userTMapper.insert(user);

        return new OperationMessage(0, "");
    }

    @ApiOperation(value="查询用户", notes="根据用户ID查询用户详细信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public QueryUserMessage query(@RequestBody User user) {
        return new QueryUserMessage(0, "", userTMapper.selectByPrimaryKey(user.getUserId()));
    }
}
