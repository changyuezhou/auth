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
                        "auth_name": "账号管理",
                        "auth_level": 1,
                        "auth_f_name": "授权管理",
                        "auth_id": "12",
                        "url": "/accountMng"
                    }            
                ]
            },
            {
                "auth_f_id": "",
                "auth_name": "配置管理",
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
export default { auth_list, system_list, system_add, system_update, system_delete, platform_list, platform_add, platform_update, platform_delete }