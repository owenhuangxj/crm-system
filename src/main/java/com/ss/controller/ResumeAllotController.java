package com.ss.controller;
/**
 * @Author: 王顺坤
 * @Date: 2019年1月11日
 * @Description:王顺坤的前端测试类
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ss.constant.ResumeTemplate;
import com.ss.entity.Student;
import com.ss.service.ResumeAllotService;
import com.ss.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("/allot")
public class ResumeAllotController {
    @Autowired
    private ResumeAllotService ras;

    @GetMapping("/test")
    public String index() {
        return "wsk-test";
    }

    @PostMapping("importResumeFromExcel")
    public @ResponseBody Json uploadAndWrite(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {

        String src = null;
        for (MultipartFile file : files) {
            try {
                String uploadDir = ResumeTemplate.MYSQL_SECURE_FILE_PATH;
                System.out.println(uploadDir);
                System.out.println(file.getOriginalFilename());
                file.transferTo(new File(uploadDir.concat(file.getOriginalFilename())));
                src = uploadDir.concat(file.getOriginalFilename());
                System.out.println(src);
                ras.importResumeFromExcele(src, "51JOB");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Integer rows = ras.importResumeFromExcele(src, null);
        return  Json.succ("import").data("插入"+rows+"条数据",rows);
    }
/*, produces = "application/json;charset=UTF-8"*/
    @RequestMapping(value ="/getUnAllotResume")
    public @ResponseBody Json getUnAllotResume(@RequestParam("current") Long current,@RequestParam("size") Long size ,@RequestParam("column")Integer column  ,@RequestParam("value")String value){
        System.out.println(current);
        IPage<Student> data = ras.getOrginStudent(current, size, value, column);
        return Json.succ("get data ").data("data",data);
    }


    @RequestMapping("/getAllUsers")
    public @ResponseBody Json getAllUsers() {

        return   Json.succ("get all users").data("user",ras.getAllUser());
    }
    @RequestMapping("/allotResume")
    public @ResponseBody
    Json AllotResume(@RequestParam("key")String[] key,@RequestParam("value")String value[],@RequestParam("allotTime")Integer []time,Integer way) {
        System.out.println(Arrays.deepToString(key)+" "+ Arrays.deepToString(value)+"  "+way+"   "+ Arrays.deepToString(time));
        Integer rows = ras.allotResume(key,value,way,time);
        return Json.succ("allot").data("allot",rows);
    }
    @RequestMapping("/delByStuId")
    public @ResponseBody Json delByStuId(@RequestParam("stuIds") Integer[]stuIdList) {

        return   Json.succ("get all users").data("del",ras.deleteByStuId(stuIdList));
    }

}
