import api from '@/utils/api'

/**
 * 添加分组
 * @param {*} params
 */
export function addGroup (params) {
  return api({
    url: '/group/add',
    method: 'post',
    data: params
  })
}

/**
 * 更新分组信息
 * @param {*} groupId
 * @param {*} params
 */
export function updateGroup (groupId, params) {
  return api({
    url: '/group/' + groupId,
    method: 'put',
    data: params
  })
}

/**
 * 删除分组
 * @param {*} groupId
 */
export function deleteGroup (groupId) {
  return api({
    url: '/group/' + groupId,
    method: 'delete'
  })
}

/**
 * 批量添加设备到分组
 * @param {*} params
 */
export function batchAddDeviceToGroup (groupId, params) {
  return api({
    url: '/group/addDevice/' + groupId,
    method: 'post',
    data: params
  })
}

/**
 * 批量从分组移除设备
 * @param {*} params
 */
export function batchDeleteDeviceToGroup (groupId, params) {
  return api({
    url: '/group/deleteDevice/' + groupId,
    method: 'delete',
    data: params
  })
}

/**
 * 查询分组详情
 * @param {*} groupId
 */
export function getGroupDetail (groupId) {
  return api({
    url: '/group/' + groupId,
    method: 'get'
  })
}

/**
 * 查询分组列表
 * @param {*} pageNum
 * @param {*} pageSize
 */
export function getGroupList (pageNum, pageSize) {
  return api({
    url: '/group/list/' + pageNum + '/' + pageSize,
    method: 'get'
  })
}

/**
 * 查询分组中的设备
 * @param {*} groupId
 * @param {*} pageNum
 * @param {*} pageSize
 */
export function getDeviceListByGroup (groupId, pageNum, pageSize) {
  return api({
    url: '/group/queryDevice/' + groupId + '/' + pageNum + '/' + pageSize,
    method: 'get'
  })
}
