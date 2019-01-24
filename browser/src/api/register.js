/**
 * 注册相关接口
 */
import request from '@/utils/request'

export default {

  emailAction(email,num){
    return request({
      url:'findEmail',
      method: 'post',
      data:{
        email:email,
        num:num
      }
    })
  },

  nameAction(username){
    return request({
      url:'findName',
      method: 'post',
      data:{
        username:username
      }
    })
  },
  registerAction(email,username, password) {
    const data = {
      email:email,
      username: username,
      password: password
    }
    return request({
      url: 'addUser',
      method: 'post',
      data
    })
  },

}

