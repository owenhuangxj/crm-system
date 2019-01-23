package com.ss.controller;

import com.ss.entity.TrackModel;
import com.ss.service.TrackModelService;
import com.ss.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TraceController
 * @Description TODO
 * @Author Jack Hu
 * @Date 2019/1/23 15:20
 */
@Controller
@RequestMapping("/track")
public class TraceController {


    @Autowired
    private TrackModelService tms;


    @PostMapping(value = "/getTrackList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public PageData<TrackModel> getTrackList(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("input") String input,
            @RequestParam("value") String value) {
        System.out.println("进getTrackList的参数：" + input + "，" + value + "，" + pageNum + "，" + pageSize);
        String inp = "".equals(input) ? null : input;
        PageData<TrackModel> trackModel = tms.getTrackModel(inp, value, pageNum, pageSize);
        System.out.println("返回的集合：" + trackModel);
        return trackModel;
    }
}
