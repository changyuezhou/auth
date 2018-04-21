import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import store from '@/store/index.js'

Vue.use(VueAxios, axios)

//axios配置
axios.defaults.baseURL = "http://192.168.56.101:8081/api"
    //axios响应拦截器 
axios.interceptors.response.use((res) => {
    //如果返回302重定向
    if (res.data.code == 302) {
        location.href = res.data.redirect_url
        return
    }
    //如果范围状态是unauthorized 将返回信息设置为未授权
    if (!res.data.isSuccess && res.data.msg === "unauthorized") {
        res.data.msg = "未授权使用该功能"
    }
    return res;
}, (err) => {
    return Promise.reject(error);
})




export default {
    /* =============================================================获取左侧菜单-start======================================================== */
    getLeftMenuList(){
        return postFun("/user/auth_list")
    },
    /* =============================================================获取左侧菜单-end======================================================== */
    
    
    /* =============================================================系统管理-start======================================================== */
    //获取系统列表
    getSystemList(page_number = 1, page_size = 10) {
        let data = { page_number, page_size }
        return postFun('/system/list', data)
    },
    //添加系统
    addSystem(data) {
        return postFun('/system/add', data)
    },
    //修改系统
    updateSystem(data) {
        return postFun('/system/update', data)
    },
    //删除系统
    deleteSystem(systemId) {
        return postFun('/system/delete', { systemId })
    },
    /* =============================================================系统管理-end======================================================== */

    /* =============================================================平台管理-start======================================================== */
    //获取平台列表
    getPlatformList(page_number = 1, page_size = 10) {
        let data = { page_number, page_size }
        return postFun('/platform/list', data)
    },
    //添加平台
    addPlatform(data) {
        return postFun('/platform/add', data)
    },
    //修改平台
    updatePlatform(data) {
        return postFun('/platform/update', data)
    },
    //删除平台
    deletePlatform(platformId) {
        return postFun('/platform/delete', { platformId })
    },
    /* =============================================================平台管理-end======================================================== */    

/* =============================================================权限管理-start======================================================== */
    //获取权限列表
    getAuthorityList(page_number = 1, page_size = 10) {
        let data = { page_number, page_size }
        return postFun('/authority/list', data)
    },
    //添加权限
    addAuthority(data) {
        return postFun('/authority/add', data)
    },
    //修改权限
    updateAuthority(data) {
        return postFun('/authority/update', data)
    },
    //删除权限
    deleteAuthority(authorityId) {
        return postFun('/authority/delete', { authorityId })
    },
    /* =============================================================权限管理-end======================================================== */ 

/* =============================================================组管理-start======================================================== */
    //获取组列表
    getGroupList(page_number = 1, page_size = 10) {
        let data = { page_number, page_size }
        return postFun('/group/list', data)
    },
    //添加组
    addGroup(data) {
        return postFun('/group/add', data)
    },
    //修改组
    updateGroup(data) {
        return postFun('/group/update', data)
    },
    //删除组
    deleteGroup(groupId) {
        return postFun('/group/delete', { groupId })
    },
    //获取组权限列表
    getGroupAuthorityList(groupId) {
        let data = { groupId }
        return postFun('/group/auth_list', data)
    },
    //更改组权限列表
    updateGroupAuthority(groupId, auth_list) {
        let data = { groupId, auth_list }
        return postFun('/group/update_auth', data)
    },       
    /* =============================================================组管理-end======================================================== */

/* =============================================================角色管理-start======================================================== */
    //获取角色列表
    getRoleList(page_number = 1, page_size = 10) {
        let data = { page_number, page_size }
        return postFun('/role/list', data)
    },
    //添加角色
    addRole(data) {
        return postFun('/role/add', data)
    },
    //修改角色
    updateRole(data) {
        return postFun('/role/update', data)
    },
    //删除角色
    deleteRole(roleId) {
        return postFun('/role/delete', { roleId })
    },
    //获取角色权限列表
    getRoleAuthorityList(roleId) {
        let data = { roleId }
        return postFun('/role/auth_list', data)
    },
    //更改角色权限列表
    updateRoleAuthority(roleId, auth_list) {
        let data = { roleId, auth_list }
        return postFun('/role/update_auth', data)
    },        
    /* =============================================================角色管理-end======================================================== */

    /* =============================================================组织管理-start======================================================== */
    //获取组织列表
    getOrganizationList(page_number = 1, page_size = 10) {
        let data = { page_number, page_size }
        return postFun('/organization/list', data)
    },
    //添加组织
    addOrganization(data) {
        return postFun('/organization/add', data)
    },
    //修改组织
    updateOrganization(data) {
        return postFun('/organization/update', data)
    },
    //删除组织
    deleteOrganization(organizationId) {
        return postFun('/organization/delete', { organizationId })
    },
    //获取组织权限列表
    getOrganizationAuthorityList(organizationId) {
        let data = { organizationId }
        return postFun('/organization/auth_list', data)
    },
    //更改组织权限列表
    updateOrganizationAuthority(organizationId, auth_list) {
        let data = { organizationId, auth_list }
        return postFun('/organization/update_auth', data)
    },       
    /* =============================================================组织管理-end======================================================== */

/* =============================================================用户管理-start======================================================== */
    //获取用户列表
    getUserList(page_number = 1, page_size = 10) {
        let data = { page_number, page_size }
        return postFun('/user/list', data)
    },
    //添加用户
    addUser(data) {
        return postFun('/user/add', data)
    },
    //修改用户
    updateUser(data) {
        return postFun('/user/update', data)
    },
    //删除用户
    deleteUser(userId) {
        return postFun('/user/delete', { userId })
    },
    //删除用户
    getUserByName(userName) {
        return postFun('/user/query_by_name', { userName })
    },
    //更改用户密码
    updatePassword(userId, oldPwd, newPwd) {
        let data = { userId, oldPwd, newPwd }
        return postFun('/user/update_password', data)
    },
    //更改用户角色
    updateUserRole(userId, roleIds) {
        let data = { userId, roleIds }
        return postFun('/user_role/update', data)
    },
    //更改用户组
    updateUserGroup(userId, groupIds) {
        let data = { userId, groupIds }
        return postFun('/user_group/update', data)
    },
    //更改用户组织
    updateUserOrganization(userId, organizationIds) {
        let data = { userId, organizationIds }
        return postFun('/user_organization/update', data)
    },
    getUserGroup(userId) {
        return postFun('/user_group/list', { userId })
    },
    getUserRole(userId) {
        return postFun('/user_role/list', { userId })
    },
    getUserOrganization(userId) {
        return postFun('/user_organization/list', { userId })
    }      
    /* =============================================================用户管理-end======================================================== */

}


function postFun(url, data={}) {

    return new Promise((resolve, reject) => {
        store.commit('SHOW_LOADING')
        Vue.axios.post(url, data)
            .then((res) => {
                store.commit('HIDE_LOADING')
                if (res.data.code == 0) {
                    resolve(res.data)
                } else {
                    reject(res.data.msg || "服务器未定义错误")
                }
            }).catch((err) => {
                store.commit('HIDE_LOADING')
                reject("网络错误")
            })
    })
}