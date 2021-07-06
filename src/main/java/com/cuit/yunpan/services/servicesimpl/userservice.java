package com.cuit.yunpan.services.servicesimpl;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import com.cuit.yunpan.services.userservies;
import com.sun.javafx.collections.MappingChange;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private myfiles myf;

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
    public String fileserv(MultipartFile file, userinfo userb, myfiles myfile) throws IOException {
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
        InputStream in=myfile.getContent();
        String hdfspath="/"+staticuser.getTel()+"/"+origalname;
        Path hdfsPath=new Path(hdfspath);
        FSDataOutputStream out=fileSystem.create(hdfsPath);
        IOUtils.copyBytes(in,out,4096,true);
        out.flush();
        out.close();
        userb=udao.getUserinfoById(userb);
        myfile.setUser_id(userb.getId());
        myfile.setFilename(origalname);
        myfile.setFile_ext("."+origalname.substring(origalname.lastIndexOf(".")+1));

        try {
            FileStatus fileStatus = fileSystem.getFileStatus(hdfsPath);
            Long s=fileStatus.getLen()/1024;

            myfile.setFile_size(s.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        myfile.setFile_path(hdfspath);
        myfile.setIs_upload(1);
        System.out.println("dao层之前");
        System.out.println(myfile);
        if(udao.insertUpLoad(myfile)){
            System.out.println("dao层sucess!");
        }
        System.out.println("dao层之后");
        return "500";
    }

    @Override
    public List<myfiles> getallfile(userinfo user,myfiles myfile) {
        List<myfiles> list ;
        user=udao.getUserinfoById(user);
        myfile.setUser_id(user.getId());
        list=udao.getMyfilesByUser_id(myfile);

        return list;
    }

    @Override
    public String getusername(userinfo user) {
        String s=udao.getusername(user);
        return s;
    }

    @Override
    public String updatefilename(userinfo user,myfiles myfile) throws IOException {
        myf=udao.getMyfilesByid(myfile);
        String oldpath=myf.getFile_path();
        String newpath="/"+user.getTel()+"/"+myfile.getFilename();
        myfile.setFile_path(newpath);
        Path old= new Path(oldpath);
        Path newp= new Path(newpath);
        fileSystem.rename(old,newp);
        int s=udao.changeFilename(myfile);
        if(s==1){
            return "1";
        }
        return "0";
    }

    public List<myfiles> limitpage(Integer user_id,Integer page){//分页操作，输出页码。
        Integer pagesize = 5;//一页中显示的条数
        Integer pagemax = udao.countUserFile(user_id);
        if(page <= 1){
            return udao.limitpage_myfiles(user_id,0,pagesize);
        }
        if (page > 1 && page <= pagemax){
            return udao.limitpage_myfiles(user_id,(page-1)*pagesize,pagesize);
        }

        return udao.limitpage_myfiles(user_id,(pagemax-1)*pagesize,pagesize);
    }

    public String file_size_check(long s){
        String L = "0KB";
        if(s < 1){
            L = "1KB";
        }
        if(s >= 1 && s < 1024){
            L = s + "KB";
        }
        if (s >= 1024 && s <1024*1024){
            L = (s / 1024) + "MB";
        }
        if(s >= 1024*1024){
            L = (s / (1024*1024))+"GB";
        }
        return L;
    }

}
