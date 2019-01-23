package com.ss.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ss.vo.AuthVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;
@Data
@TableName("sys_user")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SysUser extends Model<SysUser> {

    @TableId(type = IdType.ID_WORKER_STR)
    private String uid;     // 用户id
    private String uname;   // 登录名，不可改
    private String nick;    // 用户昵称，可改
    private String pwd;     // 已加密的登录密码
    private String salt;    // 加密盐值
    private Boolean locked;   // 是否锁定
    private Date created;   // 创建时间
    private Date updated;   // 修改时间
    private String email;

    @TableField(exist = false)
    private List<SysRole> roleList = new ArrayList<>();    //用户所有角色值，在管理后台显示用户的角色
    @TableField(exist = false)
    private Set<AuthVo> roles = new HashSet<>();    //用户所有角色值，用于shiro做角色权限的判断
    @TableField(exist = false)
    private Set<AuthVo> perms = new HashSet<>();    //用户所有权限值，用于shiro做资源权限的判断

    @Override
    protected Serializable pkVal() {
        return uid;
    }

}
