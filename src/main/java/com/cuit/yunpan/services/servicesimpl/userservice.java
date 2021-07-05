package com.cuit.yunpan.services.servicesimpl;

import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import com.cuit.yunpan.services.userservies;
import com.sun.javafx.collections.MappingChange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

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
//            DigestUtils.md5DigestAsHex(pwd.getBytes())
            s=udao.getPwd(user);
            s=DigestUtils.md5DigestAsHex(s.getBytes());
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
}
