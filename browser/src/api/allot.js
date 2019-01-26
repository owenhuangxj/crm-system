/**
 * “角色管理”相关接口
 */
import request from '@/utils/request'

export  default {

  /**获取所有的员工*/
  getAllUsers() {
    return request({
      url: '/allot/getAllUsers',
      method: 'post',
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
getUnAllotResume(data)
{
  console.log(data.get("column"));
  console.log(data.get("value"));
  console.log(data.get("size"));
  console.log(data.get("current"));
  return request({
    url: '/allot/getUnAllotResume',
    method: 'post',
    data: data
  })
},
  /*分发简历*/
allotResume(data){
  return request({
    url: '/allot/allotResume',
    method: 'post',
    data:data,
  })
},
  delByStuId(data){
    return request({
      url: '/allot/delByStuId',
      method: 'post',
      data:data,
    })
  }

}

