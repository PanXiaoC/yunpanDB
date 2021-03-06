package com.cuit.yunpan.services.servicesimpl;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.recycl;
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
import java.io.OutputStream;
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
    @Resource
    private recycl rec;

    @Override
    public Map<String,String> login(userinfo user){
        int num=udao.checkuser(user);
        Map<String,String> map= new HashMap<>();
        String s;
        if(num==1){
            s=udao.getPwd(user);
            if(s.equals(user.getPwd())){
                s="????????????";
                map.put("login",s);
                return map;
            }
            s="????????????";
        }
        s="????????????";
        map.put("login",s);
        return map;
    }
    @Override
    public Map<String,String> register(userinfo user){
        Map<String,String> map = new HashMap<>();
        boolean t= udao.registerUserinfo(user);
        String s;
        if(t){
            s = "????????????";
        }
         s = "????????????";
        map.put("login",s);
        return map;
    }
    @Override
    public Map<String,String> repassword(userinfo user){
        Map<String,String> map = new HashMap<>();
      boolean t= udao.changePwd(user);
        if(t){
            map.put("login", "????????????");
        }
        map.put("failed","????????????");

        return map;
    }
    @Override
    public Map<String,String> checkuser(userinfo user) {
        Map<String, String> map = new HashMap<>();
        int num = udao.checkuser(user);
        String s;
        if (num==1) {
            map.put("user","???????????????");
            return map;
        }
        map.put("user","???");
        return map;
    }

    @Override
    public String fileserv(MultipartFile file, userinfo userb, myfiles myfile) throws IOException {
        if(file.isEmpty()){
            return "400";
        }
        String origalname=file.getOriginalFilename();

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
            myfile.setFile_size(  fileStatus.getLen()/1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        myfile.setFile_path(hdfspath);
        myfile.setIs_upload(1);
        myfile.setIs_folder(0);

        System.out.println("dao?????????");
        System.out.println(myfile);
        int s=udao.insertUpLoad(myfile);
        System.out.println("dao?????????");
        return "500";
    }

    @Override
    public List<myfiles> getallfile(userinfo user,myfiles myfile) {
        List<myfiles> list ;
        user=udao.getUserinfoById(user);
        myfile.setUser_id(user.getId());
        list =udao.limitpage_myfiles(user.getId(),0,5);
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
        fileSystem.delete(old,false);
        int s=udao.changeFilename(myfile);
        if(s==1){
            return "1";
        }
        return "0";
    }

    @Override
    public String deletefiles(userinfo user, myfiles myfile) throws IOException {
        myf=udao.getMyfilesByid(myfile);
        String oldpath=myf.getFile_path();
        String newpath="/"+user.getTel()+"/"+"recycle"+"/"+myf.getFilename();
        System.out.println(newpath);
        Path old= new Path(oldpath);
        System.out.println(oldpath);
        Path newp= new Path(newpath);
        String newpath2="/"+user.getTel()+"/"+"recycle";
        Path mulupath=new Path(newpath2);
        fileSystem.mkdirs(mulupath);
        fileSystem.rename(old,newp);
        fileSystem.delete(old,false);
        user=udao.getUserinfoByTel(user);
        rec.setUser_id(user.getId());
        rec.setFile_ext(myf.getFile_ext());
        rec.setFile_path(newpath);
        rec.setFile_size(myf.getFile_size());
        rec.setFilename(myf.getFilename());
        int s = udao.insertRecycl(rec);
        udao.deleteMyfiles(myf);
        return "0";
    }
    public InputStream  downloadfile(myfiles myfile) throws IOException {

        myf=udao.getMyfilesByid(myfile);
        String hdfspath=myf.getFile_path();
        Path descpath=new Path(hdfspath);
        InputStream is=fileSystem.open(descpath);
        return is;
    }

    public Map<String,Object> limitpage(userinfo user,Integer page){//??????????????????????????????
        Integer pagesize = 5;//????????????????????????
        Map<String,Object> map=new HashMap<>();
        user=udao.getUserinfoByTel(user);
        myf.setUser_id(user.getId());
        List<myfiles> list=null;
        if(udao.countUserFile(myf)==null){
            page=1;
            map.put("page",page);
            map.put("list",list);
            return map;
        }
        Integer pagemax = udao.countUserFile(myf);
        Integer maxpage=(int)Math.ceil(pagemax/pagesize)+1;
        System.out.println(maxpage);
        System.out.println(pagemax);
        if(page <= 1){
            list= udao.limitpage_myfiles(user.getId(),0,pagesize);
            page=1;
            map.put("page",page);
            map.put("list",list);
            return map;
        }
        if (page > 1 && page <= maxpage){
            list=udao.limitpage_myfiles(user.getId(),(page-1)*pagesize,pagesize);
            map.put("page",page);
            map.put("list",list);
            return map;
        }
        list= udao.limitpage_myfiles(user.getId(),(maxpage-1)*pagesize,pagesize);
        map.put("page",maxpage);
        map.put("list",list);
        return map;
    }
    @Override
    //???myfiles???????????????????????????????????????????????????1???
    public List<myfiles> findShareInMyfiles(){
        return  udao.findshare_myfiles(1);
    }

    @Override
    //???myfiles??????????????????????????????????????????????????????????????????????????????,
    public Map<String,Object> sumSize(userinfo user){
        Map<String,Object> map = new HashMap<>();
        user=udao.getUserinfoByTel(user);
        myf.setUser_id(user.getId());
        System.out.println(udao.sumOneFilesByUserid(myf));
        if(udao.sumOneFilesByUserid(myf)==null){
            map.put("sumsize", 0);
            map.put("count", 0);
            return map;
        }
        long sum=udao.sumOneFilesByUserid(myf);
        String r = file_size_check(sum);
        int count=udao.countUserFile(myf);
        map.put("sumsize", r);
        map.put("count", count);
        return map;
    }

    @Override
    //???myfiles??????????????????????????????????????????kb???????????????????????????????????????5GB???
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

    @Override
    public List<recycl> showshowrecycle_s(userinfo user) {
        user=udao.getUserinfoByTel(user);
        rec.setUser_id(user.getId());
        List<recycl> list=udao.getallrecycle(rec);
        return list;
    }

    @Override
    public String createfilebean_s(myfiles myfile,userinfo user) throws IOException {
        String newpath2="/"+user.getTel()+"/"+myfile.getFilename();
        Path beanpath=new Path(newpath2);
        fileSystem.mkdirs(beanpath);
        user=udao.getUserinfoByTel(user);
        myfile.setUser_id(user.getId());
        myfile.setFile_path(newpath2);
        myfile.setFile_size(null);
        myfile.setFile_ext("-");
        myfile.setIs_folder(1);
        myfile.setIs_upload(0);
        udao.insertUpLoad(myfile);
        return null;
    }
}
