package com.cuit.yunpan;

import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Hdfs;
//import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.file.Path;

import static org.apache.hadoop.mapreduce.MRJobConfig.USER_NAME;

@SpringBootTest
class YunpanApplicationTests {

    @Test
    void contextLoads() {
    }
    @Resource
    userdao ud;

    @Autowired
    userdao user;

    @Autowired
    userinfo userin;

    @Test
    void getUserinfoByTel(){
        userin.setTel("18780693160");
        System.out.println("=================");
        System.out.println(user.getUserinfoByTel(userin));
    }

    @Test
    void changePwd(){
        userin.setTel("18800000003");
        userin.setPwd("222222");
        System.out.println("=================");
        System.out.println(ud.changePwd(userin));
    }

    @Test
    void registerUserinfo(){
        userin.setUsername("张无");
        userin.setPwd("123");
        userin.setGender(1);
        userin.setEmail("lsj@qq.com");
        userin.setTel("1234567896");
        System.out.println("==========");
        System.out.println(ud.registerUserinfo(userin));
        System.out.println("==========");
    }

}
