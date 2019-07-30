import api from '@/utils/api'

/**
 * @description 获取系统信息
 */
export function getSystemInfo () {
  return api({
    url: '/system',
    methods: 'get'
  })
}
