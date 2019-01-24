package com.ss.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ss.entity.SysUser;
import com.ss.service.SysUserService;
import com.ss.util.SendEmail;
import com.ss.vo.Json;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private SysUserService sysUserService;
    /*注册邮箱验证*/
    @ResponseBody
    @PostMapping("/findEmail")
    public Json findEmail(@RequestBody String body){
        JSONObject jsonObject = JSON.parseObject(body);
     String email=  jsonObject.getString("email");
        String num=  jsonObject.getString("num");
        System.out.println("接收的数据"+ email);
        SysUser user = sysUserService.getEmail(email);
        Json json = new Json("邮箱验证",true,200,"email",true);
        if(user==null){
            try {
                SendEmail.send(email,num);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
          json.data(false);
        }
        return json;
    }

    //发送邮件的内容
    @GetMapping("{email}/{num}/authCode")
    public String authCode(@PathVariable("email")String email, @PathVariable("num")String num, HttpServletRequest request){
        //System.out.println("email:"+email+"num:"+num);
        if(num!=null){
            request.setAttribute("num",num);
            return "code";
        }
        return null;

    }
    /*注册用户名验证*/
    @ResponseBody
    @PostMapping("findName")
    public Json findName(@RequestBody String body){
        JSONObject jsonObject = JSON.parseObject(body);
        System.out.println(body);
        String username = jsonObject.getString("username");
        SysUser userName = sysUserService.getUserName(username);
        Json json = new Json("邮箱验证",true,200,"email",true);
        if(userName!=null){
            json.data(false);
        }
        return json;
    }
    /*注册验证*/
    @ResponseBody
    @PostMapping("/addUser")
    public Json addUser(@RequestBody String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        System.out.println("json:"+jsonObject);
        String email = jsonObject.getString("email");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        //密码加密
        RandomNumberGenerator saltGen = new SecureRandomNumberGenerator();
        String salt = saltGen.nextBytes().toBase64();
        String hashedPwd = new Sha256Hash(password, salt, 1024).toBase64();
        int sysUser = sysUserService.insertUser(email, username, hashedPwd);
        Json json = new Json("邮箱验证", true, 200, "email", true);
        if (sysUser <= 0) {
            json.data(false);
        }
        return json;
        }
}
