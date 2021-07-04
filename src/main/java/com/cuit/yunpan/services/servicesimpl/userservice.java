package com.cuit.yunpan.services.servicesimpl;

import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import com.cuit.yunpan.services.userservies;
import com.sun.javafx.collections.MappingChange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    public Map<String,String> register(userinfo user){
        Map<String,String> map = new HashMap<>();
        udao.registerUserinfo(user);
        String s = "注册成功";
        map.put("login",s);
        return map;
    }

    public Map<String,String> repassword(userinfo user){
        Map<String,String> map = new HashMap<>();
        userinfo us = new userinfo();
        us = udao.getUserinfoByTel(user);
        if(us.getTel() != user.getTel()){
            map.put("notel","无该用户");
            return map;
        }
        us.setPwd(user.getPwd());
        Boolean i = udao.updatePwd(us);
        if(i == true){
            map.put("login", "修改成功");
        } else
        {
            map.put("failed","修改失败");
        }
        return map;
    }

}
