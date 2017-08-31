package com.example.administrator.zhihuibeijing.domain;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/31/031
 * <p>
 * 使用Json解析数据时技巧
 * -1.逢{}创建对象，逢[]创建集合Arraylist<>;
 * -2:所以字段名称要和json返回的名称一致。
 */

public class NewsMenu {
    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<NewsMenuData> date;

    public class NewsMenuData {

        public int id;
        public String title;
        public int type;
        public ArrayList<NewsTabData> children;

        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "title='" + title + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    public class NewsTabData {

        public int id;
        public String title;
        public int type;
        public ArrayList children;
        public String url;

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsMenu{" +
                "date=" + date +
                '}';
    }


}
