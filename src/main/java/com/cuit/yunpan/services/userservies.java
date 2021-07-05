package com.cuit.yunpan.services;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.userinfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface userservies {
    Map<String,String> login(userinfo user);
    Map<String,String> register(userinfo user);
    Map<String,String> repassword(userinfo user);
    Map<String,String> checkuser(userinfo user);
    String fileserv(MultipartFile file, userinfo userb, myfiles myfile);
}
