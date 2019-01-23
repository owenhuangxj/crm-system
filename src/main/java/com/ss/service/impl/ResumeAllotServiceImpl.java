package com.ss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ss.constant.ResumeTemplate;
import com.ss.dao.ResumeAllotMapper;
import com.ss.dao.SysUserMapper;
import com.ss.entity.Student;
import com.ss.entity.SysPerm;
import com.ss.entity.SysUser;
import com.ss.entity.User;
import com.ss.service.ResumeAllotService;
import com.ss.util.ExcelToCsv;
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
@Autowired
    private SysUserMapper sum;

@Override
public Integer importResumeFromExcele(String src,String TemplateId){

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


    return num;
}
    @Override
public IPage<Student>  getOrginStudent(Long current, Long size, String value, Integer columnIndex){

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

    return data;
}

public Integer allotResume(Map<Integer,Integer> source){
    Integer rows = ram.updateBatch(source);

    return   rows;
}

public List<SysUser> getAllUser(){


    Page<SysUser> sysUserPage = new Page<>();
    List<SysUser> sysUsers = sum.selectUserIncludeRoles(sysUserPage, null);

        SysUser user=null;
    List<SysUser> data=new ArrayList<>();
    for(int i=0;i<sysUsers.size();i++){
        user =new SysUser();
        user.setUid(sysUsers.get(i).getUid());
        user.setUname(sysUsers.get(i).getUname());
        data.add(user);
    }
    return  data;
}




}
