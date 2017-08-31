package com.example.administrator.zhihuibeijing.domain;

/**
 * Created by Administrator on 2017/8/31/031.
 */

public class Children {

    private int id;

    private String title;

    private int type;

    private String url;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }

}
