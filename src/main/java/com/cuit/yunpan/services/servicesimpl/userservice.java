package com.cuit.yunpan.services.servicesimpl;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import com.cuit.yunpan.services.userservies;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class userservice implements userservies {
    @Resource
    private userdao udao;
    @Override
    public Map<String,String> login(userinfo user){
        int num=udao.checkuser(user);
        Map<String,String> map= new HashMap<>();
        String s;
        if(num==1){
            s=udao.getPwd(user);
            if(s.equals(user.getPwd())){
                s="登录成功";
                map.put("login",s);
                return map;
            }
            s="登录失败";
        }
        s="登录失败";
        map.put("login",s);
        return map;
    }
    @Override
    public Map<String,String> register(userinfo user){
        Map<String,String> map = new HashMap<>();
        boolean t= udao.registerUserinfo(user);
        String s;
        if(t){
            s = "注册成功";
        }
         s = "注册失败";
        map.put("login",s);
        return map;
    }
    @Override
    public Map<String,String> repassword(userinfo user){
        Map<String,String> map = new HashMap<>();
      boolean t= udao.changePwd(user);
        if(t){
            map.put("login", "修改成功");
        }
        map.put("failed","修改失败");

        return map;
    }
    @Override
    public Map<String,String> checkuser(userinfo user) {
        Map<String, String> map = new HashMap<>();
        int num = udao.checkuser(user);
        String s;
        if (num==1) {
            map.put("user","用户已存在");
            return map;
        }
        map.put("user","√");
        return map;
    }

    @Override
    public String fileserv(MultipartFile file, userinfo userb, myfiles myfile) {
        if(file.isEmpty()){
            return "400";
        }
//file.getOriginalFilename()得到文件上传时的文件名
        String origalname=file.getOriginalFilename();
        String filename=System.currentTimeMillis()+"."+origalname.substring(origalname.lastIndexOf(".")+1);
        String filepase="D:\\r\\";
        File dest= new File(filepase+filename);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try{
            file.transferTo(dest);
        }catch(Exception e){
            return "400";
        }
        userb = udao.getUserinfoById(userb);
        System.out.println(userb);
        myfile.setUser_id(userb.getId());
        myfile.setFilename(filename);
        myfile.setIs_folder(1);
        myfile.setIs_upload(1);
        System.out.println("dao层之前");
        System.out.println(myfile);
        System.out.println(udao.insertUpLoad(myfile));
        if(udao.insertUpLoad(myfile)){
            System.out.println("dao层sucess!");
        }
        System.out.println("dao层之后");
        return "500";
    }

}
