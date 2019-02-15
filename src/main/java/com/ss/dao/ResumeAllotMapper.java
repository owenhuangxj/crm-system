package com.ss.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ss.entity.Student;
import com.ss.entity.SysUser;
import com.ss.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 王顺坤
 * @Date: 2019/1/17 10:41
 * @Description:
 */

public interface ResumeAllotMapper  extends BaseMapper<Student> {
        /*导入csv简历文件到t_student表*/
    Integer ImportResume(@Param("src") String src);
        /*多条件分页查询*/
    IPage<Student> selectPage(IPage<Student> page, @Param("ew") Wrapper<Student> queryWrapper);
        /*分配简历*/
     Integer updateBatch(@Param("map") Map<String, String> map);










}
