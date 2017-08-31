package com.example.administrator.zhihuibeijing.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31/031
 */

public class Data {

    private int id;

    private String title;

    private int type;

    private List<Children> children ;

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
    public void setChildren(List<Children> children){
        this.children = children;
    }
    public List<Children> getChildren(){
        return this.children;
    }
}
