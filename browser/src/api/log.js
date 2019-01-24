/**
 * 日志接口
 */
import request from '@/utils/request'

export  default {
  /*查询日志的总数*/
  logNumAll(){
    return request({
      url: '/logNum',
      method: 'get',
    })
  },

  /*查询所有日志*/
  selectLog(cur_page,cur_page_size){
    return request({
      url: "/selectLog?currentPage="+cur_page+"&&currentPageSize=" + cur_page_size,
      method: 'get',
      /*param:{
        currentPage:this.cur_page,
        currentPageSize:this.cur_page_size
      }*/
    })
  },

  /*按条件查询日志*/
  asyncSelectLog(){
    return request({
      url: '/asyncSelectLog',
      method: 'get',
    })
  }
}
