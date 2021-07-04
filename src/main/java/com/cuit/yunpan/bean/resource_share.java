package com.cuit.yunpan.bean;

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

    public String getResource_list() {
        return resource_list;
    }

    public void setResource_list(String resource_list) {
        this.resource_list = resource_list;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getBrowse_count() {
        return browse_count;
    }

    public void setBrowse_count(int browse_count) {
        this.browse_count = browse_count;
    }

    public int getSave_count() {
        return save_count;
    }

    public void setSave_count(int save_count) {
        this.save_count = save_count;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public String getShare_time() {
        return share_time;
    }

    public void setShare_time(String share_time) {
        this.share_time = share_time;
    }

    public int getShare_type() {
        return share_type;
    }

    public void setShare_type(int share_type) {
        this.share_type = share_type;
    }

    public String getShare_code() {
        return share_code;
    }

    public void setShare_code(String share_code) {
        this.share_code = share_code;
    }
}
