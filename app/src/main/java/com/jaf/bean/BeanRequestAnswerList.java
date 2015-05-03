package com.jaf.bean;

import java.io.Serializable;

/**
 * Created by jarrah on 2015/4/16.
 */
public class BeanRequestAnswerList extends BeanRequest implements Serializable{
    private int questId;
    private int idType;
    private int lastId;

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

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }
}
