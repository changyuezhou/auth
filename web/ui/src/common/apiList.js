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
    }
    /* =============================================================系统管理-end======================================================== */
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