package com.cuit.yunpan;

import com.cuit.yunpan.bean.userinfo;
import com.cuit.yunpan.dao.userdao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YunpanApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    userdao user;

    @Autowired
    userinfo userin;
    @Test
    void registerUserinfo(){
        System.out.println("");
    }


}
