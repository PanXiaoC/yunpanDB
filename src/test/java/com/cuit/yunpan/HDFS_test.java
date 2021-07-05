package com.cuit.yunpan;

import com.cuit.yunpan.controller.login;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
//import org.junit.After;
//import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.apache.hadoop.mapreduce.MRJobConfig.USER_NAME;

public class HDFS_test {
    private static final String HDFS_PATH="hdfs://192.168.119.120:8020";
    private static final String USER_NAME="root";
    private static FileSystem fileSystem;

//    @Before
    public void prepare(){
        Configuration conf = new Configuration();
        conf.set("dfs.replication","2");
        try {
            fileSystem = FileSystem.get(new URI(HDFS_PATH),conf,USER_NAME);
            System.out.println(fileSystem);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mkdir(){
        Path mydir = new Path("/tomtest1");
        try {
            Configuration conf = new Configuration();
            conf.set("dfs.replication","2");
            fileSystem = FileSystem.get(new URI(HDFS_PATH),conf,USER_NAME);
            System.out.println(fileSystem);
            fileSystem.mkdirs(mydir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteFile(){
        Path mydir = new Path("/tomtest");
        try {
            Configuration conf = new Configuration();
            conf.set("dfs.replication","2");
            fileSystem = FileSystem.get(new URI(HDFS_PATH),conf,USER_NAME);
            fileSystem.delete(mydir,true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void upLoad(){
        login lo = new login();
//        lo.upLoad();
    }

//    @After
    public void destroy(){
        fileSystem=null;
    }
}
