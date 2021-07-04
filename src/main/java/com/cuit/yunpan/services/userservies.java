package com.cuit.yunpan.services;

import com.cuit.yunpan.bean.userinfo;

import java.util.Map;

public interface userservies {
    Map<String,String> login(userinfo user);
    Map<String,String> register(userinfo user);
    Map<String,String> repassword(userinfo user);
}
