/**
 * “角色管理”相关接口
 */
import request from '@/utils/request'

export  default {

  /**获取所有的员工*/
  getAllUsers() {
    return request({
      url: '/allot/getAllUsers',
      method: 'get',
    });
  },
  /**从excle中导入简历数据*/
  importResumeFromExcel() {
    return request({
      url: '/allot/importResumeFromExcel',
      method: 'post',
    })
  },
/*查询所有简历*/
getUnAllotResume()
{
  return request({
    url: '/allot/getUnAllotResume',
    method: 'get',
  })
},
  /*分发简历*/
allotResume(){
  return request({
    url: '/allot/allotResume',
    method: 'get',
  })

}

}

