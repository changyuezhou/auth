import Vue from 'vue'
import Router from 'vue-router'
import HeadBar from '@/components/head_bar'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HeadBar',
      component: HeadBar
    }
  ]
})
