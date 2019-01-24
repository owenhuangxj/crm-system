package com.ss.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TargetMapper {
    /*月上门量*/
    Integer targetGetMonthType();
    /*周上门量*/
    Integer targetGetWeekType();
    /*月关单量*/
    Integer getTargetMonth();
    /*周关单量*/
    Integer getTargetWeek();
}
