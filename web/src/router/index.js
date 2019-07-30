import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/404', component: _import('404'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: _import('dashboard/index'),
        name: '首页',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  }
]
export default new Router({
  // mode: 'history', // 后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/news',
    component: Layout,
    redirect: '/news/article',
    children: [
      {
        path: 'article',
        name: '文章管理',
        component: _import('article/article'),
        meta: { title: '文章管理', icon: 'example' },
        menu: 'article'
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    name: '',
    meta: { title: '系统管理', icon: 'setting' },
    children: [
      {
        path: 'user',
        name: '用户管理',
        component: _import('system/user'),
        meta: { title: '用户管理', icon: 'user-setting' },
        menu: 'user'
      },
      {
        path: 'role',
        name: '角色管理',
        component: _import('system/role'),
        meta: { title: '角色管理', icon: 'role' },
        menu: 'role'
      },
      {
        path: 'permission',
        name: '权限管理',
        component: _import('system/permission'),
        meta: { title: '权限管理', icon: 'permission' },
        menu: 'permission'
      }
    ]
  },
  {
    path: '',
    component: Layout,
    redirect: '/product',
    meta: { title: 'IOT 管理', icon: 'iot' },
    children: [
      {
        path: 'product',
        name: '产品管理',
        component: _import('product/product'),
        meta: { title: '产品管理', icon: 'product' },
        menu: 'product'
      },
      {
        path: 'product/detail/:productKey',
        name: '产品详情',
        component: _import('product/detail/detail'),
        meta: {
          title: '产品详情',
          icon: 'product',
          activeMenu: '/product'
        },
        menu: 'product',
        hidden: true
      },
      {
        path: 'device',
        name: '设备管理',
        component: _import('device/device'),
        meta: { title: '设备管理', icon: 'device' },
        menu: 'device'
      },
      {
        path: 'device/detail/:iotId',
        name: '设备详情',
        component: _import('device/detail/detail'),
        meta: {
          title: '设备详情',
          icon: 'product',
          activeMenu: '/device'
        },
        menu: 'device',
        hidden: true
      },
      {
        path: 'group',
        name: '房间管理',
        component: _import('group/group'),
        meta: { title: '房间管理', icon: 'group' },
        menu: 'group'
      },
      {
        path: 'group/detail/:groupId',
        name: '房间详情',
        component: _import('group/detail/detail'),
        meta: {
          title: '房间详情',
          icon: 'group',
          activeMenu: '/group'
        },
        menu: 'group',
        hidden: true
      }
    ]
  },
  {
    path: '',
    component: Layout,
    redirect: '/alarm',
    children: [
      {
        path: 'alarm',
        name: '告警管理',
        component: _import('alarm/alarm'),
        meta: { title: '告警管理', icon: 'message' },
        menu: 'alarm'
      }
    ]
  },
  {
    path: '',
    component: Layout,
    redirect: '/about',
    children: [
      {
        path: 'about',
        name: '关于我们',
        component: _import('about/about'),
        meta: { title: '关于我们', icon: 'component' },
        menu: 'about'
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]
