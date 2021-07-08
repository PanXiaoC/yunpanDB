package com.cuit.yunpan.dao;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.recycl;
import com.cuit.yunpan.bean.userinfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface userdao {
//    查看密码
    @Select("select count(*) from userinfo where tel=#{tel}")
    public Integer checkuser(userinfo user);
    @Select("select pwd from userinfo where tel=#{tel}")
    public String getPwd(userinfo user);
//    通过tel查userinfo数据(已测试）
    @Select("select *from userinfo where tel=#{tel}")
    public userinfo getUserinfoByTel(userinfo user);

//    通过tel查看userinfo表中的所有数据
    @Select("select * from userinfo where tel=#{tel}")
    public userinfo getUserinfoById(userinfo user);
//    修改密码(已测试)
    @Update("update userinfo set pwd=#{pwd} where tel=#{tel}")
    public boolean changePwd(userinfo user);
//    注册(已测试)
    @Insert("insert into userinfo(username,pwd,gender,tel,email,create_time,last_login)" +
            " values(#{username},#{pwd},#{gender},#{tel},#{email},now(),now())")
    public boolean registerUserinfo(userinfo user);
//  上传
    @Insert("insert into myfiles(user_id,filename,file_ext,file_size,create_time,is_upload,file_path,is_folder)" +
            " values(#{user_id},#{filename},#{file_ext},#{file_size},now(),#{is_upload},#{file_path},#{is_folder})")
    public Integer insertUpLoad(myfiles myfile);
//    通过user_id查myfiles表中数据
    @Select("select * from myfiles where user_id=#{user_id}")
    //查找用户名-1
    List<myfiles> getMyfilesByUser_id(myfiles myfile);
    //得到用户名称
    @Select("select username from userinfo where tel=#{tel}")
    String getusername(userinfo user);
    //求登录人拥有所有的文件总大小(已测试通过)
    @Select("select sum(file_size)from myfiles GROUP BY user_id HAVING user_id=#{user_id}")
    public Long sumFile_size(Integer user_id);
    // 求登录人拥有文件的个数(已测试通过)
    @Select("select count(*)from myfiles group by user_id having user_id=#{user_id}")
    public Integer countUserFile(myfiles myfile);
    //    分页查询，从n-1条数据开始返回m条数据(已测试通过)
    @Select("select *from myfiles where user_id=#{user_id} LIMIT #{n}, #{m}")
    public List<myfiles> limitpage_myfiles(Integer user_id, Integer n, Integer m);
    //修改文件信息

    @Update("update myfiles set filename=#{filename},file_path=#{file_path} where id=#{id}")
    public Integer changeFilename(myfiles myfile);
    //根据id查找文件名
    @Select("select *from myfiles where id=#{id}")
    public myfiles getMyfilesByid(myfiles myfile);
//    向回收站表插入数据
    @Insert("insert into table_4(user_id,del_time,file_path,file_ext,file_size,filename)" +
            "values(#{user_id},now(),#{file_path},#{file_ext},#{file_size},#{filename})")
    public int insertRecycl(recycl rec);
//    从myfiles表中删除
    @Delete("delete from myfiles where id=#{id}")
    public Integer deleteMyfiles(myfiles myfile);
    @Select("select *from myfiles where id_delete=#{id_delete}")
    public List<myfiles> findshare_myfiles(Integer id_delete);
    //查询myfiles中某个user的所有文件总和；
    @Select("SELECT SUM(file_size) FROM myfiles WHERE user_id=#{user_id}")
    public long sumOneFilesByUserid(myfiles myfile);

    @Select("select * from table_4 where user_id=#{user_id}")
    public List<recycl> getallrecycle(recycl rec);
}
