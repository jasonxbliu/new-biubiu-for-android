package com.jaf.bean;

/**
 * Created by jarrahwu on 15/4/19.
 */
public class PostLike extends BeanRequest {
    private int ansId;
    private int questId;
    private int like;

    public int getAnsId() {
        return ansId;
    }

    public void setAnsId(int ansId) {
        this.ansId = ansId;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
