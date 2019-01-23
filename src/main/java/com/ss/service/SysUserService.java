package com.ss.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ss.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    Page<SysUser> queryUserIncludeRoles(Page page, String nick);

    /*注册*/
    //邮箱是否存在
    SysUser getEmail(String email);
    //用户名是否存在
    SysUser getUserName(String uname);
    /*注册添加到数据库*/
    int insertUser(String email, String uname, String pwd);


}
