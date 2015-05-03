package com.jaf.bean;

import java.io.Serializable;

/**
 * Created by jarrahwu on 15/4/18. publish question request json bean
 */
public class BeanRequestPublish extends BeanRequest implements Serializable {
	private String selfLocDesc;
	private String quest;
	private int unionId;
	private int questType;
	private String sign;

    public String getSelfLocDesc() {
        return selfLocDesc;
    }

    public void setSelfLocDesc(String selfLocDesc) {
        this.selfLocDesc = selfLocDesc;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public int getUnionId() {
        return unionId;
    }

    public void setUnionId(int unionId) {
        this.unionId = unionId;
    }

    public int getQuestType() {
        return questType;
    }

    public void setQuestType(int questType) {
        this.questType = questType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
