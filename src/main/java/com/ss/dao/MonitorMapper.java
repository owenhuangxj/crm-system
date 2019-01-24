package com.ss.dao;

import com.ss.entity.Student;
import com.ss.entity.TrackInfo;
import com.ss.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@CacheNamespace()
public interface MonitorMapper {
    //分页查询员工信息
    List<User> selectAllWorker(@Param("start") Integer start, @Param("currentPageSize") Integer currentPageSize);
    //查询公司员工的总人数
    Integer selectWorkerNum();
    //查询员工名下的跟踪的学员
    List<Student> selectWorkerOfStu(@Param("userId") Integer userId, @Param("start") Integer start, @Param("currentPageSize") Integer currentPageSize);
    //查询周完成的工作量
    Integer selectAlreadZhouWork(@Param("userId") Integer userId);
    //查询周未完成的工作量
    Integer selectNoZhouWork(@Param("userId") Integer userId);
    //查询月完成的工作量
    Integer selectAlreadYueWork(@Param("userId") Integer userId);
    //查询余额未完成的工作量
    Integer selectNoYueWork(@Param("userId") Integer userId);
    //查询某个学员的跟踪记录
    List<TrackInfo> selectWorkerOfStuTrak(@Param("stuNumber") String stuNumber);
    //查询某个员工名下的学员数量
    Integer selectWorkerOfStuNum(@Param("userId") Integer userId);
    //多条件查询某个员工的信息
    User selectSomeWorker(User user);
}
