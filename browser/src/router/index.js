import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/** note: submenu only apppear when children.length>=1
*   detail see  https://panjiachen.github.io/vue-element-admin-site/#/router-and-nav?id=sidebar
**/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/authredirect', component: _import('login/authredirect'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [{
      path: 'dashboard',
      component: _import('dashboard/index'),
      name: 'dashboard',
      meta: { title: '首页', icon: 'dashboard', noCache: true }
    }]
  },
  {
      path: '/register',
      component: _import('register/register'),
      hidden: true
  },
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [

  {
    path: '/system',
    component: Layout,
    meta: { perm:'m:sys', title: '系统', icon: 'chart' },
    children: [
      {
        path: 'user_manage',
        name: 'user_manage',
        component: _import('_system/user/index'),
        meta: { perm: 'm:sys:user', title: '用户管理', icon: 'chart', noCache: true }
      },
      {
        path: 'role_manage',
        name: 'role_manage',
        component: _import('_system/role/index'),
        meta: { perm: 'm:sys:role', title: '角色管理', icon: 'chart', noCache: true },
      },
      {
        hidden: true,
        path: 'role_manage/:roleId/assign_perm',
        name: 'role_manage_assign_perm',
        component: _import('_system/role/assign_perm'),
        meta: { hiddenTag: true , title: '角色授权'},
      },
      {
        path: 'perm_manage',
        name: 'perm_manage',
        component: _import('_system/perm/index'),
        meta: { perm: 'm:sys:perm', title: '权限管理', icon: 'chart', noCache: true }
      },
      {
        path: 'log',
        name: 'log',
        component: _import('_system/log'),
        meta: { perm: 'm:sys:perm', title: '操作日志', icon: 'chart', noCache: true }
      },
    ]
  },
  {
    path: '/person_message',
    component: Layout,
    children: [{
      path: 'person_message',
      name: '消息',
      component: _import('menu/user/message'),
      meta: { perm:'m:user:message', title: '个人消息', icon: 'icon' }
    }]
  },
  {
    path: '/employee',
    component: Layout,
    children: [{
      path: 'employee',
      name: 'employee',
      component: _import('menu/employee/schedule'),
      meta: { perm:'m:employee:management', title: '工作进度', icon: 'icon' }
    },
      {
        path: 'workorTable',
        name: 'workorTable',
        component: _import('menu/employee/workorTable'),
        meta: {perm: 'm:workorTable:management', title: '业务详情', icon: 'icon'},
        hidden:true
      }
      ,
      {
        path: 'myChart',
        name: 'myChart',
        component: _import('menu/employee/myChart'),
        meta: {perm: 'm:myChart:management', title: '跟踪详情', icon: 'icon'},
        hidden:true
      }
      ,
      {
        path: 'genzongxiangqing',
        name: 'genzongxiangqing',
        component: _import('menu/employee/genzongxiangqing'),
        meta: {perm: 'm:genzongxiangqing:management', title: '跟踪详情', icon: 'icon'},
        hidden:true
      }
    ]
  },

  {
    path: '/stumanage',
    component: Layout,
    meta: {
      perm:'m:menu3',
      title: '学员管理',
      icon: 'chart'
    },
    children: [
      { path: 'stutrace', component: _import('menu/student/trace'), name: 'stutrace', meta: { perm:'m:student:trace', title: '学员跟踪', icon: 'chart', noCache: true }},
      { path: 'stutraceinfo', component: _import('menu/student/TrackDetail'), name: 'stutraceinfo', meta: { perm:'m:student:TrackDetail', title: '跟踪详情', icon: 'chart', noCache: true },hidden:true},
      { path: 'allot', component: _import('menu/student/allot'), name: 'allot', meta: { perm:'m:student:allot', title: '简历分配', icon: 'chart', noCache: true }},
    ]
  },


  /*{
    path: '/menu4',
    name: 'menu4',
    component: Layout,
    redirect: '/menu4/menu4_1/a',
    meta: {
      perm:'m:menu4',
      title: '菜单4',
      icon: 'example'
    },
    children: [
      {
        path: '/menu4/menu4_1',
        name: 'menu4_1',
        component: _import('menu/menu4_1/index'),
        redirect: '/menu4/menu4_1/a',
        meta: {
          perm:'m:menu4:1',
          title: '菜单4-1',
          icon: 'table'
        },
        children: [
          { path: 'a', name: 'menu4_1_a', component: _import('menu/menu4_1/a'), meta: { perm:'m:menu4:1:a', title: '菜单4-1-a' }},
          { path: 'b', name: 'menu4_1_b', component: _import('menu/menu4_1/b'), meta: { perm:'m:menu4:1:b', title: '菜单4-1-b' }},
          { path: 'c', name: 'menu4_1_c', component: _import('menu/menu4_1/c'), meta: { perm:'m:menu4:1:c', title: '菜单4-1-c' }}
        ]
      },
      { path: 'menu4/menu4_2', name: 'menu4_2', icon: 'tab', component: _import('menu/menu4_2/index'), meta: {perm:'m:menu4:2', title: '菜单4-2' }}
    ]
  },*/

  { path: '*', redirect: '/404', hidden: true }
]
