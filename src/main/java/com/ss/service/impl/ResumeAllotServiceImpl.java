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
import org.apache.poi.util.SystemOutLogger;
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
        /*0:无条件按时间排序，1：姓名;2:电话*/
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
public Integer allotResume(String[] key,String[]value,Integer way,Integer[] time) {

    Map<String,String> map=null;
    switch(way){
        case 0:
            map=fixedAllot(key,value);
            break;
        case 1:
            Long num=Long.valueOf(value[0])*key.length;
            final List<Student> resumeList = getOrginStudent(1L,num, null, 0).getRecords();
            map=randomAllot(key,resumeList);
            break;
    }
        TimedTask(map,time);
        return 1;
    }
    /*固定分配给单人的任务*/
    private Map<String,String> fixedAllot(String [] employee, String [] resumeId){
        Map<String,String> allotContext=new HashMap<>();
        for(int j=0;j<resumeId.length;j++){
            allotContext.put(resumeId[j],employee[0]);
        }
        return allotContext;
    }
/*随机分配给某人几个数据*/
    private  Map<String,String> randomAllot(String [] employee,List<Student> resumeList){
        Map<String,String> allotContext=new HashMap<>();
        loop: while(true){
            for(int i=0;i<employee.length;i++){
                allotContext.put(resumeList.get(0).getStuNumber(),employee[i]);
                resumeList.remove(0);
                if(resumeList.size()==0){
                    break loop;
                }
            }
        }

        return allotContext;
    }


    public void TimedTask(Map<String, String> allotContext,Integer []time) {
        Date taskTime = getTime(time);
        System.out.println("指定时间time=" + taskTime);
        if(taskTime==null){
            System.out.println("立即执行");
            new Thread(new AllotResumeThread(allotContext)).start();
        }else{
            Timer timer = new Timer();
            System.out.println("定时执行");
            timer.schedule(new AllotResumeThread(allotContext), taskTime);
        }
    }

    class AllotResumeThread extends TimerTask {
        Map<String, String> allotContext;
        public AllotResumeThread(Map<String, String> allotContext){
            this.allotContext=allotContext;
        }
        @Override
        public void run() {
            ram.updateBatch(allotContext);
            System.out.println(new Date().getTime());
        }
    }

    private Date getTime(Integer[] time) {
        if(time[0]==-1){
            return null;
        }else{
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, time[3]);
            calendar.set(Calendar.MINUTE, time[4]);
            calendar.set(Calendar.SECOND, time[5]);
            return calendar.getTime();
        }
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

}









