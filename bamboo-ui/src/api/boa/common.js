import request from '@/utils/request'

// 查询简易人员列表
export function userSmallList() {
  return request({
    url: '/boa/common/userSmall',
    method: 'get'
  })
}
