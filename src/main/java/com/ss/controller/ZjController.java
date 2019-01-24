package com.ss.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ss.entity.Message;
import com.ss.entity.User;
import com.ss.service.MessageService;
import com.ss.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller

public class ZjController {
    @Autowired
    private MessageService ms;


    @GetMapping("/message")
    public String testVueModule() {
        return "msgList";
    }

    @RequestMapping(value = "msgData")
    @ResponseBody
    public Json getMsgData(HttpServletRequest req,
                           HttpServletResponse resp,
                           @RequestBody String body) {
        JSONObject json = JSON.parseObject(body);
        String uname = json.getString("uname");
        Integer status = json.getInteger("status");
        Set<Object> messageList = ms.getMessageList(uname, status);
//        System.out.println(messageList);
        return new Json("msgdata",true,1,"msgdata",messageList);
    }

    @RequestMapping(value = "changeMsgStatus")
    @ResponseBody
    public Boolean changeMsgStatus(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   @RequestBody String body) {
        JSONObject json = JSON.parseObject(body);
        String uname = json.getString("uname");
        Integer msgId = json.getInteger("msgId");
        Integer oldStatus = json.getInteger("oldStatus");
        Boolean result = ms.setMessageStatus(msgId, oldStatus, uname);
        return result;
    }

    @RequestMapping(value = "changeAllStatus")
    @ResponseBody
    public Integer changeAllStatus(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   @RequestBody String body) {
        JSONObject json = JSON.parseObject(body);
        String func = json.getString("func");
        String uname = json.getString("uname");
        return ms.setAllStatus(func, uname);
    }

    @RequestMapping(value = "passMsg")
    @ResponseBody
    public Json passMsg(HttpServletRequest req,
                           @RequestBody String body){
        JSONObject json = JSON.parseObject(body);
        JSONArray list = json.getJSONArray("msglist");
        List<Message> messages = list.toJavaList(Message.class);
        System.out.println("接收数组 ：" + messages);

        String uname = json.getString("uname");
        Integer integer = ms.insertMessage(messages, uname);

        return new Json("passMsg",true,1,"pass",integer);

//        Integer integer = ms.insertMessage(list, uname);
//        return integer;
    }

//    @RequestMapping("savemyid")
//    @ResponseBody
//    public void saveid(@RequestParam("id")int id, HttpServletRequest req){
//        User user = new User();
//        user.setUserId(id);
//        req.getSession().setAttribute("user",user);
//        System.out.println("存入用户"+user);
////        System.out.println("向session中存储用户 ： "+id);
//    }



}
