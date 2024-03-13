import request from '@/utils/request'
import {getToken} from "@/utils/auth";

// 查询会议室信息列表
export function listRooms(query) {
  return request({
    url: '/boa/rooms/list',
    method: 'get',
    params: query
  })
}

// 查询会议室信息详细
export function getRooms(id) {
  return request({
    url: '/boa/rooms/' + id,
    method: 'get'
  })
}

// 新增会议室信息
export function addRooms(data) {
  return request({
    url: '/boa/rooms',
    method: 'post',
    data: data
  })
}

// 修改会议室信息
export function updateRooms(data) {
  return request({
    url: '/boa/rooms',
    method: 'put',
    data: data
  })
}

// 删除会议室信息
export function delRooms(id) {
  return request({
    url: '/boa/rooms/' + id,
    method: 'delete'
  })
}


// 上传图片
export function uploadFile(data){
  return request({
    url: '/boa/rooms/uploadImg',
    headers:{
      'Authorization': 'Bearer ' + getToken(),
      'Content-Type': 'multipart/form-data'
    },
    method: "post"
  })
}

// 添加imgUrl
export function addImagUrl(data){
  return request({
    url: '/boa/rooms/imgUrl',
    method: 'post',
    data
  })
}
