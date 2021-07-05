package com.cuit.yunpan.services.servicesimpl;

import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import com.cuit.yunpan.services.userservies;
import com.sun.javafx.collections.MappingChange;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class userservice implements userservies {
    private static final String HDFS_PATH = "hdfs://192.168.187.80:8020";
    private static final String USER_NAME = "root";
    private static FileSystem fileSystem;
    private static Configuration conf = new Configuration();
    static {
        try {

            conf.set("dfs.replication", "2");

            try {
                fileSystem = FileSystem.get(new URI(HDFS_PATH), conf, USER_NAME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Resource
    private userdao udao;
    @Resource
    private userinfo staticuser;
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
      boolean t= udao.updatePwd(user);
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
    public String fileserv(MultipartFile file)  {
        if(file.isEmpty()){
            return "400";
        }
        String origalname=file.getOriginalFilename();
        String filename=System.currentTimeMillis()+"."+origalname.substring(origalname.lastIndexOf(".")+1);
        String filepase="D:\\r\\";
        File dest= new File(filepase+filename);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String localpath=filepase+filename;
        String hdfspath="/"+staticuser.getTel()+"/"+origalname;
        Path filePath=new Path(localpath);
        Path hdfsPath=new Path(hdfspath);
        try {
            fileSystem.copyFromLocalFile(filePath,hdfsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            FileInputStream in=new FileInputStream(dest);
            try {
                Path pth=new Path("/user/newuser/pc.txt");
                FileSystem fsdst= fileSystem.get(URI.create(pth.toString()),conf);
                FSDataOutputStream out= fsdst.create(pth);
                int b;
                byte data[]=new byte[1024];
                while((b=in.read(data))!= -1){
                    System.out.println(data);
                    out.write(data);
                }
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fileSystem.mkdirs(new Path("/hdfs-api-demo"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return "500";
    }

}
