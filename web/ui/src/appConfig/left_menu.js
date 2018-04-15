export default {
    left_menu_config: {
        f_auths: {
            1: {
                "label": "agentMng",
                "title": "品牌商管理",
                "icon": require('@/assets/images/leftNav/icons/agentMng.png'),
                "router": "",
                "index": 0
            },
            4: {
                "label": "systemConfig",
                "title": "系统配置",
                "icon": require('@/assets/images/leftNav/icons/systemConfig.png'),
                "router": "",
                "index": 1
            },
        },
        s_auths: {
            2: {
                "label": "",
                "title": "企业号配置",
                "icon": "",
                "router": "qyhConfig"
            },
            3: {
                "label": "",
                "title": "服务号配置",
                "icon": "",
                "router": "fwhConfig"
            },

            5: {
                "label": "",
                "title": "修改密码",
                "icon": "",
                "router": "updatePsw"
            },
            6: {
                "label": "",
                "title": "账号管理",
                "icon": "",
                "router": "accountMng"
            },
            7: {
                "label": "",
                "title": "角色权限",
                "icon": "",
                "router": "roleAuth"
            }
        }
    }
}


