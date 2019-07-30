import api from '@/utils/api'

/**
 * @description 获取列表
 * @param {*} pageNum
 * @param {*} pageSize
 */
export function getAboutList (pageNum, pageSize, params) {
  return api({
    url: '/about/' + pageNum + '/' + pageSize,
    method: 'get',
    params: params
  })
}

/**
 * @description 创建
 */
export function createAbout (params) {
  return api({
    url: '/about',
    method: 'post',
    data: params
  })
}

/**
 * @description 删除
 * @param {*} id
 */
export function deleteAbout (id) {
  return api({
    url: '/about/' + id,
    method: 'delete'
  })
}

/**
 * @description 更新
 * @param {*} id
 */
export function updateAbout (id, params) {
  return api({
    url: '/about/' + id,
    method: 'put',
    data: params
  })
}
