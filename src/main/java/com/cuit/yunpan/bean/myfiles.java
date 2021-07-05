package com.cuit.yunpan.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
@Data
@Component
public class myfiles {
    private Integer id;
    private Integer user_id;
    private String filename;
    private String file_ext;
    private Long file_size;
    private String create_time;
    private Integer is_delete;
    private Integer is_folder;
    private String file_path;
    private Integer is_update;
    private Integer is_upload;
    private Integer is_download;


}
