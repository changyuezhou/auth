import Vue from 'vue'
import vuex from 'vuex'
Vue.use(vuex)


import global_alert from './components/global_alert.js';
import global_toast from './components/global_toast.js';
import LOADING from './components/loading.js';

export default new vuex.Store({
    modules: {
        global_alert,
        global_toast,
        LOADING
    }
})