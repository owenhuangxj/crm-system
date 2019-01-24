/**
 * 学生接口
 **/
import request from '@/utils/request'

export default {
  /**
   * 获取学生的基本信息
   */
  queryStuBasicInfo(stuNum){
    return request({
      url:"stu/getStu?stuNumber="+stuNum,
      method:"get",
    })
  },
  /**
   * 更新学生信息
   */
  updateStuBasicInfo(data){
    return request({
      url:'stu/updateStu',
      method:'post',
      headers:{"Content-Type":"application/json;charset=UTF-8"},
      data
    })
  },
  /**
   * 增加学生
   */
  addStuInfo(data){
    return request({
      url:'stu/addStu',
      method:'post',
      headers:{"Content-Type":"application/json;charset=UTF-8"},
      data
    })
  },
  /**
   * 获取跟踪信息
   */
  queryTrackInfo(stuNum){
    return request({
      url:'track/getTrack?stuNumber='+stuNum,
      method:'get',
    })
  },
  /**
   * 新增跟踪信息
   */
  addNewTrackInfo(data){
    return request({
      url:'track/addTrack',
      method:'post',
      headers:{"Content-Type":"application/json;charset=UTF-8"},
      data
    })
  }
}
