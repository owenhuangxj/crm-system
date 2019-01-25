package com.ss.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ss.entity.TrackModel;
import com.ss.service.TrackModelService;
import com.ss.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName TraceController
 * @Description TODO
 * @Author Jack Hu
 * @Date 2019/1/23 15:20
 */
@Controller
@RequestMapping("/trace")
public class TraceController {

    @Autowired
    private TrackModelService tms;

    /**
     * 多条件查询学员跟踪信息
     *
     * @param pageSize
     * @param pageNum
     * @param input
     * @param value
     * @return
     */
    @RequestMapping(value = "/getTraceList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Json getTraceList(@RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("input") String input, @RequestParam("value") String value) {
        String inp = "".equals(input) ? null : input;
        Json trackModel = tms.getTrackModel(inp, value, pageNum, pageSize);
        System.out.println("返回的Json：" + trackModel);
        return trackModel;
    }

    @RequestMapping(value = "/getUserNames", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Json getUserNames(){
        Json userNames = tms.getUserNames();
        return userNames;
    }

    @RequestMapping("/updateNick")
    @ResponseBody
    public Json updateNick(@RequestBody String body) {
        JSONObject obj = JSON.parseObject(body);
        JSONArray trace = obj.getJSONArray("trace");
        String nick = obj.getString("nick");
        List<TrackModel> trackModel = trace.toJavaList(TrackModel.class);
        Json json = tms.updateNick(nick, trackModel);
        return json;
    }

    @RequestMapping("/updateStuStatus")
    @ResponseBody
    public Json updateStuStatus(@RequestBody String body){
        JSONObject obj = JSON.parseObject(body);
        JSONArray trace = obj.getJSONArray("trace");
        List<TrackModel> trackModels = trace.toJavaList(TrackModel.class);
        System.out.println("trackModels：" + trackModels);
        Json json = tms.updateStuStatus(trackModels);
        return json;
    }
}
