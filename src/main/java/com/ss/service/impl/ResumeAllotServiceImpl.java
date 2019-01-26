package com.ss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ss.constant.ResumeTemplate;
import com.ss.dao.ResumeAllotMapper;
import com.ss.dao.SysUserMapper;
import com.ss.entity.Student;
import com.ss.entity.SysUser;
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
        IPage<Student> data=null;
        switch (columnIndex){
        case 0:
            data=ram.selectPage(page,new QueryWrapper<Student>().eq("stu_status","0").orderByAsc("stu_create_time"));
            break;
        case 1:
            data=ram.selectPage(page, new QueryWrapper<Student>().like("stu_name", value).eq("stu_status","0"));
            break;
        case 2:
            data=ram.selectPage(page, new QueryWrapper<Student>().like("stu_phone_num", value).eq("stu_status","0"));
            break;
    }


    return data;
}
    @Override

    /*map ,为前端传来的数据。way 是分配方式，0 是固定分配，1是随机分配 time -1 表示立刻分配*/
public Integer allotResume(Integer key,Integer[]value,Integer way,String time) {
        Map<Integer, Integer> map = new HashMap();
        if (way == 0) {
            /*固定分配*/
            for (int i = 0; i < value.length; i++) {
                map.put(value[i], key);
            }
        }else if(way==1){
            Integer total=key*(value.length);
            List<Student> records =getOrginStudent(1L, total.longValue(), null, 0).getRecords();
            for(int i=0;i<value.length;i++){
                for(int j=0;j<key;j++){
                    map.put( records.get(value.length + j).getStuId().intValue(),value[i]);
                }
            }
        }
        Integer rows = ram.updateBatch(map);



        return rows;
    }

    @Override
public List<SysUser> getAllUser(){

    Page<SysUser> sysUserPage = new Page<>();
    List<SysUser> sysUsers = sum.selectUserIncludeRoles(sysUserPage, null);

        SysUser user=null;
    List<SysUser> data=new ArrayList<>();
    for(int i=0;i<sysUsers.size();i++){
        user =new SysUser();
        user.setUid(sysUsers.get(i).getUid());
        user.setNick(sysUsers.get(i).getNick());
        data.add(user);
    }
    return  data;
}


public Integer deleteByStuId(Integer[] stuIdList){

   List students=Arrays.asList(stuIdList);

    return    ram.deleteBatchIds(students);
}
/*

    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                System.out.println("Hello !!!");
            }
        };

        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod =  5*1000;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay,
                intevalPeriod);
    } // end of main

*/



}
