package com.cuit.yunpan.controller;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.services.servicesimpl.userservice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin
public class login {
    @Resource
    private myfiles myfile;
    @Resource
    private userservice users;
    @Resource
    private userinfo userb;
    @Resource
    private myfiles file;

    @RequestMapping("/tologin")
    public String tologin (){
        System.out.println("用户准备登录");
        return "login";
    }

    @RequestMapping("/dologin")
    public String  dologin (String pwd, String tel,HttpSession session, HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        userb.setPwd(pwd);
        userb.setTel(tel);
        System.out.println(userb.getPwd());
        System.out.println(userb.getTel());
       map= users.login(userb);
       if(map.get("login").equals("登录成功")){
           System.out.println("登录成功");
           List<myfiles> list;
           list=users.getallfile(userb,myfile);
           session.setAttribute("list",list);
           String s=users.getusername(userb);
           session.setAttribute("username",s);
            return "index";
       }
       session.setAttribute("error","用户名或密码错误");
       System.out.println("登录失败");
       return "login";
    }
    @RequestMapping("/toregister")
    public String toregister(HttpSession session){
        System.out.println("跳入注册");
        session.invalidate();
        return "register";
    }
    @RequestMapping("/tosubmit")
    public String tosubmit(HttpSession session){
        System.out.println("跳入文件");
        session.invalidate();
        return "submit";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        System.out.println("退出系统");
        session.invalidate();
        return "login";
    }
    @RequestMapping("/tochangepwd")
    public String tochangepwd( HttpSession session){
        System.out.println("跳入修改密码");
        session.invalidate();
        return "recover-password";
    }
    @RequestMapping("/doregister")
    public String  doregister (String pwd, String username,String tel,String email,String gender,HttpSession session, HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        userb.setPwd(pwd);
        userb.setUsername(username);
        userb.setTel(tel);
        userb.setEmail(email);
        if(gender.equals("男")){
            userb.setGender(1);
        }else
        {
            userb.setGender(0);
        }
        System.out.println(userb.getPwd());
        System.out.println(userb.getTel());
        System.out.println(userb.getEmail());
        System.out.println(userb.getGender());
        System.out.println(userb.getUsername());
        map= users.register(userb);
        if(map.get("login").equals("注册成功")){
            System.out.println("注册成功");
            return "login";
        }
        System.out.println("注册失败");
        return "login";
    }
    @RequestMapping("/dochangepwd")
    public String  dochangepwd (String pwd, String tel,HttpSession session, HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        userb.setPwd(pwd);
        userb.setTel(tel);
        System.out.println(userb.getPwd());
        System.out.println(userb.getTel());
        map= users.repassword(userb);
        if(map.get("login").equals("修改成功")){
            System.out.println("修改成功");
            return "login";
        }
        System.out.println("修改失败");
        return "login";
    }
    @RequestMapping("/docheckuser")
    @ResponseBody
    public String  docheckuser (String tel){
        Map<String,String> map=new HashMap<>();
        userb.setTel(tel);
        System.out.println(userb.getTel());
        map= users.checkuser(userb);
        if(map.get("user").equals("用户已存在")){
            System.out.println("用户已存在");
            return "用户已存在";
        }
        return "√";
    }
    @RequestMapping(value="/doupload")
    public String  doupload (@RequestParam("file") MultipartFile file ,HttpSession session, HttpServletRequest request) throws IOException {
        System.out.println("你好，文件");
//        System.out.println(userb);
        myfile.setContent(file.getInputStream());
         String s = users.fileserv(file,userb,myfile);
         if(s.equals("500")){
             List<myfiles> list;
             list=users.getallfile(userb,myfile);
             session.setAttribute("list",list);
             System.out.println(list);
             return "index";
         }
        List<myfiles> list;
        list=users.getallfile(userb,myfile);
        session.setAttribute("list",list);
        System.out.println(list);
        System.out.println("=====");
         return "index";
    }
    @RequestMapping("/renamefile")
    @ResponseBody
    public String  renamefile(String id,String value,HttpSession session) throws IOException {
        System.out.println(id);
        System.out.println(value);
        myfile.setFilename(value);
        int s=Integer.parseInt(id);
        myfile.setId(s);
        String s1=users.updatefilename(userb,myfile);
        if("1".equals(s1)){
            List<myfiles> list;
            list=users.getallfile(userb,myfile);
            session.setAttribute("list",list);
            System.out.println(list);
            System.out.println("修改成功");
        }
        return "success";
    }
}
