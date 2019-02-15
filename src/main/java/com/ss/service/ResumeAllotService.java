package com.ss.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ss.entity.Student;
import com.ss.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @Author: 王顺坤
 * @Date: 2019/1/18 16:37
 * @Description:
 */
public interface ResumeAllotService {
    Integer importResumeFromExcele(String src, String TemplateId);
    /*多条件分页查询*/
    IPage<Student> getOrginStudent(Long current, Long size, String value, Integer columnIndex);

    Integer allotResume(String[] key, String[] value, Integer way, Integer[] time);

    List<SysUser>getAllUser();

    Integer deleteByStuId(Integer[] stuIdList);
}


