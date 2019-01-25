package com.ss.service;

import com.ss.entity.Log;

import java.util.List;

public interface LogService<PageData> {


    /*分页查询日志总数*/
    Integer selectLogNum();
    /*添加日志*/
    Integer getInsertLog();
    /*查询日志所有内容*/
    List<Log> getSelectLog(Integer currentPage, Integer currentPageSize);
    /*按要求查找日志内容*/
    List<Log> getAsyncSelectLog(String logType, String uId, String nick, String startTime, String endTime, Integer currentPage, Integer currentPageSize);

}
