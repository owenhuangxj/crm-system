package com.ss.controller;

import com.alibaba.fastjson.JSON;
import com.ss.entity.StudentInfo;
import com.ss.service.StuInfoService;
import com.ss.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/stu")
public class StuController {

    @Autowired
    private StuInfoService ss;

    /**
     * 手动添加学生简历
     *
     * @param stu 学生数据
     * @return 是否添加成功
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/addStu")
    public Boolean addStuResume(@RequestBody StudentInfo stu) {
        System.out.println(stu);
        return ss.addStuResume(stu);
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
        return Json.succ(oper).data("info",stuInfo);
    }

    /**
     * 修改学生信息
     *
     * @param stu 学生数据
     * @return 是否修改成功
     */
    @PostMapping(value = "/updateStu")
    public Boolean updateStuInfo(@RequestBody StudentInfo stu) {
        System.out.println("-----------修改数据---------");
        System.out.println("stu" + stu);
        return ss.updateStuInfo(stu) > 0;
    }


}
