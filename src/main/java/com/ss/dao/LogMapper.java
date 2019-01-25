package com.ss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ss.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogMapper extends BaseMapper<Log> {


    /*分页查询日志总数*/
    Integer selectLogNum();
    /*添加日志*/
    Integer getInsertLog();
    /*查询日志所有内容*/
    List<Log> getSelectLog(@Param("currentPage") Integer currentPage, @Param("currentPageSize") Integer currentPageSize);
    /*根据条件查询日志*/
    List<Log> getAsyncSelectLog(@Param("logType") String logType, @Param("uId") String uId, @Param("nick") String nick, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("currentPage") Integer currentPage, @Param("currentPageSize") Integer currentPageSize);

}
