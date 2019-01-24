/**
 * “个人消息”相关接口
 */
import request from '@/utils/request'

export default {

  initMsgList(data) {
    return request({
      url: '/msgData/',
      method: 'post',
      data
    })
  },

  passMsg(data){
    return request({
      url: '/passMsg/',
      method: 'post',
      data
    })
  },

  changeStatus(data){
    return request({
      url: 'changeMsgStatus',
      method: 'post',
      data
    })
  },

  changeAllStatus(data) {
    return request({
      url: 'changeAllStatus',
      method: 'post',
      data
    })
  }


}

