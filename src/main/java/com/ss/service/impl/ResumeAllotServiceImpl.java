package com.ss.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ss.constant.ResumeTemplate;
import com.ss.dao.ResumeAllotMapper;
import com.ss.entity.Selector;
import com.ss.entity.Student;
import com.ss.entity.User;
import com.ss.service.ResumeAllotService;
import com.ss.util.ExcelToCsv;
import com.ss.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: 王顺坤
 * @Date: 2019/1/17 15:00
 * @Description:
 */
@Service
public class ResumeAllotServiceImpl implements ResumeAllotService {

@Autowired
    private ResumeAllotMapper ram;


@Override
public String importResumeFromExcele(String src,String TemplateId){

    ExcelToCsv etc = new ExcelToCsv();
    etc.setSrc(src);
    etc.setTargetFolder(ResumeTemplate.MYSQL_SECURE_FILE_PATH,null);
    int num = etc.getWorkbook().getNumberOfSheets();
    final List<Integer> index = ResumeTemplate.CONSUME_ROW_INDEX.get(TemplateId);
    etc.setSexIndex(1);
    etc.setColumnIndexList(2,2);
    etc.setAppendInfo(new ArrayList<>(Arrays.asList("51Job","简历导入")));
    etc.consolidation(1, num, index);
    System.out.println(etc.getTargetFile());
    ram.ImportResume(etc.getTargetFile());
    JsonData jsonData=new JsonData("importResumeFromExcele","插入数据","插入"+(+num-1)+"条简历",true);

    return JSON.toJSONString(jsonData);
}
    @Override
public String getOrginStudent(Long current, Long size, String value, Integer columnIndex){

    IPage<Student> page=new Page<>(current,size);
    String column=null;
    switch (columnIndex){
        case 0:
            column="stu_name";
        break;
        case 1:
            column="stu_phone_num";
            break;
    }
    IPage<Student> data = ram.selectPage(page, new QueryWrapper<Student>().like(column, value));

    JsonData jsonData=new JsonData("getOrginStudent",data,"查询成功",true);

    return JSON.toJSONString(jsonData);
}

public String allotResume(Map<Integer,Integer> source){
    Integer rows = ram.updateBatch(source);
    JsonData data = new JsonData("allotResume",rows,"分配了"+rows+"份简历",true);
    return    JSON.toJSONString(data);
}

public List<Selector> getAllUser(){
    final User user = new User();
    final List<User> users = user.selectAll();
    List<Selector> selector=new ArrayList<>();
    for(int i=0;i<users.size();i++){
        selector.add(new Selector(users.get(i).getUserId(),users.get(i).getUserName(),users.get(i)));
    }
    return  selector;
}




}
