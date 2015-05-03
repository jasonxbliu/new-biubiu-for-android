package com.jaf.bean;

import java.util.ArrayList;

/**
 * Created by jarrahwu on 15/4/26.
 */
public class PostDeleteMsg extends BeanRequest{
    private ArrayList<Integer> delId;

    public ArrayList<Integer> getDelId() {
        return delId;
    }

    public void setDelId(ArrayList<Integer> delId) {
        this.delId = delId;
    }
}
