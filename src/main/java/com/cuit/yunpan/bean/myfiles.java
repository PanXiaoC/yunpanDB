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
    private int is_folder;
    private String file_path;
    private int is_update;
    private int is_upload;
    private int is_download;


}
