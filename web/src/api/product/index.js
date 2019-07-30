import api from '@/utils/api'

/**
 * @description 查询产品列表
 */
export function getProductList (pageNum, pageSize) {
  return api({
    url: '/product/list/' + pageNum + '/' + pageSize
  })
}

/**
 * @description 查询产品详情
 */
export function getProductDetail (productKey) {
  return api({
    url: '/product/detail/' + productKey
  })
}

/**
 * @description 创建产品
 */
export function createProduct (params) {
  return api({
    url: '/product',
    method: 'post',
    data: params
  })
}

/**
 * @description 更新产品
 */
export function updateProduct (productKey, params) {
  return api({
    url: '/product/' + productKey,
    method: 'put',
    data: params
  })
}

/**
 * @description 删除产品
 */
export function deleteProduct (productKey) {
  return api({
    url: '/product/' + productKey,
    method: 'delete'
  })
}
