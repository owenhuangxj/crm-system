package com.ss.constant;

import java.util.*;

/**
 * @Author: 王顺坤
 * @Date: 2019/1/11 10:24
 * @Description:
 */
public class ResumeTemplate {
    public  static List<String> CONSUME_NEEDED_COLUMN=new ArrayList<>( Arrays.asList("姓名","性别","手机","出生日期","家庭地址","职能/职位","自我评价","工作经验","项目经验","培训经历"));
    public  static Map<String,List<Integer>> CONSUME_ROW_INDEX=new HashMap<>();

    /*public  static List<Integer> CONSUME_51JOB_ROW_INDEX=new ArrayList<>(Arrays.asList(9,10,11,13));*/

    /*mysql 安全路径的字段*/

    public  static String MYSQL_SECURE_FILE_PATH="C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/";

    static {

        CONSUME_ROW_INDEX.put("51JOB",new ArrayList<>(Arrays.asList(9,10,11,13,22,35,39,41,43,52)));


    }



}
