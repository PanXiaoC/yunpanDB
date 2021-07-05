package com.cuit.yunpan.dao;

import com.cuit.yunpan.bean.myfiles;
import com.cuit.yunpan.bean.userinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.awt.*;

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

//    通过id查看userinfo表中的所有数据
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
    @Insert("insert into myfiles(user_id,filename,create_time,is_folder,is_upload)" +
            " values(#{user_id},#{filename},now(),#{is_folder},#{is_upload})")
    public boolean insertUpLoad(myfiles myfile);
}
