package com.ss.controller;

import com.alibaba.fastjson.JSON;
import com.ss.entity.StudentInfo;
import com.ss.service.StuInfoService;
import com.ss.vo.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/stu")
public class StuController {

    private static final Logger log = LoggerFactory.getLogger(SysPermController.class);

    @Autowired
    private StuInfoService ss;

    /**
     * 手动添加学生简历
     *
     * @param body 学生数据
     * @return 是否添加成功
     */
    @ResponseBody
    @PostMapping(value = "/addStu")
    public Json addStuResume(@RequestBody String body) {
        String oper = "add student info";
        log.info("{}, body: {}", oper, body);
        StudentInfo stu = JSON.parseObject(body, StudentInfo.class);
        return Json.result(oper, ss.addStuResume(stu));
    }

    /**
     * 通过学生编号查询学生信息
     *
     * @param stuNumber 学生编号
     * @return 学生信息
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getStu")
    public Json getStuInfo(@RequestParam("stuNumber") String stuNumber) {
        String oper = "get student basicInfo";
        StudentInfo stuInfo = ss.getStuInfo(stuNumber);
        return Json.succ(oper).data("info", stuInfo);
    }

    /**
     * 修改学生信息
     *
     * @param body 学生数据
     * @return 是否修改成功
     */
    @ResponseBody
    @PostMapping(value = "/updateStu")
    public Json updateStuInfo(@RequestBody String body) {
        String oper = "update student info";
        log.info("{}, body: {}", oper, body);
        StudentInfo infos = JSON.parseObject(body, StudentInfo.class);
        Integer integer = ss.updateStuInfo(infos);
        return Json.result(oper, integer > 0);
    }


}
