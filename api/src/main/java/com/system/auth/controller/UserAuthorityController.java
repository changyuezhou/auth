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
import com.system.auth.dao.UserAuthorityMapper;
import com.system.auth.model.ext.UserAuthorityView;
import com.system.auth.model.ext.UserMenuAuthorityView;
import com.system.auth.model.request.UserAuthorityListCondition;
import com.system.auth.model.response.AuthorityAddResponse;
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
@RequestMapping(value = "/api/user_authority")

public class UserAuthorityController {
    @Autowired
    SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
    UserAuthorityMapper userAuthorityGroupMapper = session.getMapper(UserAuthorityMapper.class);

    @ApiOperation(value="查询用户权限菜单", notes="根据条件查询用户权限菜单信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/menu_list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<List<UserMenuAuthorityView>> menu_list(HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), "/menu_list", request, "", SystemLogging.getOperationStart());

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            ResponseMessage<List<UserMenuAuthorityView>> result = new ResponseMessage<List<UserMenuAuthorityView>>(302, user_info.getMsg(), null);

            return result;
        }

        UserAuthorityListCondition condition = new UserAuthorityListCondition();
        condition.setUserId(user_info.getData().getUserId());
        condition.setPlatformId(Auth.getPlatformId());

        List<UserAuthorityView> user_auth_list = userAuthorityGroupMapper.selectBySelective(condition);

        HashMap<String, UserMenuAuthorityView> menu_list = formatMenu(user_auth_list);

        List<UserMenuAuthorityView> list = new ArrayList<UserMenuAuthorityView>();
        Set entrySet = menu_list.entrySet();
        Iterator it = entrySet.iterator();
        while(it.hasNext()){
            Map.Entry me = (Map.Entry)it.next();
            UserMenuAuthorityView item = (UserMenuAuthorityView)me.getValue();
            list.add(item);
        }

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(list), SystemLogging.getSUCCESS());

        return new ResponseMessage<List<UserMenuAuthorityView>>(0, "", list);
    }

    @ApiOperation(value="查询用户权限列表", notes="根据条件查询用户权限列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<UserAuthorityView> list(@Validated @RequestBody UserAuthorityListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            QueryListMessage<UserAuthorityView> result = new QueryListMessage<UserAuthorityView>(302, user_info.getMsg(), null);

            return result;
        }

        condition.setUserId(user_info.getData().getUserId());

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        QueryListMessage<UserAuthorityView> result = queryUserAuthorityListByCondition(condition);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="查询用户权限列表", notes="根据条件查询用户权限列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/platform_authority", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public QueryListMessage<UserAuthorityView> platform_authority(@Validated @RequestBody UserAuthorityListCondition condition, BindingResult check, HttpServletRequest request) throws Exception {
        if (check.hasErrors()) {
            throw new OperationException(OperationException.getUserInputException(), check.getAllErrors().get(0).getDefaultMessage());
        }

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            QueryListMessage<UserAuthorityView> result = new QueryListMessage<UserAuthorityView>(302, user_info.getMsg(), null);

            return result;
        }

        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, "", SystemLogging.getOperationStart());

        QueryListMessage<UserAuthorityView> result = queryUserAuthorityListByCondition(condition);

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(result), SystemLogging.getSUCCESS());

        return result;
    }

    @ApiOperation(value="查询权限菜单", notes="获取权限菜单信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求服务器已处理"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；资源不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/tree", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseMessage<List<UserMenuAuthorityView>> menu_tree(@RequestBody UserAuthorityListCondition condition, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SystemLogging.Logging(SystemLogging.getINFO(), "/tree", request, "", SystemLogging.getOperationStart());

        UserInfo user_info = Auth.getUserInfo(request);
        if (302 == user_info.getCode() || null == user_info.getData()) {
            ResponseMessage<List<UserMenuAuthorityView>> result = new ResponseMessage<List<UserMenuAuthorityView>>(302, user_info.getMsg(), null);

            return result;
        }

        List<UserAuthorityView> user_auth_list = userAuthorityGroupMapper.selectBySelective(condition);

        HashMap<String, UserMenuAuthorityView> menu_list = formatMenu(user_auth_list);

        List<UserMenuAuthorityView> list = new ArrayList<UserMenuAuthorityView>();
        Set entrySet = menu_list.entrySet();
        Iterator it = entrySet.iterator();
        while(it.hasNext()){
            Map.Entry me = (Map.Entry)it.next();
            UserMenuAuthorityView item = (UserMenuAuthorityView)me.getValue();
            list.add(item);
        }

        SystemLogging.Logging(SystemLogging.getINFO(), mapper.writeValueAsString(condition), request, mapper.writeValueAsString(list), SystemLogging.getSUCCESS());

        return new ResponseMessage<List<UserMenuAuthorityView>>(0, "", list);
    }


    public QueryListMessage<UserAuthorityView> queryUserAuthorityListByCondition(UserAuthorityListCondition condition) {

        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<UserAuthorityView> user_auth_list = userAuthorityGroupMapper.selectBySelective(condition);
        PageInfo<UserAuthorityView> user_auth_list_info = new PageInfo<UserAuthorityView>(user_auth_list);
        QueryListMessage<UserAuthorityView> result = new QueryListMessage<UserAuthorityView>(0, "", user_auth_list);
        result.setPageNum(condition.getPageNum());
        result.setPageSize(condition.getPageSize());
        result.setTotalNum(user_auth_list_info.getTotal());

        return result;
    }

    public HashMap<String, UserMenuAuthorityView> formatMenu(List<UserAuthorityView> user_auth_list) {
        HashMap<String, UserMenuAuthorityView> menu_list = new HashMap<String, UserMenuAuthorityView>();

        for (UserAuthorityView item: user_auth_list) {
            UserMenuAuthorityView menu_item = new UserMenuAuthorityView();
            menu_item.setAuthFId(item.getAuthFId());
            menu_item.setAuthFName(item.getAuthFName());
            menu_item.setAuthId(item.getAuthId());
            menu_item.setAuthName(item.getAuthName());
            menu_item.setUrl(item.getUrl());

            if (0 == item.getAuthLevel()) {
                if (null  == menu_list.get(menu_item.getAuthId())) {
                    menu_list.put(menu_item.getAuthId(), menu_item);
                }
            } else if (1 == item.getAuthLevel()) {
                UserMenuAuthorityView parent = menu_list.get(item.getAuthFId());
                if (null == parent) {
                    parent = new UserMenuAuthorityView();
                    parent.setAuthId(item.getAuthFId());
                    parent.setAuthName(item.getAuthFName());
                    parent.setAuthFId(item.getAuthFId());
                    parent.setAuthFName(item.getAuthFName());

                    menu_list.put(parent.getAuthId(), parent);
                }

                if (null == parent.getChildren()) {
                    parent.setChildren(new ArrayList<UserMenuAuthorityView>());
                }

                if (!parent.getChildren().add(menu_item)) {
                    throw new OperationException(OperationException.getUserInputException(), "add menu:" + item.getAuthName() + " failed");
                }
            }
        }

        return menu_list;
    }
}
