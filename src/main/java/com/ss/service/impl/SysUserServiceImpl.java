package com.ss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ss.dao.SysUserMapper;
import com.ss.entity.SysUser;
import com.ss.service.SysUserService;
import com.ss.util.CreateUId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public Page<SysUser> queryUserIncludeRoles(Page page, String nick) {
        return page.setRecords(baseMapper.selectUserIncludeRoles(page,nick));
    }

    @Override
    public SysUser getEmail(String email) {
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .eq("email", email)
        );
        return sysUser;
    }

    @Override
    public SysUser getUserName(String uname) {
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .eq("uname", uname)
        );
        return sysUser;
    }

    @Override
    public int insertUser(String email, String uname, String pwd) {
        SysUser sysUser = new SysUser();
        sysUser.setUid(CreateUId.getOrderIdByUUId());
        sysUser.setEmail(email);
        sysUser.setUname(uname);
        sysUser.setPwd(pwd);
        sysUser.setCreated(new Date());
        int user = sysUserMapper.insert(sysUser);
        return user;
    }

}
