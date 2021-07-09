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

}
