package com.ss.controller;
/**
 * @Author: 王顺坤
 * @Date: 2019年1月11日
 * @Description:王顺坤的前端测试类
 */

import com.ss.constant.ResumeTemplate;
import com.ss.entity.Selector;
import com.ss.service.ResumeAllotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ResumeAllotController {
    @Autowired
    private ResumeAllotService ras;

    @GetMapping("/test")
    public String index() {
        return "wsk-test";
    }

    @PostMapping("importResumeFromExcel")
    public String uploadAndWrite(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {

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

        return "wsk-test";
    }

    @RequestMapping("/getUnAllotResume")
    public @ResponseBody
    String getUnAllotResum() {

        String str = ras.getOrginStudent(1L, 1L, "赵", 0);

        return str;
    }

    @RequestMapping("/getAllUsers")
    public @ResponseBody
    List<Selector> getAllUsers() {
        return ras.getAllUser();
    }

    @RequestMapping("/allotResume")
    public @ResponseBody
    String AllotResume(@RequestParam("map") Map<Integer,Integer> map) {

    ras.allotResume(map);

        return null;


    }


}
