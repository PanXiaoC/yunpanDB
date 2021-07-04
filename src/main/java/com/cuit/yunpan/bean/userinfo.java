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

     public userinfo(){

     }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public userinfo(Integer id, String username, String pwd, Integer gender, String mobile, String email, String create_time, String last_login, Integer is_admin, Integer disk_max_size) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.gender = gender;
        this.tel = tel;
        this.email = email;
        this.create_time = create_time;
        this.last_login = last_login;
        this.is_admin = is_admin;
        this.disk_max_size = disk_max_size;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return tel;
    }

    public void setMobile(String mobile) {
        this.tel = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public Integer getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Integer is_admin) {
        this.is_admin = is_admin;
    }

    public Integer getDisk_max_size() {
        return disk_max_size;
    }

    public void setDisk_max_size(Integer disk_max_size) {
        this.disk_max_size = disk_max_size;
    }
}
