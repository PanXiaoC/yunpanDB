package com.cuit.yunpan.bean;

import lombok.Data;

@Data
public class resource_share {

    private int id;
    private int user_id;
    private String resource_list;
    private String share_url;
    private int browse_count;
    private int save_count;
    private int download_count;
    private String share_time;
    private int share_type;
    private String share_code;

}
