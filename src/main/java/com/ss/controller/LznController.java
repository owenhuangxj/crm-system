package com.ss.controller;

import com.ss.entity.Log;
import com.ss.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller

public class LznController {
    @Autowired
    private LogService ls;
    /*测试*/
    @GetMapping("/log_test")
    public String testVueModule() {
        return "lzn_test";
    }

    /*进入操作日志*/
    @RequestMapping("/typeLog")
    public String typeLog() {
        return "typeLog";
    }

    /*进入日志数据*/
    @RequestMapping("/myLog")
    public String myLog() {
        return "myLog";
    }

    /*添加日志*/
    @RequestMapping("/insertLog")
    @ResponseBody
    public Integer fingInsert(Log log){
        return ls.getInsertLog(log);
    }

    /*查询日志所有内容*/
/*    @SystemControllerLog(actionType="用户操作", descrption="用户登录进入了界面之中")*/
    @RequestMapping(value = "selectLog/{currentPage}/{currentPageSize}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Log> findSelectAll(@PathVariable("currentPage") Integer currentPage, @PathVariable("currentPageSize") Integer currentPageSize){
        List<Log> log = ls.getSelectLog(currentPage, currentPageSize);
        for(Log logs : log){
            System.out.println("log : " + log);
        }
        return log;
    }

    /*根据条件查找数据*/
    @RequestMapping("/asyncSelectLog/{currentPage}/{currentPageSize}")
    @ResponseBody
    public List<Log> findSelectAsyncLog(String logType,String email,String userName,String startTime,String endTime,@PathVariable("currentPage") Integer currentPage, @PathVariable("currentPageSize") Integer currentPageSize){
        return ls.getAsyncSelectLog(logType,email,userName,startTime,endTime,currentPage,currentPageSize);
    }

    /*获取日志的所有条数*/
    @RequestMapping("/logNum")
    @ResponseBody
    public Integer findSelectLogNum(){
        return ls.selectLogNum();
    }
}
