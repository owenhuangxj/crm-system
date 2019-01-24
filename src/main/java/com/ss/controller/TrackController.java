package com.ss.controller;

import com.alibaba.fastjson.JSON;
import com.ss.entity.TrackInfo;
import com.ss.service.TrackInfoService;
import com.ss.util.DateUtil;
import com.ss.vo.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/track")
public class TrackController {

    private static final Logger log = LoggerFactory.getLogger(SysPermController.class);

    @Autowired
    private TrackInfoService tis;

    /**
     * 查询跟踪信息
     *
     * @param stuNumber
     * @return 页面集合
     */
    @ResponseBody
    @RequestMapping(value = "/getTrack")
    public Json getTrackInfo(@RequestParam("stuNumber") String stuNumber) {
        String oper = "get track info";
        List<TrackInfo> trackInfo = tis.getTrackInfo(stuNumber);
        return Json.succ(oper).data("trckInfo", trackInfo);
    }

    /**
     * 添加跟踪信息
     *
     * @param body 跟踪信息页面数据
     * @return 是否添加成功
     */
    @ResponseBody
    @PostMapping(value = "/addTrack")
    public Json addTrackMessage(@RequestBody String body) {
        String oper = "add track info";
        log.info("{}, body: {}", oper, body);
        TrackInfo ti = JSON.parseObject(body, TrackInfo.class);
        System.out.println(ti);
        return Json.result(oper, tis.addTrackInfoRecord(ti));
    }

}
