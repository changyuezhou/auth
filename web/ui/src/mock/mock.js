import Mock from 'mockjs';
 const auth_list = Mock.mock(
 '/api/user/auth_list','post', (req, res) => {
    return  {
        "msg": "测试文字,获取所有权限列表",
        "code": 0,
        "data": {
            "list":[
            {
                "auth_f_id": "",
                "auth_name": "授权管理",
                "auth_level": 0,
                "auth_f_name": "",
                "auth_id": "1",
                "url": "",
                "children":[
                    {
                        "auth_f_id": "",
                        "auth_name": "系统管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "6",
                        "url": "/systemMng"
                    },
                    {
                        "auth_f_id": "",
                        "auth_name": "权限管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "7",
                        "url": "/authorityMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "平台管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "8",
                        "url": "/platformMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "组管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "9",
                        "url": "/groupMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "角色管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "10",
                        "url": "/roleMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "组织管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "11",
                        "url": "/orgMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "用户管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "12",
                        "url": "/userMng"
                    }            
                ]
            },
            {
                "auth_f_id": "",
                "auth_name": "系统配置",
                "auth_level": 0,
                "auth_f_name": "",
                "auth_id": "2",
                "url": "",
                "children":[
                    {
                        "auth_f_id": "2",
                        "auth_name": "修改密码",
                        "auth_level": 1,
                        "auth_f_name": "配置管理",
                        "auth_id": "20",
                        "url": "/changePassword"
                    }            
                ]
            }
        ]
        }
    }
})

const system_list = Mock.mock(
  '/api/system/list','post', (req, res) => {
    return  {
        "msg": "测试系统，获取系统列表",
        "code": 0,
        "data": {
            "page_size": 10,
            "page_number": 1,
            "total_number": 1,
            "list":[
                {
                    "systemId": "S20F26F15C04D4727A9FAE7D3C322771E",
                    "systemName": "OAuth权限管理系统",
                    "description": "备注信息",
                    "createUserId": "U001",
                    "createUserName": "管理员",
                    "updateTime": 1569889900,
                    "createTime": 1569889900
                }
            ]
        }
    }
  }
)

const system_add = Mock.mock(
  '/api/system/add','post', (req, res) => {
    return  {
        "msg": "测试系统,添加系统",
        "code": 0
    }
  }
)

const system_update = Mock.mock(
  '/api/system/update','post', (req, res) => {
    return  {
        "msg": "测试系统,修改系统",
        "code": 0
    }
  }
)

const system_delete = Mock.mock(
  '/api/system/delete','post', (req, res) => {
    return  {
        "msg": "测试系统,删除系统",
        "code": 0
    }
  }
)

const platform_list = Mock.mock(
  '/api/platform/list','post', (req, res) => {
    return  {
        "msg": "测试系统，获取平台列表",
        "code": 0,
        "data": {
            "page_size": 10,
            "page_number": 1,
            "total_number": 1,
            "list":[
                {
                    "platformId": "P386ADA9F63B54EECB2B49663043CC744",
                    "platformName": "走向智能广告投放系统",
                    "secretKey": "D3FGH5GH7KLM",
                    "platformDomain": "www.platform.com",
                    "systemId": "S20F26F15C04D4727A9FAE7D3C322771E",
                    "systemName": "OAuth权限管理系统",
                    "description": "备注信息",
                    "createUserId": "U001",
                    "createUserName": "管理员",
                    "updateTime": 1569889900,
                    "createTime": 1569889900
                }
            ]
        }
    }
  }
)

const platform_add = Mock.mock(
  '/api/platform/add','post', (req, res) => {
    return  {
        "msg": "测试系统,添加平台",
        "code": 0
    }
  }
)

const platform_update = Mock.mock(
  '/api/platform/update','post', (req, res) => {
    return  {
        "msg": "测试系统,修改平台",
        "code": 0
    }
  }
)

const platform_delete = Mock.mock(
  '/api/platform/delete','post', (req, res) => {
    return  {
        "msg": "测试系统,删除平台",
        "code": 0
    }
  }
)

const authority_list = Mock.mock(
  '/api/authority/list','post', (req, res) => {
    return  {
        "msg": "测试系统，获取权限列表",
        "code": 0,
        "data": {
            "page_size": 10,
            "page_number": 1,
            "total_number": 1,
            "list":[
                {
                    "authId": "A386ADA9F63B54EECB2B49663043CC744",
                    "authName": "授权管理",
                    "authFId": "",
                    "authFName": "",
                    "authLevel": 0,
                    "url": "/qqqqqqq",
                    "systemId": "S20F26F15C04D4727A9FAE7D3C322771E",
                    "systemName": "OAuth权限管理系统",
                    "description": "备注信息",
                    "createUserId": "U001",
                    "createUserName": "管理员",
                    "updateTime": 1569889900,
                    "createTime": 1569889900
                }
            ]
        }
    }
  }
)

const authority_add = Mock.mock(
  '/api/authority/add','post', (req, res) => {
    return  {
        "msg": "测试系统,添加权限",
        "code": 0
    }
  }
)

const authority_update = Mock.mock(
  '/api/authority/update','post', (req, res) => {
    return  {
        "msg": "测试系统,修改权限",
        "code": 0
    }
  }
)

const authority_delete = Mock.mock(
  '/api/authority/delete','post', (req, res) => {
    return  {
        "msg": "测试系统,删除权限",
        "code": 0
    }
  }
)

const group_list = Mock.mock(
  '/api/group/list','post', (req, res) => {
    return  {
        "msg": "测试系统，获取组列表",
        "code": 0,
        "data": {
            "page_size": 10,
            "page_number": 1,
            "total_number": 1,
            "list":[
                {
                    "groupId": "G386ADA9F63B54EECB2B49663043CC744",
                    "groupName": "默认组",
                    "platformId": "P386ADA9F63B54EECB2B49663043CC744",
                    "platformName": "走向智能广告投放系统",
                    "description": "备注信息",
                    "createUserId": "U001",
                    "createUserName": "管理员",
                    "updateTime": 1569889900,
                    "createTime": 1569889900
                }
            ]
        }
    }
  }
)

const group_add = Mock.mock(
  '/api/group/add','post', (req, res) => {
    return  {
        "msg": "测试系统,添加组",
        "code": 0
    }
  }
)

const group_update = Mock.mock(
  '/api/group/update','post', (req, res) => {
    return  {
        "msg": "测试系统,修改组",
        "code": 0
    }
  }
)

const group_delete = Mock.mock(
  '/api/group/delete','post', (req, res) => {
    return  {
        "msg": "测试系统,删除组",
        "code": 0
    }
  }
)

const group_update_auth = Mock.mock(
  '/api/group/update_auth','post', (req, res) => {
    return  {
        "msg": "测试系统,更改组权限",
        "code": 0
    }
  }
)

const group_auth_list = Mock.mock(
 '/api/group/auth_list','post', (req, res) => {
    return  {
        "msg": "测试文字,获取组所有权限列表",
        "code": 0,
        "data": {
            "list":[
            {
                "auth_f_id": "",
                "auth_name": "授权管理",
                "auth_level": 0,
                "auth_f_name": "",
                "auth_id": "1",
                "url": "",
                "children":[
                    {
                        "auth_f_id": "",
                        "auth_name": "系统管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "6",
                        "url": "/systemMng"
                    },
                    {
                        "auth_f_id": "",
                        "auth_name": "权限管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "7",
                        "url": "/authorityMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "平台管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "8",
                        "url": "/platformMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "组管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "9",
                        "url": "/groupMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "角色管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "10",
                        "url": "/roleMng"
                    }          
                ]
            },
            {
                "auth_f_id": "",
                "auth_name": "系统配置",
                "auth_level": 0,
                "auth_f_name": "",
                "auth_id": "2",
                "url": "",
                "children":[
                    {
                        "auth_f_id": "2",
                        "auth_name": "修改密码",
                        "auth_level": 1,
                        "auth_f_name": "配置管理",
                        "auth_id": "20",
                        "url": "/changePassword"
                    }            
                ]
            }
        ]
        }
    }
})

const role_list = Mock.mock(
  '/api/role/list','post', (req, res) => {
    return  {
        "msg": "测试系统，获取角色列表",
        "code": 0,
        "data": {
            "page_size": 10,
            "page_number": 1,
            "total_number": 1,
            "list":[
                {
                    "roleId": "R386ADA9F63B54EECB2B49663043CC744",
                    "roleName": "管理员",
                    "platformId": "P386ADA9F63B54EECB2B49663043CC744",
                    "platformName": "走向智能广告投放系统",
                    "description": "备注信息",
                    "createUserId": "U001",
                    "createUserName": "管理员",
                    "updateTime": 1569889900,
                    "createTime": 1569889900
                }
            ]
        }
    }
  }
)

const role_add = Mock.mock(
  '/api/role/add','post', (req, res) => {
    return  {
        "msg": "测试系统,添加角色",
        "code": 0
    }
  }
)

const role_update = Mock.mock(
  '/api/role/update','post', (req, res) => {
    return  {
        "msg": "测试系统,修改角色",
        "code": 0
    }
  }
)

const role_delete = Mock.mock(
  '/api/role/delete','post', (req, res) => {
    return  {
        "msg": "测试系统,删除角色",
        "code": 0
    }
  }
)

const role_update_auth = Mock.mock(
  '/api/role/update_auth','post', (req, res) => {
    return  {
        "msg": "测试系统,更改角色权限",
        "code": 0
    }
  }
)

 const role_auth_list = Mock.mock(
 '/api/role/auth_list','post', (req, res) => {
    return  {
        "msg": "测试文字,获取角色所有权限列表",
        "code": 0,
        "data": {
            "list":[
            {
                "auth_f_id": "",
                "auth_name": "授权管理",
                "auth_level": 0,
                "auth_f_name": "",
                "auth_id": "1",
                "url": "",
                "children":[
                    {
                        "auth_f_id": "",
                        "auth_name": "系统管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "6",
                        "url": "/systemMng"
                    },
                    {
                        "auth_f_id": "",
                        "auth_name": "权限管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "7",
                        "url": "/authorityMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "平台管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "8",
                        "url": "/platformMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "组管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "9",
                        "url": "/groupMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "角色管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "10",
                        "url": "/roleMng"
                    }          
                ]
            },
            {
                "auth_f_id": "",
                "auth_name": "系统配置",
                "auth_level": 0,
                "auth_f_name": "",
                "auth_id": "2",
                "url": "",
                "children":[
                    {
                        "auth_f_id": "2",
                        "auth_name": "修改密码",
                        "auth_level": 1,
                        "auth_f_name": "配置管理",
                        "auth_id": "20",
                        "url": "/changePassword"
                    }            
                ]
            }
        ]
        }
    }
})

const organization_list = Mock.mock(
  '/api/organization/list','post', (req, res) => {
    return  {
        "msg": "测试系统，获取组织列表",
        "code": 0,
        "data": {
            "page_size": 10,
            "page_number": 1,
            "total_number": 1,
            "list":[
                {
                    "organizationId": "O386ADA9F63B54EECB2B49663043CC744",
                    "organizationName": "总裁办",
                    "organizationFId": "",
                    "organizationFName": "",
                    "organizationLevel": 0,
                    "platformId": "P386ADA9F63B54EECB2B49663043CC744",
                    "platformName": "走向智能广告投放系统",
                    "description": "备注信息",
                    "createUserId": "U001",
                    "createUserName": "管理员",
                    "updateTime": 1569889900,
                    "createTime": 1569889900
                }
            ]
        }
    }
  }
)

const organization_add = Mock.mock(
  '/api/organization/add','post', (req, res) => {
    return  {
        "msg": "测试系统,添加组织",
        "code": 0
    }
  }
)

const organization_update = Mock.mock(
  '/api/organization/update','post', (req, res) => {
    return  {
        "msg": "测试系统,修改组织",
        "code": 0
    }
  }
)

const organization_delete = Mock.mock(
  '/api/organization/delete','post', (req, res) => {
    return  {
        "msg": "测试系统,删除组织",
        "code": 0
    }
  }
)

const organization_update_auth = Mock.mock(
  '/api/organization/update_auth','post', (req, res) => {
    return  {
        "msg": "测试系统,更改组织权限",
        "code": 0
    }
  }
)

 const organization_auth_list = Mock.mock(
 '/api/organization/auth_list','post', (req, res) => {
    return  {
        "msg": "测试文字,获取组织所有权限列表",
        "code": 0,
        "data": {
            "list":[
            {
                "auth_f_id": "",
                "auth_name": "授权管理",
                "auth_level": 0,
                "auth_f_name": "",
                "auth_id": "1",
                "url": "",
                "children":[
                    {
                        "auth_f_id": "",
                        "auth_name": "系统管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "6",
                        "url": "/systemMng"
                    },
                    {
                        "auth_f_id": "",
                        "auth_name": "权限管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "7",
                        "url": "/authorityMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "平台管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "8",
                        "url": "/platformMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "组管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "9",
                        "url": "/groupMng"
                    },
                    {
                        "auth_f_id": "1",
                        "auth_name": "角色管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "10",
                        "url": "/roleMng"
                    }          
                ]
            },
            {
                "auth_f_id": "",
                "auth_name": "系统配置",
                "auth_level": 0,
                "auth_f_name": "",
                "auth_id": "2",
                "url": "",
                "children":[
                    {
                        "auth_f_id": "2",
                        "auth_name": "修改密码",
                        "auth_level": 1,
                        "auth_f_name": "配置管理",
                        "auth_id": "20",
                        "url": "/changePassword"
                    }            
                ]
            }
        ]
        }
    }
})

const user_list = Mock.mock(
  '/api/user/list','post', (req, res) => {
    return  {
        "msg": "测试系统，获取用户列表",
        "code": 0,
        "data": {
            "page_size": 10,
            "page_number": 1,
            "total_number": 1,
            "list":[
                {
                    "userId": "U386ADA9F63B54EECB2B49663043CC744",
                    "userName": "test@system.com",
                    "mobileNumber": "13800000000",
                    "contackName": "张帅",
                    "description": "备注信息",
                    "createUserId": "U001",
                    "createUserName": "管理员",
                    "updateTime": 1569889900,
                    "createTime": 1569889900
                }
            ]
        }
    }
  }
)

const user_add = Mock.mock(
  '/api/user/add','post', (req, res) => {
    return  {
        "msg": "测试系统,添加用户",
        "code": 0
    }
  }
)

const user_update = Mock.mock(
  '/api/user/update','post', (req, res) => {
    return  {
        "msg": "测试系统,修改用户",
        "code": 0
    }
  }
)

const user_delete = Mock.mock(
  '/api/user/delete','post', (req, res) => {
    return  {
        "msg": "测试系统,删除用户",
        "code": 0
    }
  }
)

const user_query_by_name = Mock.mock(
  '/api/user/query_by_name','post', (req, res) => {
    return  {
        "msg": "测试系统,查询用户",
        "code": 0,
        "data": {
            "userName": "测试用户",
            "userId": "U386ADA9F63B54EECB2B49663043CC744"
        }
    }
  }
)

const user_update_password = Mock.mock(
  '/api/user/update_password','post', (req, res) => {
    return  {
        "msg": "测试系统,更改用户密码",
        "code": 0
    }
  }
)

export default { auth_list, system_list, system_add, system_update, system_delete, 
    platform_list, platform_add, platform_update, platform_delete, 
    authority_list, authority_add, authority_update, authority_delete,
    group_list, group_add, group_update, group_delete,
    role_list, role_add, role_update, role_delete,
    organization_list, organization_add, organization_update, organization_delete,
    user_list, user_add, user_update, user_delete, user_query_by_name }