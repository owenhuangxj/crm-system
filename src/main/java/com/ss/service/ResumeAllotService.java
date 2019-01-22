package com.ss.service;


import com.ss.entity.Selector;

import java.util.List;
import java.util.Map;

/**
 * @Author: 王顺坤
 * @Date: 2019/1/18 16:37
 * @Description:
 */
public interface ResumeAllotService {
    String importResumeFromExcele(String src, String TemplateId);
    /*多条件分页查询*/
     String getOrginStudent(Long current, Long size, String value, Integer columnIndex);
    String allotResume(Map<Integer, Integer> source);
    List<Selector> getAllUser();

}
