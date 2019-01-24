/**
 * 跟员工工作进度相关的接口
 */
import request from '@/utils/request'

export default {
//查询公司所有的员工信息
  getAllWorkerData(cur_page,cur_page_size) {
    return request({
      url: '/selectAllWorker',
      method: 'get',
      params:{cur_page,cur_page_size},
    })
  },
  //查询公司某个员工的信息
  getSomeWorkerData(data) {
    return request({
      url: '/selectSomeWorkerData',
      method: 'post',
      data,
    })
  },
  //查询公司员工数量
  getAllNum(){
    return request({
      url:'/selectWorkerNum',
      method:'get',
    })
  },
  //查询某位员工的所有学员
  getAllWorkerOfStuData(userId,cur_page,cur_page_size) {
    return request({
      url: '/selectWorkerOfStu',
      method: 'get',
      params:{userId,cur_page,cur_page_size},
    })
  },
  //查询某位员工的所有学员的数量
  getAllStuNum(userId) {
    return request({
      url: '/selectWorkerOfStuNum',
      method: 'get',
      params:{userId},
    })
  },
  //查询某位员工本月和本周的工作量
  getWorkerOfSchedule(userId){
    return request({
      url:'/selectWorkerOfBusiness',
      method:'get',
      params:{userId},
    })
  },
  //查询某个学员的跟踪记录
  getStuOfTrace(stuNumber){
    return({
      url:'/selectWorkerOfStuTrak',
      method:'get',
      params:{stuNumber}
    })
  }
}
