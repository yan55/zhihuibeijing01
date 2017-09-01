package com.example.administrator.zhihuibeijing.domain;

/**
 * Created by Administrator on 2017/9/1/001
 */

public  class TopicBean {
    /**
     * description : 11111111
     * id : 10101
     * listimage : http://10.0.2.2:8080/zhbj/10007/1452327318UU91.jpg
     * sort : 1
     * title : 北京
     * url : http://10.0.2.2:8080/zhbj/10007/list_1.json
     */

    private String description;
    private int id;
    private String listimage;
    private int sort;
    private String title;
    private String url;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListimage() {
        return listimage;
    }

    public void setListimage(String listimage) {
        this.listimage = listimage;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}