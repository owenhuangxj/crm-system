/**
 * 学生接口
 **/
import request from '@/utils/request'

export default {
  /**
   * 获取学生的跟踪信息
   */
  queryTraceInfo(data){
    return request({
      url: "trace/getTraceList",
      method: "post",
      data
    })
  },

  /**
   * 调换咨询电话
   */
  exchangePhone(data){
    return request({
      url: "trace/updateNick",
      method: "post",
      data
    })
  },

  /**
   * 获取全部的咨询师
   */
  queryUserNames(){
    return request({
      url: "trace/getUserNames",
      method: "post"
    })
  },

  /**
   * 撤回简历
   */
  updateStuStatus(data){
    return request({
      url: "trace/updateStuStatus",
      method: "post",
      data
    })
  }
}
