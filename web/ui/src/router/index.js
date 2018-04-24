import Vue from 'vue'
import Router from 'vue-router'
import SystemMng from '@/components/system/systemList'
import PlatformMng from '@/components/platform/platformList'
import AuthorityMng from '@/components/authority/authorityList'
import GroupMng from '@/components/group/groupList'
import RoleMng from '@/components/role/roleList'
import OrgMng from '@/components/org/orgList'
import UserMng from '@/components/user/userList'
import UserGrantMng from '@/components/user/userGrant'
import GrantMng from '@/components/grant/grant'
import ChangePassword from '@/components/user/updatePwd'

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/'
    },
    {
      path: '/systemMng',
      name: 'systemMng',
      component: SystemMng
    },
    {
      path: '/platformMng',
      name: 'platformMng',
      component: PlatformMng
    },
    {
      path: '/authorityMng',
      name: 'authorityMng',
      component: AuthorityMng
    },
    {
      path: '/groupMng',
      name: 'groupMng',
      component: GroupMng
    },
    {
      path: '/roleMng',
      name: 'roleMng',
      component: RoleMng
    },
    {
      path: '/orgMng',
      name: 'orgMng',
      component: OrgMng
    },
    {
      path: '/userMng',
      name: 'userMng',
      component: UserMng
    },
    {
      path: '/grantMng',
      name: 'grantMng',
      component: GrantMng
    },
    {
      path: '/changePassword',
      name: 'changePassword',
      component: ChangePassword
    },
    {
      path: '/userGrantMng',
      name: 'userGrantMng',
      component: UserGrantMng
    }     
  ]
})
