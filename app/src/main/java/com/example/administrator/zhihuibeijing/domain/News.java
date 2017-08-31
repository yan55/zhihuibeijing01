package com.example.administrator.zhihuibeijing.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31/031
 */

public class News {

    private int retcode;

    private ArrayList<Data> data;

    private int[] extend;

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public int getRetcode() {
        return this.retcode;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> getData() {
        return this.data;
    }

    public void setExtend(int[] extend) {
        this.extend = extend;
    }

    public int[] getExtend() {
        return this.extend;
    }

    @Override
    public String toString() {
        return "News{" +
                "retcode=" + retcode +
                ", data=" + data +
                ", extend=" + Arrays.toString(extend) +
                '}';
    }
}
