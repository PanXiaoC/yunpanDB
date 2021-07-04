package com.cuit.yunpan.services.servicesimpl;

import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import com.cuit.yunpan.services.userservies;
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
        String s=udao.getPwd(user);
        System.out.println(s);
        Map<String,String> map= new HashMap<>();
        if(s.equals(user.getPwd())){
            s="登录成功";
            map.put("login",s);
            return map;
        }
        s="登录失败";
        map.put("login",s);
        return map;
    }
}
