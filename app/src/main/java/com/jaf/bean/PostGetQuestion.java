package com.jaf.bean;

/**
 * Created by jarrahwu on 15/4/26.
 */
public class PostGetQuestion extends BeanRequest{
    private int questId;

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }
}
