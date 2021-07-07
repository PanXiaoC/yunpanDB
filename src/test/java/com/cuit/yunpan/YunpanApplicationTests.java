package com.cuit.yunpan;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import com.cuit.yunpan.services.servicesimpl.userservice;
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

    @Autowired
    myfiles myf;
    @Autowired
    userservice usv;

    @Test
    public void testsize(){
        long r = 471928471;
        System.out.println("-----------------");
        String K =usv.file_size_check(r);
        System.out.println("=======================================");
        System.out.println(K);
        System.out.println("=======================================");
    }
    @Test
    void test1(){
        System.out.println(usv);
    }

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
        userin.setUsername("张");
        userin.setPwd("123");
        userin.setGender(1);
        userin.setEmail("lsj@qq.com");
        userin.setTel("1234567896");
        System.out.println("==========");
        System.out.println(ud.registerUserinfo(userin));
        System.out.println("==========");
    }

    @Test
    void sumFile_size(){
        myf.setUser_id(2);
        System.out.println("==========");
        System.out.println(ud.sumFile_size(myf.getUser_id()));
    }

    @Test
    void countUserFile(){
        myf.setUser_id(2);
        System.out.println("ud.countUserFile(myf.getUser_id()) = " + ud.countUserFile(myf.getUser_id()));
        System.out.println("==========");
    }

    @Test
    void limitpage_myfiles(){
        System.out.println("==========================================================");
        System.out.println(ud.limitpage_myfiles(2,0,2));
        System.out.println("==========================================================");

    }

    @Test
    void findshare_myfiles(){
        System.out.println("==========================================================");

        System.out.println(ud.findshare_myfiles(0));
        System.out.println("==========================================================");

    }

}
