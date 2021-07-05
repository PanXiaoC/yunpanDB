package com.cuit.yunpan.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 与数据库的对应
 */
@Data
@Component
public class userinfo {

    private   Integer id;
    private   String username;
    private   String  pwd;
    private  Integer gender;
    private  String tel;
    private   String email;
    private   String create_time;
    private  String last_login;
    private  Integer is_admin;
    private   Integer disk_max_size;

}
