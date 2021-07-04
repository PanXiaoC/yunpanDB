package com.cuit.yunpan.dao;

import com.cuit.yunpan.bean.userinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface userdao {
//    查看密码
    @Select("select pwd from userinfo where username=#{username}")
    public String getPwd(userinfo user);
//    查看userinfo表中的所有数据
    @Select("select * from userinfo where username=#{username}")
    public String getAll(userinfo user);
//    修改密码
    @Update("update userinfo set pwd=#{pwd} where username=#{username}")
    public int updatePwd(userinfo user);
//    注册
    @Insert("insert into userinfo values(#{id},#{username}),#{pwd},#{gender},#{mobile},#{email}" +
            ",#{create_time},#{last_login},#{isAdmin},#{disk_max_size}")
    public String registerUserinfo(userinfo user);

}
