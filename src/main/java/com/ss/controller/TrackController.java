package com.ss.controller;

import com.alibaba.fastjson.JSON;
import com.ss.entity.TrackInfo;
import com.ss.service.TrackInfoService;
import com.ss.util.DateUtil;
import com.ss.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/track")
public class TrackController {

    @Autowired
    private TrackInfoService tis;
// @Autowired
//    private TrackModelService tms;

    /**
     * 查询跟踪信息
     * @param stuNumber
     * @return 页面集合
     */
    @ResponseBody
    @RequestMapping(value = "/getTrack")
    public Json getTrackInfo(@RequestParam("stuNumber") String stuNumber) {
        String oper = "get track info";
        List<TrackInfo> trackInfo = tis.getTrackInfo(stuNumber);
        return Json.succ(oper).data("trckInfo",trackInfo);
    }

    /**
     * 添加跟踪信息
     *
     * @param ti 跟踪信息页面数据
     * @return 是否添加成功
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/addTrack")
    public Boolean addTrackMessage(@RequestBody TrackInfo ti) {
        // 转换时间格式
        ti.setTrackTime(DateUtil.changeDateTime(ti.getTrackTime()));
        ti.setNextTrackTime(DateUtil.changeDateTime(ti.getNextTrackTime()));
        ti.setPredictTime(DateUtil.changeDateTime(ti.getPredictTime()));
        ti.setPredictTrade(DateUtil.changeDateTime(ti.getPredictTrade()));
        return tis.addTrackInfoRecord(ti);
    }

    @GetMapping("/tailAfter")
    public String track() {
        return "trackTable";
    }

//    @PostMapping(value = "/getTrackList", produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public PageData<TrackModel> getTrackList(
//            @RequestParam("pageSize") Integer pageSize,
//            @RequestParam("pageNum") Integer pageNum,
//            @RequestParam("input") String input,
//            @RequestParam("value") String value) {
////        System.out.println("进getTrackList的参数：" + input + "，" + value + "，" + pageNum + "，" + pageSize);
//        String inp = "".equals(input) ? null : input;
//        PageData<TrackModel> trackModel = tms.getTrackModel(inp, value, pageNum, pageSize);
////        System.out.println("返回的集合：" + trackModel);
//        return trackModel;
//    }


}
