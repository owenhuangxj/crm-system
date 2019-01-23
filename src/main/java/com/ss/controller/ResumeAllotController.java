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

    @RequestMapping("/getUnAllotResume")
    public @ResponseBody Json getUnAllotResume() {
        IPage<Student> data = ras.getOrginStudent(1L, 1L, "赵", 0);
        return Json.succ("get data ").data("data",data);
    }

    @RequestMapping("/getAllUsers")
    public @ResponseBody Json getAllUsers() {

        return   Json.succ("get all users").data("用户",ras.getAllUser());
    }
    @RequestMapping("/allotResume")
    public @ResponseBody
    Json AllotResume(@RequestParam("map") Map<Integer,Integer> map) {
        Integer rows = ras.allotResume(map);
        return Json.succ("allot").data("分发了"+rows+"份简历",rows);
    }


}
