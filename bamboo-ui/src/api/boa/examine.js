import request from '@/utils/request'

// 查询审批管理列表
export function listExamine(query) {
  return request({
    url: '/boa/examine/list',
    method: 'get',
    params: query
  })
}

// 查询审批管理详细
export function getExamine(id) {
  return request({
    url: '/boa/examine/' + id,
    method: 'get'
  })
}

// 新增审批管理
export function addExamine(data) {
  return request({
    url: '/boa/examine',
    method: 'post',
    data: data
  })
}

// 修改审批管理
export function updateExamine(data) {
  return request({
    url: '/boa/examine',
    method: 'put',
    data: data
  })
}

// 删除审批管理
export function delExamine(id) {
  return request({
    url: '/boa/examine/' + id,
    method: 'delete'
  })
}
