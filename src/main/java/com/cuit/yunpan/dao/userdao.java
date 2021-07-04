package com.cuit.yunpan.dao;

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
    @Select("select pwd from userinfo where username=#{username}")
    public String getPwd(userinfo user);
    @Select("select *from userinfo where tel=#{tel}")
    public userinfo getUserinfoByTel(userinfo user);

//    查看userinfo表中的所有数据
    @Select("select * from userinfo where username=#{username}")
    public userinfo getAll(userinfo user);
//    修改密码
    @Update("update userinfo set pwd=#{pwd} where username=#{username}")
    public boolean updatePwd(userinfo user);
//    注册
    @Insert("insert into userinfo(id,username,pwd,gender,tel,email,null,null,0,5) " +
            "values(#{id},#{username}),#{pwd},#{gender},#{tel},#{email}" +
            ",#{create_time},#{last_login},#{isAdmin},#{disk_max_size}")
    public String registerUserinfo(userinfo user);

}
