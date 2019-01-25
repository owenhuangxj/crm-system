package com.ss.service;

import com.ss.entity.Search;
import com.ss.entity.Student;
import com.ss.entity.TrackInfo;
import com.ss.entity.User;
import com.ss.util.ChartData;

import java.util.List;

public interface MonitorService {
    /**
     * 查询公司所有员工
     * @param currentPage
     * @param currentPageSize
     * @return
     */
    List<User> selectAllWorker(Integer currentPage, Integer currentPageSize);

    /**
     * 查询公司总人数
     * @return
     */
    Integer selectWorkerNum();

    /**
     * 查询员工下面跟踪的学员
     * @param userId
     * @param currentPage
     * @param currentPageSize
     * @return
     */
    List<Student> selectWorkerOfStu(Integer userId, Integer currentPage, Integer currentPageSize);

    /**
     * 查询员工业务的完成情况
     * @param userId
     * @return
     */
    ChartData selectWorkerOfBusiness(Integer userId);

    /**
     * 查询某个学员的跟踪记录信息
     * @param stuNumber
     * @return
     */
    List<TrackInfo> selectWorkerOfStuTrak(String stuNumber);

    /**
     * 查询某个员工名下的学员数量
     * @param userId
     * @return
     */
    Integer selectWorkerOfStuNum(Integer userId);

    /**
     * 多条件查询某个员工
     * @param search
     * @return
     */
    User selectSomeWorker(Search search);
}
