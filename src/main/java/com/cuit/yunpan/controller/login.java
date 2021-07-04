package com.cuit.yunpan.controller;

import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.services.servicesimpl.userservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
public class login {
    @Resource
    private userservice users;
    @Resource
    private userinfo userb;

    @RequestMapping("/tologin")
    public String tologin (){
        System.out.println("用户准备登录");
        return "login";
    }

    @RequestMapping("/dologin")
    public String  dologin (String pwd, String username,HttpSession session, HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        userb.setPwd(pwd);
        userb.setTel(username);
        System.out.println(userb.getPwd());
        System.out.println(userb.getTel());
       map= users.login(userb);
       if(map.get("login").equals("登录成功")){
           System.out.println("登录成功");
            return "index";
       }
       session.setAttribute("error","用户名或密码错误");
       System.out.println("登录失败");
       return "login";
    }
}
