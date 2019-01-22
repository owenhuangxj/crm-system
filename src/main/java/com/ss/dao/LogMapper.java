package com.ss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ss.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogMapper extends BaseMapper<Log> {


    /*分页查询日志总数*/
    Integer selectLogNum();
    /*添加日志*/
    Integer getInsertLog(Log log);
    /*查询日志所有内容*/
    List<Log> getSelectLog(@Param("start") Integer start, @Param("currentPageSize") Integer currentPageSize);
    /*根据条件查询日志*/
    List<Log> getAsyncSelectLog(@Param("logType") String logType, @Param("email") String email, @Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("currentPageSize") Integer currentPageSize);

}
