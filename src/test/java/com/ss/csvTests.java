package com.ss;

import com.ss.constant.ResumeTemplate;
import com.ss.dao.ResumeAllotMapper;
import com.ss.entity.SysUser;
import com.ss.service.impl.ResumeAllotServiceImpl;
import com.ss.util.ExcelToCsv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class csvTests {

    @Autowired
    private ResumeAllotMapper ram;
    @Autowired
    private ResumeAllotServiceImpl rasi;
    @Test
    /*横向数据采集测试*/
    public void rowTest() {
        ExcelToCsv csvUtil = new ExcelToCsv();
        csvUtil.setSrc("C:/Users//Administrator/Desktop/爱你.xls");
        csvUtil.setTargetFolder("C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/","n8dsaS");
        int num = csvUtil.getWorkbook().getNumberOfSheets();
        final List<Integer> index = ResumeTemplate.CONSUME_ROW_INDEX.get("51JOB");
        csvUtil.setSexIndex(1);
        csvUtil.setColumnIndexList(2,2);
        csvUtil.setAppendInfo(new ArrayList<>(Arrays.asList("Boos直聘","简历导入")));
       csvUtil.consolidation(1, num, index);
        ram.ImportResume(csvUtil.getTargetFile());

        /*  csvUtil.close();*/
     //   System.out.println(Arrays.deepToString(neededRowIndex.toArray()));
    }
    @Test
    public void rowsIndexTest() {

        String str = rasi.getOrginStudent(1L, 10L, "赵", 0).toString();
        System.out.println(str);

    }  @Test
    public void hell() {
        List<SysUser> allUser = rasi.getAllUser();
        System.out.println(allUser.size());
    }
    @Test
    public void he1ll() {
       rasi.deleteByStuId(null);
    }


    @Test
    /*纵向数据采集测试*/
    /*废弃测试*/
    public void columnTest() {
        ExcelToCsv csvUtil = new ExcelToCsv();
        csvUtil.setSrc("C:/Users//Administrator/Desktop/爱你.xls");
        csvUtil.setTargetFolder("C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/","列采集");
        csvUtil.setAppendInfo(new ArrayList<>(Arrays.asList("51job","简历录入")));
/*        final List<Integer> index = ResumeTemplate.CONSUME_COLUMN_INDEX;*/
     /*   csvUtil.setColumnIndexList(index);*/
        csvUtil.setSexIndex(1);
        csvUtil.excelToCSV(2,0);
    }

    @Test
    public void or(){
        HashMap<String, String> map = new HashMap<>();
       /* map.put("40",2);
        map.put("42",45);
        map.put("42",2);*/
        ram.updateBatch(map);



    }

    @Test
    public void or1(){

   rasi.getAllUser();
        System.out.println();

    }

    @Test
    public void uselessTest(){
        ram.ImportResume("C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/test.csv");
    }

    @Test
    public void TimedTest(){



    }

}

