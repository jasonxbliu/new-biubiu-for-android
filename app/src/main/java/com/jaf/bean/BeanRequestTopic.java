package com.jaf.bean;

/**
 * Created by jarrahwu on 15/4/15.
 */
public class BeanRequestTopic extends BeanRequest{
    private int idType;
    private int lastId;
    private int type;

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
