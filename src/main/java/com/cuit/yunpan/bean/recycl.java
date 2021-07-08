package com.cuit.yunpan.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class recycl {

    private Integer id;
    private Integer user_id;
    private  String filename;
    private String create_time;
    private String file_path;
    private String file_ext;
    private Long file_size;
    private Integer is_folder;

}
