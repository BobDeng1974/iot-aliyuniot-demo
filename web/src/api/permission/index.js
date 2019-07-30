import api from '@/utils/api'

/**
 * @description 查询所有权限列表
 */
export function getPermissionList (pageNum, pageSize, params) {
  return api({
    url: '/user/permission/' + pageNum + '/' + pageSize,
    method: 'get',
    params: params
  })
}

/**
 * @description 创建权限
 */
export function createPermission (params) {
  return api({
    url: '/user/permission',
    method: 'post',
    data: params
  })
}

/**
 * @description 更新权限
 */
export function updatePermission (params) {
  return api({
    url: '/user/permission',
    method: 'put',
    data: params
  })
}

/**
 * @description 删除权限
 */
export function deletePermission (id) {
  return api({
    url: '/user/permission/' + id,
    method: 'delete'
  })
}
