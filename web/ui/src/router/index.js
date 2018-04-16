import Vue from 'vue'
import Router from 'vue-router'
import SystemMng from '@/components/system/systemList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/'
    },
    {
      path: '/systemMng',
      name: 'systemMng',
      component: SystemMng
    }    
  ]
})
