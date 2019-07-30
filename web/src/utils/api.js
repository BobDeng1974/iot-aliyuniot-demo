import axios from 'axios'
// eslint-disable-next-line no-unused-vars
import { Message, MessageBox, Notification } from 'element-ui'
// eslint-disable-next-line no-unused-vars
import { getToken } from '@/utils/auth'
import store from '../store'
// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_URL, // api的base_url
  timeout: 15000 // 请求超时时间2
})

/**
 * 错误提示方法
 */

function errorTips (msg) {
  Notification.error({
    offset: 100,
    message: msg,
    title: '提示'
  })
}

// request拦截器
service.interceptors.request.use(
  config => {
    return config
  },
  error => {
    console.error(error)
    Promise.reject(error)
  }
)
// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === '1000') {
      return res
    }
    if (res.success === true) {
      return res
    }
    if (res.success === false) {
      errorTips(res.code + ':' + res.errorMessage)
      return Promise.reject(res.errorMessage)
    }
    if (res.status === 40002) {
      errorTips(res.status + ':' + res.message)
      return Promise.reject(res.message)
    }

    if (res.status === 0) {
      return res
    }

    if (res.code === 100) {
      return res.info
    }
    if (res.code === 20011) {
      Notification.error({
        offset: 100,
        message: res.msg,
        duration: 1000,
        title: '提示',
        onClose: () => {
          store.dispatch('FedLogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        }
      })
      // eslint-disable-next-line prefer-promise-reject-errors
      return Promise.reject('未登录')
    } else {
      errorTips(res.msg)
      return Promise.reject(res)
    }
  },

  error => {
    console.error('err' + error) // for debug
    errorTips(error.message)
    return Promise.reject(error)
  }
)

export default service
