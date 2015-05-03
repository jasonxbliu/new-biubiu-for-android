package com.jaf.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by jarrah on 2015/4/21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeanMsgItem implements Serializable{
    private int ullId;
    private int questId;
    private String quest;
    private int ansId;
    private String ans;
    private long pubTime;
    private int type;
    private int unionId;
    private int secansId;
    private String secAns;
    private String typePic;
    private int isPass;
    private String unionName;

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }

    public int getIsPass() {
        return isPass;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }

    public String getTypePic() {
        return typePic;
    }

    public void setTypePic(String typePic) {
        this.typePic = typePic;
    }

    public int getUllId() {
        return ullId;
    }

    public void setUllId(int ullId) {
        this.ullId = ullId;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public int getAnsId() {
        return ansId;
    }

    public void setAnsId(int ansId) {
        this.ansId = ansId;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public long getPubTime() {
        return pubTime;
    }

    public void setPubTime(long pubTime) {
        this.pubTime = pubTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUnionId() {
        return unionId;
    }

    public void setUnionId(int unionId) {
        this.unionId = unionId;
    }

    public int getSecansId() {
        return secansId;
    }

    public void setSecansId(int secansId) {
        this.secansId = secansId;
    }

    public String getSecAns() {
        return secAns;
    }

    public void setSecAns(String secAns) {
        this.secAns = secAns;
    }
}
