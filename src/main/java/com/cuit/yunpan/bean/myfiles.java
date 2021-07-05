package com.cuit.yunpan.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
@Data
@Component
public class myfiles {
    private int id;
    private int user_id;
    private String filename;
    private String file_ext;
    private int file_size;
    private String create_time;
    private int is_delete;
    private int is_Folder;
    private String file_path;
    private int is_update;
    private int is_give;
    private int is_download;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFile_ext() {
        return file_ext;
    }

    public void setFile_ext(String file_ext) {
        this.file_ext = file_ext;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time() {
        this.create_time = create_time;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getIs_Folder() {
        return is_Folder;
    }

    public void setIs_Folder(int is_Folder) {
        this.is_Folder = is_Folder;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getIs_update() {
        return is_update;
    }

    public void setIs_update(int is_update) {
        this.is_update = is_update;
    }

    public int getIs_give() {
        return is_give;
    }

    public void setIs_give(int is_give) {
        this.is_give = is_give;
    }

    public int getIs_download() {
        return is_download;
    }

    public void setIs_download(int is_download) {
        this.is_download = is_download;
    }


}
