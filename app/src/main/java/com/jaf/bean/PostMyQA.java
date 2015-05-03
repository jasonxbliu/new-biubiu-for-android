package com.jaf.bean;

/**
 * Created by jarrah on 2015/4/21.
 */
public class PostMyQA extends BeanRequest{

    private int IdType;
    private int lastId;

    public int getIdType() {
        return IdType;
    }

    public void setIdType(int idType) {
        IdType = idType;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }
}
