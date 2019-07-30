import api from '@/utils/api'

/**
 * @description 查询所有设备状态
 */
export function getAllDeviceStatus (productKey) {
  return api({
    url: '/device/allStatus',
    method: 'get',
    params: { productKey: productKey }
  })
}

/**
 * @description 查询某产品下的设备
 * @param {*} productKey 产品Key
 * @param {*} pageNum 页数
 * @param {*} pageSize 每页显示条数
 */
export function queryDeviceByProduct (productKey, pageNum, pageSize) {
  return api({
    url: '/device/product/' + productKey + '/' + pageNum + '/' + pageSize,
    method: 'get'
  })
}

/**
 * 查询所有设备
 * @param {*} pageNum
 * @param {*} pageSize
 */
export function getDeviceList (pageNum, pageSize) {
  return api({
    url: '/device/all/' + pageNum + '/' + pageSize,
    method: 'get'
  })
}

/**
 * 查询指定设备状态
 * @param {*} iotId
 */
export function getDeviceStatus (iotId) {
  return api({
    url: '/device/single/' + iotId,
    method: 'get'
  })
}

/**
 * 查看设备详情
 * @param {*} iotId
 */
export function getDeviceDetail (iotId) {
  return api({
    url: '/device/detail/' + iotId,
    method: 'get'
  })
}

/**
 * 创建设备
 * @param {*} params
 */
export function addDevice (params) {
  return api({
    url: '/device/add',
    method: 'post',
    data: params
  })
}

/**
 * 删除设备
 * @param {*} iotId
 */
export function deleteDevice (iotId) {
  return api({
    url: '/device/delete/' + iotId,
    method: 'delete'
  })
}
