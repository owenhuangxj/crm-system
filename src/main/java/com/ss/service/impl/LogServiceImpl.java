package com.ss.service.impl;


import com.ss.dao.LogMapper;
import com.ss.entity.Log;
import com.ss.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper lm;

    /*分页查询日志总数*/
    @Override
    public Integer selectLogNum() {
        return lm.selectLogNum();
    }

    /*添加日志*/
    @Override
    public Integer getInsertLog() {
        String date = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss").format(new Date());
        System.out.println("创建日志的时间:" + date);
        return lm.getInsertLog();
    }

    /*查询日志所有内容*/
    @Override
    public List<Log> getSelectLog(Integer currentPage, Integer currentPageSize) {
        return lm.getSelectLog((currentPage-1)*currentPageSize,currentPageSize);
    }

    /*按要求查找日志内容*/
    @Override
    public List<Log> getAsyncSelectLog(String logType,String uId,String nick,String startTime, String endTime,Integer currentPage, Integer currentPageSize) {
        return lm.getAsyncSelectLog(logType,uId,nick,startTime,endTime,(currentPage-1)*currentPageSize,currentPageSize);
    }


}
