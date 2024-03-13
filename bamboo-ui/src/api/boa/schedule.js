import request from '@/utils/request'

// 查询日志列表
export function listSchedule(query) {
  return request({
    url: '/boa/schedule/list',
    method: 'get',
    params: query
  })
}

// 查询日志详细
export function getSchedule(id) {
  return request({
    url: '/boa/schedule/' + id,
    method: 'get'
  })
}

// 新增日志
export function addSchedule(data) {
  return request({
    url: '/boa/schedule',
    method: 'post',
    data: data
  })
}

// 修改日志
export function updateSchedule(data) {
  return request({
    url: '/boa/schedule',
    method: 'put',
    data: data
  })
}

// 删除日志
export function delSchedule(id) {
  return request({
    url: '/boa/schedule/' + id,
    method: 'delete'
  })
}
