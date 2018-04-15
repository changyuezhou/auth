import Vue from 'vue'
import Router from 'vue-router'
import HeadBar from '@/components/head_bar'
import Index from '@/components/index'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/fwhConfig',
      name: 'Index',
      component: Index
    }
  ]
})
