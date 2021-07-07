package com.cuit.yunpan;

import com.cuit.yunpan.controller.login;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
//import org.junit.After;
//import org.junit.Before;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.apache.hadoop.mapreduce.MRJobConfig.USER_NAME;

public class HDFS_test {
    private static final String HDFS_PATH="hdfs://192.168.119.120:8020";
    private static final String USER_NAME="root";
    private static FileSystem fileSystem;

    @Before
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

//    @Test
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
    //文件重命名
    @Test
    public void rename() throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication","2");
        fileSystem = FileSystem.get(new URI(HDFS_PATH),conf,USER_NAME);
        Path oldPath = new Path("/tomtest1");
        Path newPath = new Path("/yxl");
        boolean result = fileSystem.rename(oldPath, newPath);
        System.out.println(result);
    }

    //删除目录或文件
    @Test
    public void deleteFile(){
        Path mydir = new Path("/test02");
        try {
            Configuration conf = new Configuration();
            conf.set("dfs.replication","2");
            fileSystem = FileSystem.get(new URI(HDFS_PATH),conf,USER_NAME);
            System.out.println("==========================================");
            fileSystem.delete(mydir,true);
            System.out.println("==========================================");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void upLoad(){
        login lo = new login();
//        lo.upLoad();
    }

    //从HDFS上下载文件
    @Test
    public void copyToLocalFile() throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication","2");
        fileSystem = FileSystem.get(new URI(HDFS_PATH),conf,USER_NAME);
        Path src = new Path("/user/");
        Path dst = new Path("d:\\test\\test1");
        /*
         * 第一个参数控制下载完成后是否删除源文件,默认是 true,即删除;
         * 最后一个参数表示是否将 RawLocalFileSystem 用作本地文件系统;
         * RawLocalFileSystem 默认为 false,通常情况下可以不设置,
         * 但如果你在执行时候抛出 NullPointerException 异常,则代表你的文件系统与程序可能存在不兼容的情况 (window 下常见),
         * 此时可以将 RawLocalFileSystem 设置为 true
         */
        System.out.println("==========================================");

        fileSystem.copyToLocalFile(false, src, dst, true);
        System.out.println("==========================================");
    }

    //上传文件到HDFS
//    @Test
    public void copyFromLocalFile() throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication","2");
        fileSystem = FileSystem.get(new URI(HDFS_PATH),conf,USER_NAME);
        // 如果指定的是目录，则会把目录及其中的文件都复制到指定目录下
        Path src = new Path("d:\\test\\hello.txt");
        Path dst = new Path("/user");
        System.out.println("===========================================");
        System.out.println(fileSystem);
        System.out.println("===========================================");
        fileSystem.copyFromLocalFile(src, dst);
    }

    // 查看指定目录下所有文件的信息
    //FileStatus 中包含了文件的基本信息，比如文件路径，是否是文件夹，修改时间，访问时间，所有者，所属组，文件权限，是否是符号链接等
    @Test
    public void listFiles() throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication","2");
        fileSystem = FileSystem.get(new URI(HDFS_PATH),conf,USER_NAME);
        FileStatus[] statuses = fileSystem.listStatus(new Path("/test01"));
        for (FileStatus fileStatus : statuses) {
            System.out.println("===========================================");

            //fileStatus 的 toString 方法被重写过，直接打印可以看到所有信息
            System.out.println(fileStatus.toString());
            System.out.println("===========================================");

        }
    }

    // 递归查看指定目录下所有文件的信息,和上面输出类似，只是多了文本大小，副本系数，块大小信息。
    @Test
    public void listFilesRecursive() throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication","2");
        fileSystem = FileSystem.get(new URI(HDFS_PATH),conf,USER_NAME);
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path("/test01"), true);
        while (files.hasNext()) {
            System.out.println("===========================================");
            System.out.println(files.next());
            System.out.println("===========================================");

        }
    }

    @After
    public void destroy(){
        fileSystem=null;
    }
}
