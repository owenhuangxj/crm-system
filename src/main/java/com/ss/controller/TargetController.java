package com.ss.controller;

import com.ss.service.TargetService;
import com.ss.util.ChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TargetController {
    @Autowired
    private TargetService targetService;

    @GetMapping("getDoorCount")
    @ResponseBody
    public ChartData getCount(){
        ChartData chartData = targetService.selectFindTarget();
        return chartData;
    }
    @GetMapping("findCloseCount")
    @ResponseBody
    public ChartData findCount(){
        ChartData targetData = targetService.findTargetCount();
        return targetData;
    }
}
