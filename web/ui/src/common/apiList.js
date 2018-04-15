import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import store from '@/store/index.js'


Vue.use(VueAxios, axios)



//axios配置
axios.defaults.baseURL = "/api"
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
    
    
    /* =============================================================品牌商管理-start======================================================== */
    //获取企业号账号列表
    getQyhAccountList(page_number = 1, page_size = 10) {
        let data = { page_number, page_size }
        return postFun('/qyh_user/query_list', data)
    },
    //添加企业号账号
    addQyhAccount(data) {
        return postFun('/qyh_user/add', data)
    },
    //删除企业号账号
    deleteQyhAccount(user_id, platform_name) {
        return postFun('/qyh_user/delete', { user_id, platform_name })
    },
    //修改企业号账号
    updateQyhAccount(data) {
        return postFun('/qyh_user/update', data)
    },
    //重置企业号账号密码
    resetQyhPassword(user_id, password) {
        return postFun("/qyh_user/reset_password", { user_id, password })
    },
    //获取服务号账号列表
    getFwhAccountList(page_number = 1, page_size = 10) {
        return postFun('/fwh_user/query_list', { page_number, page_size })
    },
    //添加服务号账号
    addFwhAccount(data) {
        return postFun('/fwh_user/add', data)
    },
    //删除服务号账号
    deleteFwhAccount(user_id, platform_name) {
        return postFun("/fwh_user/delete", { user_id, platform_name })
    },
    //修改服务号账号
    updateFwhAccount(data) {
        return postFun('/fwh_user/update', data)
    },
    //重置服务号账号密码
    resetFwhPassword(user_id, password) {
        return postFun('/fwh_user/reset_password', { user_id, password })
    },


    /* =============================================================品牌商管理-end======================================================== */


    /* =============================================================系统配置-start======================================================== */
    //修改当前用户密码
    updatePassword(old_password, new_password) {
        let data = { old_password, new_password }
        return postFun('/user/change_password', data)
    },
    //获取账号列表
    getAccountList(page_number = 1, page_size = 10) {
        let data = { page_size, page_number }
        return postFun('/user/query_by_conditions', data)
    },

    //添加账号
    addAccount(data) {
        return postFun('/user/add', data)
    },

    //修改账号
    updateAccount(data) {
        return postFun('/user/update', data)
    },
    //删除账号
    deleteAccount(user_id) {
        return postFun('/user/delete', { "user_id": [user_id] })
    },
    //重置账号密码
    resetAccountPassword(data) {
        return postFun('/user/reset_password', data)
    },

    //获取角色列表
    getRoleList(page_number = 1, page_size = 100) {
        let data = { page_number, page_size }
        return postFun('/role/query_list', data)
    },

    //添加角色
    addRole(role_name) {
        let data = { role_name }
        return postFun('/role/add', data)
    },
    //修改角色
    updateRole(role_id, role_name) {
        let data = { role_id, role_name }
        return postFun('/role/update', data)
    },
    //删除角色
    deleteRole(role_id) {
        let data = { role_id: [role_id] }
        return postFun('/role/delete', data)
    },

    //获取权限列表
    getAuthList(role_id,page_number = 1, page_size = 100) {
        let data = {role_id,page_number, page_size }
        return postFun('/auth/query_list', data)
    },

    //获取角色权限列表
    getRoleAuthList(role_id) {
        let data = { "role_id": role_id, "page_number": 1, "page_size": 999999 }
        return postFun('/auth/query_role_list', data)
    },
    //修改角色权限
    updateRoleAuth(role_id, auth_id) {
        let data = { role_id, auth_id }
        return postFun('/role/update_auth', data)
    },
    /* ===============================================================系统配置-end============================================================ */
    
    /* ===============================================================其他-start============================================================ */
    //判断用户名是否重复
    checkAccount(user_name){
        return postFun('/user/query_by_name',{user_name})
    }
    /* ===============================================================其他-end============================================================ */
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