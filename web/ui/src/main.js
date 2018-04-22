// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import mockdata from "@/mock/mock"

import store from '@/store' //vuex
Vue.use(store)

import '@/common/style/reset.less'
import utils from '@/common/utils.js'
import apiList from '@/common/apiList'

Vue.config.productionTip = false

Vue.prototype.utils = utils
Vue.prototype.apis = apiList

//引入全局公用基础组件
import headGuild from '@/components/basic/positionGuild.vue' //头部您的位置
Vue.component('headGuild', headGuild)
import vButton from '@/components/basic/button.vue' //按钮
Vue.component('vButton', vButton)
import vDialog from '@/components/basic/dialog.vue' //dialog
Vue.component('vDialog', vDialog)
import vSelection from '@/components/basic/selection.vue' //下拉框
Vue.component('vSelection', vSelection)
import vTable from '@/components/basic/commonTable.vue' //通用table
Vue.component('vTable', vTable)
import vPagenation from '@/components/basic/pageNation.vue' //分页
Vue.component('vPagenation', vPagenation)
import vAlert from '@/components/basic/alertDialog.vue' //弹框
Vue.component('vAlert', vAlert)
import vToast from '@/components/basic/toast.vue' //提示tip
Vue.component('vToast', vToast)
import vCheckBox from '@/components/basic/checkBox.vue' //复选框
Vue.component('vCheckBox', vCheckBox)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
