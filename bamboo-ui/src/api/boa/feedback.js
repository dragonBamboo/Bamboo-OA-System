import request from '@/utils/request'

// 查询员工反馈列表
export function listFeedback(query) {
  return request({
    url: '/boa/feedback/list',
    method: 'get',
    params: query
  })
}

// 查询员工反馈详细
export function getFeedback(id) {
  return request({
    url: '/boa/feedback/' + id,
    method: 'get'
  })
}

// 新增员工反馈
export function addFeedback(data) {
  return request({
    url: '/boa/feedback',
    method: 'post',
    data: data
  })
}

// 修改员工反馈
export function updateFeedback(data) {
  return request({
    url: '/boa/feedback',
    method: 'put',
    data: data
  })
}

// 删除员工反馈
export function delFeedback(id) {
  return request({
    url: '/boa/feedback/' + id,
    method: 'delete'
  })
}
