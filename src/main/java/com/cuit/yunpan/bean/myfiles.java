package com.cuit.yunpan.bean;

import lombok.Data;

import java.time.DateTimeException;
@Data
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

}
