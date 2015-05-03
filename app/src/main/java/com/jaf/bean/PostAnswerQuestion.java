package com.jaf.bean;

/**
 * Created by jarrahwu on 15/4/19.
 */
public class PostAnswerQuestion extends BeanRequest{
    private String ans;
    private String selfLocDesc;
    private int questId;
    private String toNick;

    public String getToNick() {
        return toNick;
    }

    public void setToNick(String toNick) {
        this.toNick = toNick;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getSelfLocDesc() {
        return selfLocDesc;
    }

    public void setSelfLocDesc(String selfLocDesc) {
        this.selfLocDesc = selfLocDesc;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }
}
