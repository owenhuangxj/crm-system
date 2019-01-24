package com.ss.controller;

import com.ss.entity.Log;
import com.ss.service.LogService;
import com.ss.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/")
public class LogController {
    @Autowired
    private LogService ls;
    /*测试*//*
    @GetMapping("/log_test")
    public String testVueModule() {
        return "lzn_test";
    }

    *//*进入操作日志*//*
    @RequestMapping("/typeLog")
    public String typeLog() {
        return "typeLog";
    }

    *//*进入日志数据*//*
    @RequestMapping("/myLog")
    public String myLog() {
        return "myLog";
    }
*/
    /*添加日志*/
    @GetMapping("/insertLog")
    @ResponseBody
    public Json fingInsert(){
        Integer insertLog = ls.getInsertLog();
        Json json = new Json("添加日志",true,200,"insertLog",insertLog);
        return json;
    }

    /*查询日志所有内容*/
/*    @SystemControllerLog(actionType="用户操作", descrption="用户登录进入了界面之中")*/
    @GetMapping("/selectLog")
    @ResponseBody
    public Json findSelectAll(@PathParam("currentPage") Integer currentPage, @PathParam("currentPageSize") Integer currentPageSize){
        List<Log> log = ls.getSelectLog(currentPage, currentPageSize);
        Json json = new Json("查询日志内容",true,200,"selectLog",log);
        return json;
    }

    /*根据条件查找数据*/
    @GetMapping("/asyncSelectLog")
    @ResponseBody
    public Json findSelectAsyncLog(String logType,String uId,String nick,String startTime,String endTime,@PathParam("currentPage") Integer currentPage, @PathParam("currentPageSize") Integer currentPageSize){
        List asyncSelectLog = ls.getAsyncSelectLog(logType, uId, nick, startTime, endTime, currentPage, currentPageSize);
        Json json = new Json("根据条件查询数据",true,200,"asyncSelectLog",asyncSelectLog);
        return json;
    }

    /*获取日志的所有条数*/
    @GetMapping("/logNum")
    @ResponseBody
    public Json findSelectLogNum(){
        Integer integer = ls.selectLogNum();
        Json json = new Json("获取日志的所有条数",true,200,"logNum",integer);
        return json;
    }
}
