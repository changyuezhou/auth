import "babel-polyfill"
import Vue from 'vue'
import sign from './SignApp.vue'

import '@/common/style/reset.less'
import utils from '@/common/utils.js'
import apiList from '@/common/apiList'

import store from '@/store' //vuex
Vue.use(store)

Vue.config.productionTip = false

Vue.prototype.utils = utils
Vue.prototype.apis = apiList

/* eslint-disable no-new */
new Vue({
  el: '#sign',
  store,
  render: h => h(sign)
})