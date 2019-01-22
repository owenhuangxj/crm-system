package com.ss.controller;

import com.ss.entity.Message;
import com.ss.entity.User;
import com.ss.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping(value = "msgData", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Set<Object> getMsgData(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  @RequestParam("status") Integer status) {
//        User user = (User) (req.getSession().getAttribute("user"));
//        Set<Object> messageList = ms.getMessageByUserId(user.getUserId());
//        System.out.println("==============");
//        System.out.println(status);
//        System.out.println("==============");
        User user = (User) req.getSession().getAttribute("user");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        Set<Object> messageList = ms.getMessageList(user.getUserId(), status);
//        System.out.println(messageList);
        return messageList;
    }

    @RequestMapping(value = "changeMsgStatus", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Boolean changeMsgStatus(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   @RequestParam("msgId") Integer msgId,
                                   @RequestParam("oldStatus") Integer oldStatus) {
        User user = (User) req.getSession().getAttribute("user");
//        resp.setHeader("Access-Control-Allow-Origin", "*");
        Boolean result = ms.setMessageStatus(msgId, oldStatus, user.getUserId());
        return result;
    }

    @RequestMapping(value = "changeAllStatus/{func}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Integer changeAllStatus(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   @PathVariable("func") String func) {
        User user = (User) req.getSession().getAttribute("user");
//        resp.setHeader("Access-Control-Allow-Origin", "*");
        return ms.setAllStatus(func, user.getUserId());
    }

    @RequestMapping(value = "passMsg")
    @ResponseBody
    public Integer passMsg(@RequestBody List<Message> list, HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        System.out.println("接收数组 ：" + list);
        Integer integer = ms.insertMessage(list, user);
        return integer;
    }

    @RequestMapping("savemyid")
    @ResponseBody
    public void saveid(@RequestParam("id")int id, HttpServletRequest req){
        User user = new User();
        user.setUserId(id);
        req.getSession().setAttribute("user",user);
        System.out.println("存入用户"+user);
//        System.out.println("向session中存储用户 ： "+id);
    }



}
