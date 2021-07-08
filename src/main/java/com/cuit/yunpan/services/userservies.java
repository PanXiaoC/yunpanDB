package com.cuit.yunpan.services;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.recycl;
import com.cuit.yunpan.bean.userinfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface userservies {
    Map<String,String> login(userinfo user);
    Map<String,String> register(userinfo user);
    Map<String,String> repassword(userinfo user);
    Map<String,String> checkuser(userinfo user);
    String fileserv(MultipartFile file, userinfo userb, myfiles myfile) throws IOException;
    List<myfiles> getallfile(userinfo user, myfiles myfile);
    String getusername(userinfo user);
    String updatefilename(userinfo user, myfiles myfile) throws IOException;
    String deletefiles(userinfo user, myfiles myfile) throws IOException;
    InputStream downloadfile(myfiles myfile) throws IOException;
    Map<String,Object> limitpage(userinfo user,Integer page);
    List<myfiles> findShareInMyfiles();
    Map<String,Object> sumSize(userinfo user);
    String file_size_check(long s);
    List<recycl> showshowrecycle_s(userinfo user);
    String createfilebean_s(myfiles myfile,userinfo user) throws IOException;
}
