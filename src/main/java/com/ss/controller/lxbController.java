package com.ss.controller;

import com.alibaba.fastjson.JSON;
import com.ss.entity.Search;
import com.ss.entity.Student;
import com.ss.entity.TrackInfo;
import com.ss.entity.User;
import com.ss.service.MonitorService;
import com.ss.util.ChartData;
import com.ss.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
     * @param cur_page
     * @param cur_page_size
     * @return
     */
    @GetMapping("/selectAllWorker")
    public @ResponseBody
    Json selectUser(@PathParam("cur_page") Integer cur_page, @PathParam("cur_page_size") Integer cur_page_size) {
        List<User> users = monitorService.selectAllWorker(cur_page, cur_page_size);
        Json json = new Json("查询所有用户",true,200,"user",users);
        for (User user:users
             ) {
            System.out.println("users:"+user);
        }
        return json;
    }
    /**
     * 查询公司员工的总人数
     * @return
     */
    @GetMapping("/selectWorkerNum")
    public @ResponseBody
    Json selectWorkerNum() {
        Integer integer = monitorService.selectWorkerNum();

        System.out.println("我获得的用户数量是"+integer);
        Json json = new Json("查询用户数量",true,200,"userNum",integer);
        return json;
    }
    /**
     *查询某位员工的所有学员
     * @param userId
     * @param cur_page
     * @param cur_page_size
     * @return
     */
    @GetMapping("/selectWorkerOfStu")
    public @ResponseBody
    Json selectWorkerOfStu(@PathParam("userId") Integer userId, @PathParam("cur_page") Integer cur_page, @PathParam("cur_page_size") Integer cur_page_size) {
        List<Student> students = monitorService.selectWorkerOfStu(userId,cur_page, cur_page_size);
        System.out.println("我获得的信息"+userId+cur_page+cur_page_size);
        Json json = new Json("查询学生信息",true,200,"student",students);
        return json;
    }

    /**
     * 查询某位员工本月和本周的工作量
     * @param userId
     * @return
     */
    @GetMapping("/selectWorkerOfBusiness")
    public @ResponseBody
    Json selectWorkerOfBusiness(@PathParam("userId") Integer userId) {
        ChartData chartData = monitorService.selectWorkerOfBusiness(userId);
        Json json = new Json("查询员工的工作进度",true,200,"user",chartData);
//        System.out.println("我获得的数据"+chartData.toString());
        return json;
    }

    /**
     *查询某个学员的跟踪记录
     * @param stuNumber 20191121870
     * @return
     */
    @GetMapping("/selectWorkerOfStuTrak")
    public @ResponseBody
    Json selectWorkerOfStuTrak(@PathParam("stuNumber") String stuNumber) {
        List<TrackInfo> TrackInfo = monitorService.selectWorkerOfStuTrak(stuNumber);
        Json json = new Json("查询学生的跟踪记录",true,200,"Track",TrackInfo);
//        System.out.println("我获得的数据"+chartData.toString());
        return json;
    }
    /**
     * 查询公司某个员工名下的学员的总人数
     * @return
     */
    @GetMapping("/selectWorkerOfStuNum")
    public @ResponseBody
    Json selectWorkerOfStuNum(@PathParam("userId") Integer userId) {
        Integer integer = monitorService.selectWorkerOfStuNum(userId);
        Json json = new Json("查询学生的数量",true,200,"student",integer);

        return json;
    }

    @PostMapping("/selectSomeWorkerData")
    public  @ResponseBody
    Json searchWorker(@RequestBody String body){
        System.out.println("我从前端获得的东西是"+ JSON.parseObject(body));
        Json json = new Json("查询某个员工的信息",true,200,"worker",null);
        Search search = JSON.parseObject(body, Search.class);
        monitorService.selectSomeWorker(search);
        System.out.println("search:"+search);
        return  json;
    }
}
