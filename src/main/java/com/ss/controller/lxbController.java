package com.ss.controller;

import com.ss.entity.*;
import com.ss.service.MonitorService;
import com.ss.util.ChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class lxbController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/workMonitor")
    public String testlxb(){
        return  "workMonitor";
    }

    /**
     * 分页查询公司所有员工信息
     * @param currentPage
     * @param currentPageSize
     * @return
     */
    @GetMapping("/selectAllWorker/{currentPage}/{currentPageSize}")
    public @ResponseBody
    List<User> selectUser(@PathVariable("currentPage") Integer currentPage, @PathVariable("currentPageSize") Integer currentPageSize) {
        List<User> users = monitorService.selectAllWorker(currentPage, currentPageSize);
        for (User user:users
             ) {
            System.out.println("users:"+user);
        }
        return users;
    }
    /**
     * 查询公司员工的总人数
     * @return
     */
    @GetMapping("/selectWorkerNum")
    public @ResponseBody
    Integer selectWorkerNum() {
        return monitorService.selectWorkerNum();
    }
    /**
     *查询某位员工的所有学员
     * @param userId
     * @param currentPage
     * @param currentPageSize
     * @return
     */
    @GetMapping("/selectWorkerOfStu/{userId}/{currentPage}/{currentPageSize}")
    public @ResponseBody
    List<Student> selectWorkerOfStu(@PathVariable("userId") Integer userId, @PathVariable("currentPage") Integer currentPage, @PathVariable("currentPageSize") Integer currentPageSize) {
        List<Student> students = monitorService.selectWorkerOfStu(userId,currentPage, currentPageSize);
        System.out.println("我获得的信息"+userId+currentPage+currentPageSize);
        return students;
    }

    /**
     * 查询某位员工本月和本周的工作量
     * @param userId
     * @return
     */
    @GetMapping("/selectWorkerOfBusiness/{userId}")
    public @ResponseBody
    ChartData selectWorkerOfBusiness(@PathVariable("userId") Integer userId) {
        ChartData chartData = monitorService.selectWorkerOfBusiness(userId);
//        System.out.println("我获得的数据"+chartData.toString());
        return chartData;
    }

    /**
     *查询某个学员的跟踪记录
     * @param stuNumber 20191121870
     * @return
     */
    @GetMapping("/selectWorkerOfStuTrak/{stuNumber}")
    public @ResponseBody
    List<TrackInfo> selectWorkerOfStuTrak(@PathVariable("stuNumber") String stuNumber) {
        List<TrackInfo> TrackInfo = monitorService.selectWorkerOfStuTrak(stuNumber);
//        System.out.println("我获得的数据"+chartData.toString());
        return TrackInfo;
    }
    /**
     * 查询公司某个员工名下的学员的总人数
     * @return
     */
    @GetMapping("/selectWorkerOfStuNum/{userId}")
    public @ResponseBody
    Integer selectWorkerOfStuNum(@PathVariable("userId") Integer userId) {
        return monitorService.selectWorkerOfStuNum(userId);
    }
    @PostMapping("/searchUser")
    public  @ResponseBody User searchWorker(@RequestBody Search search){
        System.out.println("我从前端获得的东西是"+ search);
        return  null;
    }
}
