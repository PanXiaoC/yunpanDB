package com.cuit.yunpan.controller;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.recycl;
import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import com.cuit.yunpan.services.servicesimpl.userservice;
import org.apache.http.HttpResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
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
    @Resource
    private userdao myfiledao;

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
            String s1="1";
           session.setAttribute("page",s1);
           Map<String,Object> map2 =new HashMap<>();
           map2=users.sumSize(userb);
           session.setAttribute("count",map2.get("count"));
           session.setAttribute("sumsize",map2.get("sumsize"));
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
        System.out.println("静茹");
        Map<String,String> map=new HashMap<>();
        userb.setTel(tel);
        System.out.println(userb.getTel());
        map= users.checkuser(userb);
        if(map.get("user").equals("用户已存在")){
            System.out.println("用户已存在");
            return "用户已存在";
        }
        return "www.baidu.com";
    }
    @RequestMapping(value="/doupload")
    public String  doupload (@RequestParam("file") MultipartFile file ,HttpSession session) throws IOException {
        System.out.println("你好，文件");
//        System.out.println(userb);
        myfile.setContent(file.getInputStream());
         String s = users.fileserv(file,userb,myfile);
         if(s.equals("500")){
             List<myfiles> list;
             list=users.getallfile(userb,myfile);
             session.setAttribute("list",list);
             System.out.println(list);
             String s1=users.getusername(userb);
             session.setAttribute("username",s);
             String s2="1";
             session.setAttribute("page",s1);
             Map<String,Object> map2 =new HashMap<>();
             map2=users.sumSize(userb);
             session.setAttribute("count",map2.get("count"));
             session.setAttribute("sumsize",map2.get("sumsize"));
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
            String s3=users.getusername(userb);
            session.setAttribute("username",s);
            String s5="1";
            session.setAttribute("page",s1);
            Map<String,Object> map2 =new HashMap<>();
            map2=users.sumSize(userb);
            session.setAttribute("count",map2.get("count"));
            session.setAttribute("sumsize",map2.get("sumsize"));
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    return "success";
        }
        return "fail";
    }
    @RequestMapping("/deletefile")
    @ResponseBody
    public String  deletefile(String id,HttpSession session) throws IOException {
        System.out.println(id);
        System.out.println("pxc");
        int s=Integer.parseInt(id);
        myfile.setId(s);
        String s1 = users.deletefiles(userb,myfile);
        if("0".equals(s1)){
            List<myfiles> list;
            list=users.getallfile(userb,myfile);
            session.setAttribute("list",list);
            System.out.println(list);
            System.out.println("删除成功");
            String s4=users.getusername(userb);
            session.setAttribute("username",s);
            String s5="1";
            session.setAttribute("page",s1);
            Map<String,Object> map2 =new HashMap<>();
            map2=users.sumSize(userb);
            session.setAttribute("count",map2.get("count"));
            session.setAttribute("sumsize",map2.get("sumsize"));
            return "success";
        }
       /*
        int s=Integer.parseInt(id);
        myfile.setId(s);
        String s1=users.updatefilename(userb,myfile);
        if("1".equals(s1)){
            List<myfiles> list;
            list=users.getallfile(userb,myfile);
            session.setAttribute("list",list);
            System.out.println(list);
            System.out.println("修改成功");
        }*/
        return "fail";
    }
    @RequestMapping("/downloadfile")
    @ResponseBody
    public void  downloadfile(String id,HttpServletResponse response) throws IOException {
        System.out.println("进入下载");
        response.setContentType("application/octet-stream");
        response.setHeader("content-type","application/octet-stream");
        int id2=Integer.parseInt(id);
        myfile.setId(id2);
        myfile=myfiledao.getMyfilesByid(myfile);
        String filename=myfile.getFilename();
        try {
            filename= URLEncoder.encode(filename,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition","attachment;filename="+filename);

        OutputStream out=response.getOutputStream();

        InputStream in=users.downloadfile(myfile);
        byte[] buff=new byte[1024];
        int lenth=0;
        lenth= in.read(buff);
        while(lenth!=-1){
            out.write(buff);
            lenth= in.read(buff);
        }
        in.close();
        out.flush();
        out.close();
    }
    @RequestMapping("/updatepage")
    @ResponseBody
    public String  updatepage(String id,String page,HttpSession session){
        if(page.equals("")){
            System.out.println("出错");
            return "fail";
        }
       int newpage=Integer.parseInt(page);
        if(id.equals("down")){
            newpage=newpage-1;
        }
        if(id.equals("up")){
            newpage=newpage+1;
        }
        if(id.equals("center")){
            newpage=newpage;
        }
        Map<String,Object> map=new HashMap<>();
        map = users.limitpage(userb,newpage);
        session.setAttribute("list",map.get("list"));
        String s=users.getusername(userb);
        session.setAttribute("username",s);

        session.setAttribute("page",map.get("page"));
        System.out.println(map.get("page"));
        Map<String,Object> map2 =new HashMap<>();
        map2=users.sumSize(userb);
        session.setAttribute("count",map2.get("count"));
        session.setAttribute("sumsize",map2.get("sumsize"));
        return "success";
    }
    @RequestMapping("/showrecycle")
    @ResponseBody
    public String  showrecycle(HttpSession session){
        List<recycl> list;
        System.out.println("进入回收站");
        list =users.showshowrecycle_s(userb);
        System.out.println(list);
        session.setAttribute("list",list);
        String s=users.getusername(userb);
        session.setAttribute("username",s);
        String s1="1";
        session.setAttribute("page",s1);
        Map<String,Object> map2 =new HashMap<>();
        map2=users.sumSize(userb);
        session.setAttribute("count",map2.get("count"));
        session.setAttribute("sumsize",map2.get("sumsize"));
       return "success";
    }
    @RequestMapping("/createfilebean")
    @ResponseBody
    public String  createfilebean(String filebean, HttpSession session) throws IOException {
        System.out.println(filebean);
        myfile.setFilename(filebean);
        users.createfilebean_s(myfile,userb);
         return "success";
    }
}
