package com.cuit.yunpan.services;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.userinfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
}
